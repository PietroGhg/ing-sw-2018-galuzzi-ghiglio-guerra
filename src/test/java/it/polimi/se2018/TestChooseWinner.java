package it.polimi.se2018;
import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.ChooseWinner;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.ShadesOfBlue;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.ShadesOfGreen;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.ShadesOfRed;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.ColourDiagonals;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.DeepShades;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.RowColourVariety;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.RoundTrack;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for the methods that choose a winner.
 * @author Pietro Ghiglio
 */
public class  TestChooseWinner {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;
    private Player p5;
    private Player p6;
    private Player p7;
    private Player p8;

    private ArrayList<Player> players;
    private RoundTrack roundTrack;
    private RoundTrack roundTrack4;
    private ArrayList<PublicObjectiveCard> puCards;

    @Before
    public void setup(){
        WpcGenerator gen = WpcGenerator.getInstance();
        WPC wpc = gen.getWPC(1);
        WPC wpc2 = gen.getWPC(1);
        WPC wpc3 = gen.getWPC(1);
        WPC wpc4 = gen.getWPC(1);

        //Set up wpcs
        filler(wpc);
        filler2(wpc2);
        filler3(wpc3);
        filler5(wpc4);

        //Set up players

        //total = 21, private = 11
        p1 = new Player(1);
        p1.setWpc(wpc);
        p1.addPrCard(new ShadesOfBlue());
        p1.setFavorTokens(5);

        //total = 30, private = 21
        p2 = new Player(2);
        p2.setWpc(wpc2);
        p2.addPrCard(new ShadesOfRed());
        p2.setFavorTokens(5);

        //like p1
        p3 = new Player(2);
        p3.setWpc(wpc);
        p3.addPrCard(new ShadesOfBlue());
        p3.setFavorTokens(5);

        //total = 21, private = 15
        p4 = new Player(1);
        p4.setWpc(wpc3);
        p4.addPrCard(new ShadesOfRed());
        p4.setFavorTokens(5);


        //like p1, with one less favour token
        p6 = new Player(2);
        p6.setWpc(wpc);
        p6.addPrCard(new ShadesOfBlue());
        p6.setFavorTokens(4);

        //like p1, with one less favour token, id = 3
        p5 = new Player(3);
        p5.setWpc(wpc);
        p5.addPrCard(new ShadesOfBlue());
        p5.setFavorTokens(4);

        //total = 12, private = 3
        p7 = new Player(4);
        p7.setWpc(wpc2);
        p7.addPrCard(new ShadesOfGreen());
        p7.setFavorTokens(5);

        //total = 20, private = 11
        p8 = new Player(2);
        p8.setWpc(wpc4);
        p8.addPrCard(new ShadesOfBlue());
        p8.setFavorTokens(5);


        //Set up roundtrack and public obj cards
        roundTrack = new RoundTrack(2);
        roundTrack4 = new RoundTrack(4);
        puCards = new ArrayList<>();
        puCards.add(new ColourDiagonals());
        puCards.add(new DeepShades());
        puCards.add(new RowColourVariety());


    }

    private void filler(WPC wpc){
        wpc.setDie(0, 1, new Die(4, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.BLUE));
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
    }

    private void filler2(WPC wpc2) {
        wpc2.setDie(0, 0, new Die(4, Colour.BLUE));
        wpc2.setDie(0, 2, new Die(2, Colour.BLUE));
        wpc2.setDie(0, 4, new Die(6, Colour.BLUE));
        wpc2.setDie(1, 0, new Die(3, Colour.RED));
        wpc2.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc2.setDie(1, 2, new Die(3, Colour.RED));
        wpc2.setDie(1, 4, new Die(3, Colour.RED));
        wpc2.setDie(2, 1, new Die(3, Colour.RED));
        wpc2.setDie(2, 2, new Die(1, Colour.BLUE));
        //wpc2.setDie(2, 3, new Die(3, Colour.RED));
        wpc2.setDie(2, 4, new Die(3, Colour.GREEN));
        wpc2.setDie(3, 0, new Die(3, Colour.RED));
        wpc2.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc2.setDie(3, 2, new Die(3, Colour.RED));
        wpc2.setDie(3, 4, new Die(3, Colour.RED));
    }

    private void filler3(WPC wpc3) {
        wpc3.setDie(0, 0, new Die(4, Colour.BLUE));
        wpc3.setDie(0, 2, new Die(2, Colour.BLUE));
        wpc3.setDie(0, 4, new Die(6, Colour.BLUE));
        wpc3.setDie(1, 0, new Die(4, Colour.RED));
        wpc3.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc3.setDie(1, 2, new Die(2, Colour.RED));
        wpc3.setDie(2, 1, new Die(3, Colour.RED));
        wpc3.setDie(2, 2, new Die(1, Colour.BLUE));
        wpc3.setDie(2, 3, new Die(3, Colour.RED));
        wpc3.setDie(2, 4, new Die(3, Colour.GREEN));
        wpc3.setDie(3, 0, new Die(3, Colour.RED));
        wpc3.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc3.setDie(3, 2, new Die(3, Colour.RED));
    }

    private void filler5(WPC wpc){
        wpc.setDie(0, 1, new Die(4, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.BLUE));
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
        //wpc.setDie(3, 4, new Die(4, Colour.GREEN));
    }


    @Test
    public void test1() {
        //p2 wins by total score
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        ChooseWinner chooseWinner = new ChooseWinner(players, puCards, roundTrack);
        try {
            assertEquals(chooseWinner.getWinner(), p2);
        } catch (NoWinnerException e) {
            fail();
        }
    }

    @Test
    public void test2(){
        //p1 and p3 have the same boards and cards -> same points, p3 should win due to roundtrack rule
        players = new ArrayList<>();
        players.add(p1);
        players.add(p3);
        ChooseWinner chooseWinner = new ChooseWinner(players,puCards,roundTrack);
        try {
            assertEquals(chooseWinner.getWinner(), p3);
        }
        catch (NoWinnerException e){
            fail();
        }
    }

    @Test
    public void test3() {
        //p1 and p4 have the same amount of total points, p4 has more private points
        players = new ArrayList<>();
        players.add(p4);
        players.add(p1);
        ChooseWinner chooseWinner = new ChooseWinner(players, puCards, roundTrack);
        try{
            assertEquals(chooseWinner.getWinner(), p4);
        }
        catch (NoWinnerException e){
            fail();
        }
    }

    @Test
    public void test4(){
        //p8 and p3 have the same boards and cards -> same points, p8 should win due to favourTokens
        players = new ArrayList<>();
        players.add(p6);
        players.add(p8);
        ChooseWinner chooseWinner = new ChooseWinner(players,puCards,roundTrack);
        try {
            assertEquals(chooseWinner.getWinner(), p8);
        }
        catch (NoWinnerException e){
            fail();
        }
    }

    @Test
    public void test5() {
        //Test with 4 players, p2 wins with totalscore
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p4);
        players.add(p6);
        ChooseWinner chooseWinner = new ChooseWinner(players, puCards, roundTrack4);
        try {
            assertEquals(chooseWinner.getWinner(), p2);
        }
        catch (NoWinnerException e){
            fail();
        }
    }

    @Test
    public void test6() {
        //last round order is 2 3 4 1, p3 should win due to last round
        players = new ArrayList<>();
        players.add(p1);
        players.add(p3);
        players.add(p5);
        players.add(p7);
        ChooseWinner chooseWinner = new ChooseWinner(players, puCards, roundTrack4);
        try{
            assertEquals(chooseWinner.getWinner(), p3);
        }
        catch (NoWinnerException e){
            fail();
        }
    }


}
