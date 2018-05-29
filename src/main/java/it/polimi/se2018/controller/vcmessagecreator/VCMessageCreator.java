package it.polimi.se2018.controller.vcmessagecreator;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.controller.parametersgetter.PGFactory;
import it.polimi.se2018.controller.parametersgetter.ParametersGetter;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.cli.View;

public class VCMessageCreator implements RawInputObserver { //no system.out, chiamo input dalla view
    private View view;
    private ParametersGetter parametersGetter;
    private PGFactory pgFactory;
    private VCAbstractMessage message;



    private void parseString(String playerInput){
        if(playerInput.startsWith("ToolCard" )){
            String[] temp = playerInput.split(" ");
            int toolCardID = Integer.parseInt(temp[1]);
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
        //aggiungere caso dicemove e endturn
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
