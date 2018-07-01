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
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.cli.View;

import java.io.*;
import java.util.List;

public class VCMessageCreator implements RawInputObserver { //no system.out, chiamo input dalla view
    private View view;
    private PGFactory pgFactory;
    private ParameterGetter parametersGetter;
    private ModelRepresentation modelRep;
    private VCAbstractMessage message;

    public VCMessageCreator(View view, ModelRepresentation modelRep){
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
            view.setAsking();
            parametersGetter = new ParameterGetterDie();
            message = new VCDieMessage(view.getPlayerID());
            parametersGetter.getParameters(view);
            view.notifyController(message);
        }

        else if(playerInput.startsWith("endturn")){
            message = new VCEndTurnMessage(view.getPlayerID());
            view.notifyController(message);
        }

        else if(playerInput.startsWith("show")) {
            String[] temp = playerInput.split(" ");
            String toShow = temp[1];
            toShow = toShow.trim();
            handleShowRequest(toShow);
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
            view.setAsking();
            int dpIndex = message.getParameters().get(0);
            cardSixAction(dpIndex);
            view.notifyController(message);
        }
        else if(toolCardID == 11){
            message = new VCToolMessage(view.getPlayerID(), toolCardID);
            view.setAsking();
            view.getDraftPoolIndex();
            cardElevenAction();
            view.notifyController(message);
        }
        else{
            try {
                parametersGetter = pgFactory.get(toolCardID);
                message = new VCToolMessage(view.getPlayerID(), toolCardID);
                view.setAsking();
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

        else if(toShow.equalsIgnoreCase("objectivecards")) {
            String[] puCards = modelRep.getPuCards();
            for(String puCard: puCards){
                showFile(puCard);
            }

        }

        else if(toShow.equalsIgnoreCase("myobjectivecard")){
            String prCard = modelRep.getPrCard();
            showFile(prCard);
        }

        else{view.displayMessage("Input not valid");}

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
