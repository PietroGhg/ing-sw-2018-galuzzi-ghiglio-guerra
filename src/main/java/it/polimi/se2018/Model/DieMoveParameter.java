package it.polimi.se2018.Model;

import it.polimi.se2018.Model.WPC.WPC;

/**
 * This class contains all the parameters for a normal Die Move.
 */
public class DieMoveParameter {
    private int dieX;
    private int dieY;
    private int cellX;
    private int cellY;
    private WPC wpc;

    public int getDieX() {
        return dieX;
    }

    public int getDieY() {
        return dieY;
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public WPC getWpc() {
        return wpc;
    }

    public void setDieX(int dieX) {
        this.dieX = dieX;
    }

    public void setDieY(int dieY) {
        this.dieY = dieY;
    }

    public void setCellX(int cellX) {
        this.cellX = cellX;
    }

    public void setCellY(int cellY) {
        this.cellY = cellY;
    }

    public void setWpc(WPC wpc) {
        this.wpc = wpc;
    }
}
