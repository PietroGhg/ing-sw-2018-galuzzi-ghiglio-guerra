package it.polimi.se2018.model.table;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.model.Turn;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the round track, keeps track of the current turn
 * The turns are represented in a 10x2*nPlayers matrix containig the IDs of the players in the correct order
 * @author Pietro Ghiglio
 */
public class RoundTrack {
    private static final int NUM_ROUND = 10;
    private List<List<Die>> roundTrack;
    private int roundCounter; //the current round (row of the round matrix)
    private int turnCounter; //column of the round matrix
    private int nPlayers;
    private int[][] roundMatrix;

    public RoundTrack(int nPlayers) {
        roundTrack = new ArrayList<>(NUM_ROUND);
        for(int i = 0; i < NUM_ROUND; i++){
            ArrayList<Die> temp = new ArrayList<>();
            roundTrack.add(temp);
        }

        this.nPlayers = nPlayers;
        turnCounter = 0;
        roundCounter = 0;
        roundMatrix = matrixFiller(nPlayers);
    }

    /**
     * Method that fills the round matrix at the beginning of the game
     * @param nPlayers the number of players
     * @return a matrix filled with the IDs in the correct order
     */
    private int[][] matrixFiller(int nPlayers){
        int[][] temp = new int[NUM_ROUND][2*nPlayers];

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

    /**
     *
     * @param playerID the player's id
     * @return 0 if the playerID it's not the current, 1 if it's the player's first turn in the round,
     *         2 if it's the player's second turn if the round.
     */
    public int turnNumber(int playerID){
        int ris  = 0;
        if(whoIsPlaying() != playerID) return 0;
        for(int i=0; i<turnCounter; i++){
            if(roundMatrix[roundCounter][i] == playerID) ris++;
        }
        return ris + 1;
    }



    public List<List<Die>> getRT() { return roundTrack; }

    public void setRT(List<List<Die>> roundTrack) {this.roundTrack = roundTrack;}

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

    /**
     * Method called by the model when a player finishes his turn.
     *
     * @param draftPool the draftpool
     * @throws GameEndedException if the 10th round is complete
     */
    public List<Die> nextTurn(List<Die> draftPool) throws GameEndedException{
        if(turnCounter == 2*nPlayers - 1){
            turnCounter = 0;
            if(roundCounter == NUM_ROUND - 1) throw new GameEndedException();
            else{
                List<Die> temp = roundTrack.get(roundCounter);
                temp.addAll(draftPool);
                draftPool.clear();
                draftPool = DiceBag.getInstance().extractDice(nPlayers);
                roundCounter++;
                return draftPool;
            }
        }
        else {
            turnCounter++;
            return draftPool;
        }
    }


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for(List<Die> tempList: roundTrack) {
            for(Die temp: tempList){
                builder.append(Colour.RESET);
                builder.append(temp.getDieColour().escape());
                builder.append(temp.getDieValue());
                builder.append("\t");
                builder.append(Colour.RESET);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    //just for testing purposes
    public int[][] getRoundMatrix(){
        return roundMatrix;
    }


    /**
     * Method used to calculate the winner
     * @return the last row of the matrix (the order of the players in the last round)
     */
    public int[] getLastRound() {
        int[] ris = new int[2*nPlayers];
        //TODO: fix this
        for(int i = 0; i < 2*nPlayers; i++) ris[i] = roundMatrix[NUM_ROUND - 1][i];
        return ris;
    }
}
