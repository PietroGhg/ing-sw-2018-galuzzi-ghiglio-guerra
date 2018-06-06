package it.polimi.se2018.controller.vcmessagecreator;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCDieMessage;
import it.polimi.se2018.controller.VCEndTurnMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.controller.parametersgetter.PGFactory;
import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.controller.parametersgetter.ParameterGetterDie;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.cli.View;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VCMessageCreator implements RawInputObserver { //no system.out, chiamo input dalla view
    private View view;
    private ParameterGetter parametersGetter;
    private PGFactory pgFactory;
    private ModelRepresentation modelRep;
    private VCAbstractMessage message;

    public VCMessageCreator(View view, ModelRepresentation modelRep){
        pgFactory = new PGFactory();
        this.modelRep = modelRep;
        this.view = view;
    }

    private void parseString(String playerInput){
        String error = "error while loading";
        if(playerInput.startsWith("ToolCard" )){
            String[] temp = playerInput.split(" ");
            int toolCardID = Integer.parseInt(temp[1]);
            //check che toolcardid è tra 1 e 12
            if(toolCardID == 6){
                message = new VCToolMessage(view.getPlayerID(), toolCardID);
                view.getDraftPoolIndex();
                int dpIndex = message.getParameters().get(0);
                cardSixAction(dpIndex);
                view.getCoordinates("Insert coordinates of the recipient cell.");
                view.notifyController(message);
            }
            if(toolCardID == 11){
                //cardElevenAction();
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

        else if(playerInput.startsWith("dicemove")){
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
            if (toShow.equalsIgnoreCase("roundtrack")) {
                view.showRoundTrack();
            }

            if (toShow.equalsIgnoreCase("myboard")) {
                view.showMyBoard();
            } else if (toShow.equalsIgnoreCase("boards")) {
                view.showBoards();

            } else if (toShow.equalsIgnoreCase("toolcards")) {
                try(BufferedReader reader = new BufferedReader(new FileReader("toolcard1"))){
                    String line = reader.readLine();
                    while (line != null) {
                        view.displayMessage(line);
                        line = reader.readLine();
                    }
                }catch (IOException e){view.displayMessage(error);} }





            else if(toShow.equalsIgnoreCase("draftpool")){ view.showDraftPool();}

            else if(toShow.equalsIgnoreCase("objectivecards")) {
                try (BufferedReader reader = new BufferedReader(new FileReader("objective cards"))) {
                    String line = reader.readLine();
                    while (line != null) {
                        view.displayMessage(line);
                        line = reader.readLine();
                    }

                } catch (IOException e) { view.displayMessage(error); }

            }

            else if(toShow.equalsIgnoreCase("myobjectivecard")){
                try (BufferedReader reader = new BufferedReader(new FileReader("my objective card"))) {
                String line = reader.readLine();
                while (line != null) {
                    view.displayMessage(line);
                    line = reader.readLine();
                }

            } catch (IOException e) { view.displayMessage(error); }

            }

            else{view.displayMessage("Input not valid");}



        }
        else {
            view.displayMessage("Input not valid");
        }

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
        WPC wpc = modelRep.getWpc(view.getPlayerID());
        List<int[]> validCoordinates = wpc.isPlaceable(d);
        view.getValidCoordinates(validCoordinates);
    }

}
