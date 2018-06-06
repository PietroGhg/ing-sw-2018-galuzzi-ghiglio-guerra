package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.InputNotValidException;

public class ToolCardFactory {
    public ToolCard get(int id) throws InputNotValidException{
        switch(id) {
            case(1): return GrozingPliers.getInstance();
            case(2): return EglomiseBrush.getInstance();
            case(3): return CopperFoilBurnisher.getInstance();
            case(4): return Lathekin.getInstance();
            case(5): return LensCutter.getInstance();
            case(6): return FluxBrush.getInstance();
            case(7): return GlazingHammer.getInstance();
            case(8): return RunningPliers.getInstance();
            case(9): return CorkBackedStraightedge.getInstance();
            case(10): return GrindingStone.getInstance();
            case(11): return FluxRemover.getInstance();
            case(12): return TapWheel.getInstance();
            //TODO: delete this
            case(20): return FluxBrush2.getInstance();
            default: throw new InputNotValidException();
        }
    }

    public ToolCard get(String s) throws InputNotValidException{
        switch(s){
            case("Grozing Pliers"): return GrozingPliers.getInstance();
            case("Eglomise Brush"): return EglomiseBrush.getInstance();
            case("Copper Foil Burnisher"): return CopperFoilBurnisher.getInstance();
            case("Lthekin"): return Lathekin.getInstance();
            case("Lens Cutter"): return LensCutter.getInstance();
            case("Flux Brush"): return FluxBrush.getInstance();
            case("Glazing Hammer"): return GlazingHammer.getInstance();
            case("Running Pliers"): return RunningPliers.getInstance();
            case("Cork Backed Straightedge"): return CorkBackedStraightedge.getInstance();
            case("Grinding Stone"): return GrindingStone.getInstance();
            case("Flux Remover"): return FluxRemover.getInstance();
            case("Tap Wheel"): return TapWheel.getInstance();
            default: throw new InputNotValidException();
        }
    }
}
