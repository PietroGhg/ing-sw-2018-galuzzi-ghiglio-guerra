package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

/**
 * Abstract class for the tool card.
 *
 * The parameters assignment order is:
 * 1: GrozingPliers -> 0: DraftPool index, 1: +1 or -1; 2: die row, 3: die column
 * 2: EglomiseBrush -> 0: die row, 1: die col, 2: cell row, 3: cell col
 * 3: CopperFoilBurnisher -> 0: die row, 1: die col, 2: cell row, 3: cell col
 * 4: Lathekin -> 0: die1 row, 1: die1 col, 2: cell1 row, 3: cell1 col,
                    4: die2 row, 5: die2 col, 6: cell2 row, 7: cell2 col
 * 5: LensCutter -> 0: DraftPool index, 1: RoundTrack turn number, 2: RoundTrack index
 * 6: FluxBrush -> 0: DraftPool index, 1: new value; [if the die is placeable -> 1: cell row, 2: cell col]
 * 7: GlazingHammer -> no parameters
 * 8: RunningPliers -> 0: DraftPool index, 1: cell row, 2: cell col
 * 9: CorkBackedStraightedge -> 0: DraftPool index, 1: cell row, 2: cell col
 * 10: GrindingStone -> 0: DraftPool index, 1: die row, 2: die col
 * 11: FluxRemover -> 0: DraftPool index, 1: Dice Bag index, 2: die value
                        3: cell row, 4: cell col
 * 12: TapWheel -> 0: RoundTrack number, 1: RoundTrack index,
                        2: die1 row, 3: die1 col, 4: cell1 row, 5: cell1 col
                        (if moves 2 dice): 6: die2 row, 7: die2 col, 8: cell2 row, 9: cell2 col
 */
public abstract class ToolCard {
    private String name;
    public abstract int getFavorTokensNeeded();
    protected ToolCard(String name){
        this.name = name;
    }

    public abstract void cardAction(PlayerMoveParameters parameters) throws MoveNotAllowedException;

    public String getName(){ return name; }
}