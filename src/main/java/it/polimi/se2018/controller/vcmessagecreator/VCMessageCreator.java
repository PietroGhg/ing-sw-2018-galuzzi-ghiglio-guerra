package it.polimi.se2018.controller.vcmessagecreator;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCDieMessage;
import it.polimi.se2018.controller.VCEndTurnMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.PGFactory;
import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.ParameterGetter;
import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.ParameterGetterDie;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.ViewInterface;
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.cli.View;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Class for the message creator from View to Controller
 * no System.Out used, input from View is called
 */
public class VCMessageCreator implements RawInputObserver {
    private ViewInterface view;
    private PGFactory pgFactory;
    private ParameterGetter parametersGetter;
    private ModelRepresentation modelRep;
    private VCAbstractMessage message;

    public VCMessageCreator(ViewInterface view, ModelRepresentation modelRep){
        pgFactory = new PGFactory();
        this.modelRep = modelRep;
        this.view = view;
    }

    /**
     * Method that parses the command given by the user
     * @param playerInput the string coming from the view
     */
    private void parseString(String playerInput){
        if(playerInput.startsWith("toolcard" )){
            if(!canMove()){
                view.displayMessage("Not your turn.");
                return;
            }
            String[] temp = playerInput.split(" ");
            int toolCardID = Integer.parseInt(temp[1]);
            handleToolCard(toolCardID);
        }

        else if(playerInput.startsWith("dicemove")){
            if(!canMove()){
                view.displayMessage("Not your turn.");
                return;
            }
            parametersGetter = new ParameterGetterDie();
            message = new VCDieMessage(view.getPlayerID());
            parametersGetter.getParameters(view);
            view.notifyController(message);
        }

        else if(playerInput.startsWith("endturn")){
            message = new VCEndTurnMessage(view.getPlayerID());
            view.notifyController(message);
        }

        else if(playerInput.startsWith("close")){
            System.exit(0);
        }
        else if(playerInput.startsWith("show")) {
            try {
                String[] temp = playerInput.split(" ");
                String toShow = temp[1];
                toShow = toShow.trim();
                handleShowRequest(toShow);
            }
            catch(ArrayIndexOutOfBoundsException e){
                view.displayMessage("Input not valid");
            }
        }
        else if(playerInput.equalsIgnoreCase("help")){
            showFile("help");
        }
        else {
            view.displayMessage("Input not valid");
        }
    }

    /**
     * Method called by parseString to handle a toolcard request
     * @param toolCardID the id of the toolcard
     */
    private void handleToolCard(int toolCardID){
        if(toolCardID == 6){
            message = new VCToolMessage(view.getPlayerID(), toolCardID);
            view.getDraftPoolIndex();
            int dpIndex = message.getParameters().get(0);
            cardSixAction(dpIndex);
            view.notifyController(message);
        }
        else if(toolCardID == 11){
            message = new VCToolMessage(view.getPlayerID(), toolCardID);
            view.getDraftPoolIndex();
            cardElevenAction();
            view.notifyController(message);
        }
        else{
            try {
                parametersGetter = pgFactory.get(toolCardID);
                message = new VCToolMessage(view.getPlayerID(), toolCardID);
                parametersGetter.getParameters(view);
                view.notifyController(message);
            }
            catch (InputNotValidException e){
                view.displayMessage(e.getMessage());
            }
        }
    }

    /**
     * Method used to handle a "show something" request
     * @param toShow the item requested to be shown
     */
    private void handleShowRequest(String toShow){

        if (toShow.equalsIgnoreCase("roundtrack")) {
            view.showRoundTrack();
        }
        else if (toShow.equalsIgnoreCase("myboard")) {
            view.showMyBoard();
        } else if (toShow.equalsIgnoreCase("boards")) {
            view.showBoards();

        } else if (toShow.equalsIgnoreCase("toolcards")) {
            List<String> toolCards = modelRep.getToolCards();
            for (String tc : toolCards) {
                showFile(tc);
            }
        }
        else if(toShow.equalsIgnoreCase("draftpool")){ view.showDraftPool();}

        else if(toShow.equalsIgnoreCase("pucards")) {
            String[] puCards = modelRep.getPuCards();
            for(String puCard: puCards){
                showFile(puCard);
            }
        }
        else if(toShow.equalsIgnoreCase("favortokens")){
            showFavorTokens();
        }
        else if(toShow.equalsIgnoreCase("prcard")){
            String prCard = modelRep.getPrCard();
            showFile(prCard);
        }

        else{view.displayMessage("Input not valid");}

    }

    private void showFavorTokens(){
        Map<String, Integer> favourT = modelRep.getFavourT();
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Integer> entry: favourT.entrySet()){
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue());
            builder.append("\n");
        }
        view.displayMessage(builder.toString());
    }

    /**
     * Method used to open, read, and show the content of a file
     * @param name the name of the item
     */
    private void showFile(String name){
        String path = "/text/" + name;
        StringBuilder builder = new StringBuilder();
        InputStream in = getClass().getResourceAsStream(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = reader.readLine();
            }
            builder.append("\n");
            view.displayMessage(builder.toString());

        } catch (IOException e) { view.displayMessage("Error while opening file"); }

    }

    public void rawUpdate(RawInputMessage message){
        message.accept(this);
    }

    public void visit(RawUnrequestedMessage message){
        parseString(message.getInput());

    }

    public void visit(RawRequestedMessage input){
        message.addParameter(input.getValue());

    }

    private void cardSixAction(int dpIndex){
        Die d = modelRep.getDieFromDraft(dpIndex);
        d.roll();
        message.addParameter(d.getDieValue());
        view.displayMessage("New value: " + d.getDieValue());
        //checks placeability
        checkPlaceability(d);
    }

    private void cardElevenAction(){
        int index = modelRep.getRandomIndex();
        message.addParameter(index);
        Die d = modelRep.getDieFromDBag(index);
        view.displayMessage("Extracted a " + d.getDieColour().letter() + " die: ");
        view.newDieValue();
        d.setDieValue(message.getParameters().get(2));
        //checks placeability
        checkPlaceability(d);
    }

    private void checkPlaceability(Die d){
        WPC wpc = modelRep.getWpc(view.getPlayerID());
        List<int[]> validCoordinates = wpc.isPlaceable(d);
        view.getValidCoordinates(validCoordinates);
    }

    private boolean canMove(){
        return view.getPlayerID() == modelRep.getCurrPlayer();
    }
}
