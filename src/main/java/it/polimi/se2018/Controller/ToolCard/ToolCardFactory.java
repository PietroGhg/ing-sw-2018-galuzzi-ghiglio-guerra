package it.polimi.se2018.Controller.ToolCard;

import it.polimi.se2018.Controller.Exceptions.InputNotValidException;

public class ToolCardFactory {
    public ToolCard get(int id) throws InputNotValidException{
        switch(id) {
            case(1): return new GrozingPliers();
            case(2): return new EglomiseBrush();
            case(3): return new CopperFoilBurnisher();
            case(4): return new Lathekin();
            case(5): return new LensCutter();
            case(6): return new FluxBrush();
            case(7): return new GlazingHammer();
            case(8): return new RunningPliers();
            case(9): return new CorkBackedStraightedge();
            case(10): return new GrindingStone();
            case(11): return new FluxRemover();
            case(12): return new TapWheel();
            default: throw new InputNotValidException();
        }
    }

    public ToolCard get(String s) throws InputNotValidException{
        switch(s){
            case("Grozing PLiers"): return new GrozingPliers();
            case("Eglomise Brush"): return new EglomiseBrush();
            case("Copper Foil Burnisher"): return new CopperFoilBurnisher();
            case("Lthekin"): return new Lathekin();
            case("Lens Cutter"): return new LensCutter();
            case("Flux Brush"): return new FluxBrush();
            case("Glazing Hammer"): return new GlazingHammer();
            case("Running Pliers"): return new RunningPliers();
            case("Cork Backed Straightedge"): return new CorkBackedStraightedge();
            case("Grinding Stone"): return new GrindingStone();
            case("Flux Remover"): return new FluxRemover();
            case("Tap Wheel"): return new TapWheel();
            default: throw new InputNotValidException();
        }
    }
}
