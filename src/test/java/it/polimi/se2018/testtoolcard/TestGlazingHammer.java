package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GlazingHammer;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.table.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestGlazingHammer {
    private Model model;
    private PlayerMoveParameters param;
    private Player player;
    private GlazingHammer card;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDP;
    private ArrayList<Die> emptyDP;

    @Before
    public void setUP(){
        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(1, Colour.RED));
        beforeDP.add(new Die(2, Colour.RED));
        beforeDP.add(new Die(3, Colour.PURPLE));
        beforeDP.add(new Die(4, Colour.YELLOW));
        beforeDP.add(new Die(5, Colour.BLUE));
        beforeDP.add(new Die(6, Colour.GREEN));

        expectedDP = new ArrayList<Die>();
        expectedDP.add(new Die(6, Colour.PURPLE));
        expectedDP.add(new Die(5, Colour.RED));
        expectedDP.add(new Die(4, Colour.RED));
        expectedDP.add(new Die(3, Colour.BLUE));
        expectedDP.add(new Die(2, Colour.YELLOW));
        expectedDP.add(new Die(1, Colour.GREEN));

        emptyDP = new ArrayList<Die>();

        card = GlazingHammer.getInstance();

        //usare questo metodo se extractor da problemi
        Extractor.resetInstance();

    }

    private int[] countColours(ArrayList<Die> dp){
        int i=0;
        int temp=0;
        int [] count = new int[5];
        for(Colour c : Colour.values()){
            for(Die d : dp){
                if(d.getDieColour()==c) temp++;
            }
            count[i]=temp;
            i++;
            temp=0;
        }
        return count;
    }

    @Test
    public void test1(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);

        //bisogna aggiungere giocatori al model e chiamare model.startgame() che inizializza la roundmatrix
        model.addPlayer(player);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        model.startGame();

        /*la carta può essere giocata solo nel seocndo turno, ci sono tre giocatori in gioco
           -> devono passare (1,2,3,3,2,1) cinque turni
        */
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();


        param.setDraftPool(beforeDP);
        model.setParameters(param);


        try{
            card.cardAction(param);
            //bisogna controllare colore per colore, asserEquals non funziona per gli int[]
            int[] actualColors = countColours(param.getDraftPool());
            int[] expectedColors = countColours(expectedDP);
            for(int i = 0; i< actualColors.length; i++)
                assertEquals(expectedColors[i], actualColors[i]);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

    }
}
