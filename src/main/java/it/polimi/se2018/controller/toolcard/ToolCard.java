package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public abstract class ToolCard {
    private String name;
    public abstract int getFavorTokensNeeded();
    protected ToolCard(String name){
        this.name = name;
    }

    public abstract void cardAction(PlayerMoveParameters parameters) throws MoveNotAllowedException;

    public String getName(){ return name; }
}

/*
    Stato test      Parameters assignment order:

    T               1: GrozingPliers -> 0: DraftPool index, 1: +1 or -1; 2: die row, 3: die column
    T               2: EglomiseBrush -> 0: die row, 1: die col, 2: cell row, 3: cell col
    T               3: CopperFoilBurnisher -> 0: die row, 1: die col, 2: cell row, 3: cell col
    T               4: Lathekin -> 0: die1 row, 1: die1 col, 2: cell1 row, 3: cell1 col,
                                   4: die2 row, 5: die2 col, 6: cell2 row, 7: cell2 col
    T               5: LensCutter -> 0: DraftPool index, 1: RoundTrack number, 2: RoundTrack index
    T               6: FluxBrush -> 0: DraftPool index; (if isPlaceable): 1: cell row, 2: cell col
    T               7: GlazingHammer -> no parameters, check second turn
    T               8: RunningPliers -> 0: DraftPool index, 1: cell row, 2: cell col
    T               9: CorkBackedStraightedge -> 0: DraftPool index, 1: cell row, 2: cell col
    T               10: GrindingStone -> 0: DraftPool index, 1: die row, 2: die col
    T               11: FluxRemover -> 0: DraftPool index, 1: Dice Bag index, 2: dieValue
                                       3: cell row, 4: cell col
    T               12: TapWheel -> 0: RoundTrack number, 1: RoundTrack index,
                        2: die1 row, 3: die1 col, 4: cell1 row, 5: cell1 col
                        (if moves 2 dice): 6: die2 row, 7: die2 col, 8: cell2 row, 9: cell2 col
*/