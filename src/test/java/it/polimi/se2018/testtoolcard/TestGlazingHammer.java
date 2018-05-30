package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GlazingHammer;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
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
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            assertEquals(countColours(param.getDraftPool()), countColours(expectedDP));
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

    }
}
