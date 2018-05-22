package it.polimi.se2018.model.table;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.model.Turn;

import java.util.ArrayList;

/**
 * Class for the round track, keeps track of the current turn
 * The turns are represented in a 10x2*nPlayers matrix containig the IDs of the players in the correct order
 * @author Pietro Ghiglio
 */
public class RoundTrack {
    private static final int NUM_ROUND = 10;
    private ArrayList<ArrayList<Die>> roundTrack;
    private int turnCounter;
    private  int nPlayers;
    private int roundCounter;
    private int[][] roundMatrix;

    /**
     * Method that fills the round matrix at the beginning of the game
     * @param nPlayers the number of players
     * @return a matrix filled with the IDs in the correct order
     */
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
        // Da valutare:
        roundTrack = new ArrayList<>();
        for(int i = 0; i < NUM_ROUND;i++) {
            roundTrack.add(new ArrayList<>());
        }

    }

    public ArrayList<ArrayList<Die>> getRoundTrack() { return roundTrack; }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) {this.roundTrack = roundTrack;}

    public Die getRoundTrackCell(int turn, int index){
        return roundTrack.get(turn).get(index);
    }

    public void setRoundTrackCell(int turn, int index, Die die){
        roundTrack.get(turn).add(index, die);
    }
    /**
     *
     * @return the ID of the current player
     */
    public int whoIsPlaying(){
        return roundMatrix[roundCounter][turnCounter];
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    /**
     * Method called by the model when a player finishes his turn
     * @throws GameEndedException if the 10th round is complete
     */
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

    /*
    public Die getDie(int round, int turn){
        if(round )
    }
    */

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        Die temp;
        for(int i = 0; i < roundTrack.size(); i++){
            for(int j = 0; j < roundTrack.get(i).size(); j++){
                temp = roundTrack.get(i).get(j);
                builder.append(Colour.RESET + temp.getDieColour().escape() + temp.getDieValue() + "\t" + Colour.RESET);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    //just for testing purposes
    public int[][] getRoundMatrix(){
        return roundMatrix;
    }

    /*
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
    */

    /**
     * Method used to calculate the winner
     * @return the last row of the matrix (the order of the players in the last round)
     */
    public int[] getLastRound() {
        int[] ris = new int[2*nPlayers];
        for(int i = 0; i < 2*nPlayers; i++) ris[i] = roundMatrix[NUM_ROUND - 1][i];
        return ris;
    }
}
