package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

public interface ToolCard {
    int getFavorTokensNeeded();
    void cardAction(PlayerMoveParameters parameters) throws MoveNotAllowedException;
}

/*
        Parameters assignment order:
    T   1: GrozingPliers -> 0: DraftPool index, 1: +1 or -1; 2: die row, 3: die column
    T   2: EglomiseBrush -> 0: die row, 1: die col, 2: cell row, 3: cell col
    T   3: CopperFoilBurnisher -> 0: die row, 1: die col, 2: cell row, 3: cell col
    T   4: Lathekin -> 0: die1 row, 1: die1 col, 2: cell1 row, 3: cell1 col,
                        4: die2 row, 5: die2 col, 6: cell2 row, 7: cell2 col
    T   5: LensCutter -> 0: DraftPool index, 1: RoundTrack number, 2: RoundTrack index
        DA FINIRE! -> 6: FluxBrush -> 0: DraftPool index; se il controllo è positivo (si può piazzare il dado): 1: cell row, 2: cell col
        7: GlazingHammer -> (no parameters); controllo che sia il secondo turno
        8: RunningPliers -> 0: DraftPool index, 1: cell row, 2: cell col; controllo che sia il primo turno, far saltare il secondo turno
    T   9: CorkBackedStraightedge -> 0: DraftPool index, 1: cell row, 2: cell col
        10: GrindingStone -> 0: DraftPool index, 1: die row, 2: die col
        DA FINIRE! -> 11: FluxRemover -> 0: DraftPool index, 1: cell row, 2: cell col; die value?
        12: TapWheel -> 0: RoundTrack number, 1: RoundTrackindex,
                        2: die1 row, 3: die1 col, 4: cell1 row, 5: cell1 col,
                        6: die2 row, 7: die2 col, 8: cell2 row, 9: cell2 col
*/