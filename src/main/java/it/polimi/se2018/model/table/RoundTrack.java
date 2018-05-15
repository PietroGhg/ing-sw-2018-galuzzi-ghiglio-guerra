package it.polimi.se2018.model.table;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.exceptions.GameEndedException;

import java.util.ArrayList;

public class RoundTrack {
    private static final int NUM_ROUND = 10;
    private ArrayList<ArrayList<Die>> roundTrack;
    private int turnCounter;
    private  int nPlayers;
    private int roundCounter;
    private int[][] roundMatrix;

    /*rounds and turns are determined just by the number of players
    * each player has an unique identifier, roundMatrix contains in each row
    * the order of the players for each round*/
    private int[][] matrixFiller(int nPlayers){
        int temp[][] = new int[NUM_ROUND][2*nPlayers];

        //fills left half of the matrix
        for(int col = 0; col < nPlayers; col++){
            for(int row = 0; row < NUM_ROUND; row++){
                temp[row][col] = ((row+col) % nPlayers)  + 1;
            }
        }

        //fills right half of the matrix, it's the opposite of the left side
        for(int row = 0; row < NUM_ROUND; row++){
            for(int j = 0; j < nPlayers; j++){
                temp[row][j + nPlayers] = temp[row][nPlayers - 1 - j];
            }
        }
        return temp;
    }

    public RoundTrack(int nPlayers) {
        this.nPlayers = nPlayers;
        turnCounter = 0;
        roundCounter = 0;
        roundMatrix = matrixFiller(nPlayers);
    }

    //returns the id of the current player
    public int whoIsPlaying(){
        return roundMatrix[roundCounter][turnCounter];
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    //returns false if game is finished
    public void nextTurn() throws GameEndedException{
        if(turnCounter == 2*nPlayers - 1){
            turnCounter = 0;
            if(roundCounter == NUM_ROUND - 1) throw new GameEndedException();
            else roundCounter++;
        }
        else {
            turnCounter++;
        }
    }

    //just for testing purposes
    public int[][] getRoundMatrix(){
        return roundMatrix;
    }

    public static void main(String[] args) {
        RoundTrack r = new RoundTrack(2);
        int[][] temp = r.getRoundMatrix();
        for(int i = 0; i < NUM_ROUND; i++){
            for(int j = 0; j < 4; j++){
                System.out.print(temp[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public int[] getLastRound() {
        int[] ris = new int[2*nPlayers];
        for(int i = 0; i < 2*nPlayers; i++) ris[i] = roundMatrix[NUM_ROUND - 1][i];
        return ris;
    }
}
