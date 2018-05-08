package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.WPC.WPC;
import it.polimi.se2018.Model.WPC.WpcGenerator;

public class ColourDiagonals extends PublicObjectiveCard {  //Diagonali colorate
    /* Count of diagonally adjacent same-colour dice */
    @Override
    public int getScore (WPC wpc){
        int score=0;
        int [][] diagMatrix = new int[WPC.NUMROW][WPC.NUMCOL];
        for(int i=0; i<WPC.NUMROW; i++){
            for(int j=0; j<WPC.NUMCOL; j++) {
                diagMatrix[i][j] = 0;
            }
        }

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

        for(int i=0; i<WPC.NUMROW; i++){
                for(int j=0; j<WPC.NUMCOL; j++){
                if(diagMatrix[i][j]==1)
                    score++;
            }
        }

        return score;
    }

    public static void main (String[] args) {
        WpcGenerator gen = new WpcGenerator();
        WPC wpc = gen.getWPC(1);
        wpc.setDie(0, 1, new Die(4, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc.setDie(1, 3, new Die(1, Colour.GREEN));
        wpc.setDie(1, 4, new Die(2, Colour.YELLOW));
        wpc.setDie(2, 0, new Die(5, Colour.PURPLE));
        wpc.setDie(2, 1, new Die(6, Colour.GREEN));
        wpc.setDie(2, 3, new Die(6, Colour.PURPLE));
        wpc.setDie(2, 4, new Die(3, Colour.RED));
        wpc.setDie(3, 0, new Die(4, Colour.BLUE));
        wpc.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc.setDie(3, 2, new Die(4, Colour.GREEN));
        wpc.setDie(3, 3, new Die(2, Colour.BLUE));
        wpc.setDie(3, 4, new Die(4, Colour.GREEN));
        ColourDiagonals cd = new ColourDiagonals();
        System.out.println(cd.getScore(wpc));
    }
}
