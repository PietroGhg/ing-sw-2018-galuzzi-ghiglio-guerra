package it.polimi.se2018.controller.vcmessagecreator;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCDieMessage;
import it.polimi.se2018.controller.VCEndTurnMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.controller.parametersgetter.PGFactory;
import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.controller.parametersgetter.ParameterGetterDie;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.cli.View;

public class VCMessageCreator implements RawInputObserver { //no system.out, chiamo input dalla view
    private View view;
    private ParameterGetter parametersGetter;
    private PGFactory pgFactory;
    private VCAbstractMessage message;

    public VCMessageCreator(View view){
        pgFactory = new PGFactory();
        this.view = view;
    }

    private void parseString(String playerInput){
        if(playerInput.startsWith("ToolCard" )){
            String[] temp = playerInput.split(" ");
            int toolCardID = Integer.parseInt(temp[1]);
            //check che toolcardid è tra 1 e 12
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

        else if(playerInput.startsWith("show")){
            String[] temp =playerInput.split(" ");
            String toShow = temp[1];
            if(toShow.equalsIgnoreCase("roundtrack")){ view.showRoundTrack(); }

            if(toShow.equalsIgnoreCase("myboard")){ view.showMyBoard(); }

            if(toShow.equalsIgnoreCase("boards")){ view.showBoards();}

            if(toShow.equalsIgnoreCase("toolcards")){ }

            if(toShow.equalsIgnoreCase("draftpool")){ view.showDraftPool();}

            if(toShow.equalsIgnoreCase("objectivecards")){ }

            if(toShow.equalsIgnoreCase("myobjectivecard")){

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

}
