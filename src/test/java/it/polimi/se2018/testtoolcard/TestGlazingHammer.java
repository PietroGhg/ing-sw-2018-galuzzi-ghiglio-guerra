package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GlazingHammer;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.table.DiceBag;
import it.polimi.se2018.model.table.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for tool card GlazingHammer
 * @author Leonardo Guerra
 */

public class TestGlazingHammer {
    private Model model;
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private Player player;
    private GlazingHammer card;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDP;
    private ArrayList<Die> emptyDP;

    @Before
    public void setUP(){
        beforeDP = new ArrayList<>();
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

        Extractor.resetInstance();
        DiceBag.resetInstance();

    }

    private int[] countColours(List<Die> dp){
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

    /**
     * Test for the normal use of the tool card
     */
    @Test
    public void test(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        model.addPlayer(player);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();

        /*
        the tool card can be played only during the player's second turn,
        which is after 5 turns from the beginning of the round
        (player 1 is the first of the round)
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
            int[] actualColors = countColours(param.getDraftPool());
            int[] expectedColors = countColours(expectedDP);
            //checks colour by colour
            for(int i = 0; i< actualColors.length; i++)
                assertEquals(expectedColors[i], actualColors[i]);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Not player's turn -> throws exception
     */
    @Test
    public void testWrongTurn(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        model.addPlayer(player);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        //Not player's turn

        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: this card can be played in the second turn only.", e.getMessage());
        }
    }

    /**
     * Player has already drafted a die -> throws exception
     */
    @Test
    public void testPlayedDie() {
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(5);
        model.addPlayer(player);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        DiceBag.resetInstance();
        model.startGame();
        model.nextTurn();
        model.setToolCardUsed();
        System.out.println(model.cardHasBeenPlayed());
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.setDiePlaced();
        System.out.println(model.dieHasBeenPlayed());
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: a die is already been drafted.", e.getMessage());
        }
    }

    /**
     * The player hasn't enough favor tokens -> throws exception
     */
    @Test
    public void testEnoughFT(){
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(4);
        model.addPlayer(player);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.setDraftPool(beforeDP);
        model.setParameters(param);
        DiceBag.resetInstance();
        model.startGame();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();

        //The first time should go right
        try{
            card.cardAction(param);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        //Second time should go right
        try{
            card.cardAction(param2);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(), model);
        //Third time: not enough favour tokens -> throws exception
        try{
            card.cardAction(param3);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: not enough favor tokens.", e.getMessage());
        }
    }
}
