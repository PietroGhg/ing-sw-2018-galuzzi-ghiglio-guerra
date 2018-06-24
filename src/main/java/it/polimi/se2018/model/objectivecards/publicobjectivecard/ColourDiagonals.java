package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for publicobjectivecard ColourDiagonals
 * @author Leonardo Guerra
 */

public class ColourDiagonals extends PublicObjectiveCard {  //Diagonali colorate
    public ColourDiagonals(){
        super("ColourDiagonals");
    }

    @Override
    /**
     * Method for the computation of a partial score:
     * count of diagonally adjacent same-colour dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc){
        int score=0;

        //matrix with 1 in place of adjacent same-colour dice in the corresponding cell
        int [][] diagMatrix = new int[WPC.NUMROW][WPC.NUMCOL];
        for(int i=0; i<WPC.NUMROW; i++){
            for(int j=0; j<WPC.NUMCOL; j++) {
                diagMatrix[i][j] = 0;
            }
        }

        //scan from the top left to the bottom right
        for(int i=0; i<(WPC.NUMROW)-1; i++){
            for (int j=0; j<(WPC.NUMCOL)-1; j++){
                if (!(wpc.getCell(i, j).isEmpty())) {
                    Colour c1 = wpc.getCell(i+1,j+1).getDie().getDieColour();
                    Colour c2 = wpc.getCell(i,j).getDie().getDieColour();
                    if( !(wpc.getCell(i+1, j+1).isEmpty()) && c1.equals(c2)){
                        diagMatrix[i][j]=1;
                        diagMatrix[i+1][j+1]=1;
                    }
                }
            }
        }

        //scan from the top right to the bottom left
        for(int k=0; k<(WPC.NUMROW)-1; k++){
            for(int z=WPC.NUMCOL-1; z>0; z--){
                if (!(wpc.getCell(k, z).isEmpty())) {
                    Colour c1 = wpc.getCell(k+1,z-1).getDie().getDieColour();
                    Colour c2 = wpc.getCell(k,z).getDie().getDieColour();
                    if( !(wpc.getCell(k+1, z-1).isEmpty()) && c1.equals(c2)){
                        diagMatrix[k][z]=1;
                        diagMatrix[k+1][z-1]=1;
                    }
                }
            }
        }

        //score: count of 1s in the matrix
        for(int i=0; i<WPC.NUMROW; i++){
                for(int j=0; j<WPC.NUMCOL; j++){
                    if(diagMatrix[i][j]==1)
                        score++;
            }
        }

        return score;
    }
}
