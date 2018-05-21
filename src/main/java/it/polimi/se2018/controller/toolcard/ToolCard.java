package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

public interface ToolCard {
    int getFavorTokensNeeded();
    void cardAction(Model model) throws MoveNotAllowedException;
}

/*
    Parameters assignment order:
    1: GrozingPliers -> 0: die row, 1: die column, 2: +1 or -1
    2: EglomiseBrush -> 0: die row, 1: die col, 2: cell row, 3: cell col
    3: CopperFoilBurnisher -> 0: die row, 1: die col, 2: cell row, 3: cell col
    4: Lathekin -> 0: die1 row, 1: die1 col, 2: cell1 row, 3: cell1 col,
                    4: die2 row, 5: die2 col, 6: cell2 row, 7: cell2 col
    5: LensCutter -> 0: DraftPool index, 1: RoundTrack number, 2: RoundTrack index
    6: FluxBrush -> 0: die row, 1: die col
    7: GlazingHammer -> (no parameters)
    8: RunningPliers -> 0: DraftPool index, 1: cell row, 2: cell col
    9: CorkBackedStraightedge -> 0: DraftPool index, 1: cell row, 2: cell col
    10: GrindingStone -> 0: die row, 1: die col
    11: FluxRemover -> 0: DraftPool index, 1: cell row, 2: cell col; die value?
    12: TapWheel -> 0: RoundTrack number, 1: RoundTrackindex,
                    2: die1 row, 3: die1 col, 4: cell1 row, 5: cell1 col,
                    6: die2 row, 7: die2 col, 8: cell2 row, 9: cell2 col
*/