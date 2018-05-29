package it.polimi.se2018.controller.vcmessagecreator;

import it.polimi.se2018.controller.parametersgetter.PGFactory;
import it.polimi.se2018.controller.parametersgetter.ParametersGetter;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.cli.View;

public class VCMessageCreator implements RawInputObserver { //no system.out, chiamo input dalla view
    private View view;
    private ParametersGetter parametersGetter;
    private PGFactory pgFactory;



    private void parseString(String playerInput){
        if(playerInput.startsWith("ToolCard" )){
            String[] temp = playerInput.split(" ");
            int toolCardID = Integer.parseInt(temp[1]);
            try {
                parametersGetter = pgFactory.get(toolCardID);
                view.createToolMessage(toolCardID);
                parametersGetter.getParameters(view);
                view.notifyController();
            }
            catch (InputNotValidException e){
                view.displayMessage(e.getMessage());
            }

        }
    }

    public void rawUpdate(RawInputMessage message){
        String input = message.getInput();
        parseString(input);
    }

}
