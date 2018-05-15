package it.polimi.se2018;

import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.ChooseWinner;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.ShadesOfBlue;
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
 * Test for ChooseWinner class
 * @author Leonardo Guerra
 */

public class TestChooseWinner2 {
    private Player p1;
    private Player p2;
    private ArrayList<Player> players;
    private RoundTrack roundTrack;
    private ArrayList<PublicObjectiveCard> puCards;

    @Before
    public void setup(){
        WpcGenerator gen = new WpcGenerator();
        WPC wpc = gen.getWPC(1);
        WPC wpc2 = gen.getWPC(1);

        //WPCs setup
        filler(wpc);
        filler2(wpc2);

        p1 = new Player(1);
        p1.setWpc(wpc);
        p1.addPrCard(new ShadesOfBlue());
        p1.setFavorTokens(5);

        p2 = new Player(2);
        p2.setWpc(wpc2);
        p2.addPrCard(new ShadesOfBlue());
        p2.setFavorTokens(4);

        roundTrack = new RoundTrack(2);
        puCards = new ArrayList<>();
        puCards.add(new ColourDiagonals());
        puCards.add(new DeepShades());
        puCards.add(new RowColourVariety());

    }

    private void filler(WPC wpc){
        wpc.setDie(0, 3, new Die(5, Colour.BLUE));
        wpc.setDie(1, 2, new Die(1, Colour.PURPLE));
        wpc.setDie(1, 4, new Die(1, Colour.GREEN));
        wpc.setDie(2, 0, new Die(1, Colour.BLUE));
        wpc.setDie(2, 1, new Die(5, Colour.RED));
        wpc.setDie(2, 2, new Die(1, Colour.YELLOW));
        wpc.setDie(2, 3, new Die(1, Colour.GREEN));
        wpc.setDie(2, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(3, 2, new Die(6, Colour.GREEN));
        wpc.setDie(3, 4, new Die(1, Colour.BLUE));

    }

    private void filler2(WPC wpc2){
        wpc2.setDie(0, 1, new Die(5, Colour.BLUE));
        wpc2.setDie(0, 4, new Die(5, Colour.BLUE));
        wpc2.setDie(1, 0, new Die(6, Colour.BLUE));
        wpc2.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc2.setDie(1, 2, new Die(1, Colour.BLUE));
        wpc2.setDie(1, 3, new Die(6, Colour.BLUE));
        wpc2.setDie(1, 4, new Die(1, Colour.BLUE));
        wpc2.setDie(2, 3, new Die(1, Colour.BLUE));
        wpc2.setDie(3, 2, new Die(1, Colour.BLUE));

    }

    @Test
    public void test1(){
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

}
