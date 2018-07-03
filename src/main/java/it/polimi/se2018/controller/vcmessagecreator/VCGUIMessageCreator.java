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

import java.util.List;

/**
 * Class for the GUI message creator from View to Controller
 */
public class VCGUIMessageCreator implements RawInputObserver {
    private ViewInterface view;
    private PGFactory pgFactory;
    private ParameterGetter parametersGetter;
    private ModelRepresentation modelRep;
    private VCAbstractMessage message;

    public VCGUIMessageCreator(ViewInterface view, ModelRepresentation modelRep){
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
            if(canNotMove()){
                view.displayMessage("Not your turn.");
                return;
            }
            String[] temp = playerInput.split(" ");
            int toolCardID = Integer.parseInt(temp[1]);
            handleToolCard(toolCardID);
        }

        else if(playerInput.startsWith("dicemove")){
            if(canNotMove()){
                view.displayMessage("Not your turn.");
                return;
            }
            parametersGetter = new ParameterGetterDie();

            message = new VCDieMessage(view.getPlayerID());
            new Thread(() -> {
                parametersGetter.getParameters(view);
                view.notifyController(message);
            }).start();
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
            new Thread(() -> {
                message = new VCToolMessage(view.getPlayerID(), toolCardID);
                view.displayMessage("Select a die from the draftpool.");
                view.getDraftPoolIndex();
                int dpIndex = message.getParameters().get(0);
                cardSixAction(dpIndex);
                view.notifyController(message);
            }).start();

        }
        else if(toolCardID == 11){
            new Thread(() -> {
                message = new VCToolMessage(view.getPlayerID(), toolCardID);
                view.displayMessage("Select a die from the draftpool.");
                view.getDraftPoolIndex();
                cardElevenAction();
                view.notifyController(message);
            }).start();
        }
        else{
            try {
                parametersGetter = pgFactory.get(toolCardID);
                message = new VCToolMessage(view.getPlayerID(), toolCardID);

                new Thread(() -> {
                    parametersGetter.getParameters(view);
                    view.notifyController(message);
                }).start();
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
        }
        else if(toShow.equalsIgnoreCase("draftpool")){ view.showDraftPool();}

        else{view.displayMessage("Input not valid");}

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

    private boolean canNotMove(){
        return view.getPlayerID() != modelRep.getCurrPlayer();
    }
}
