package it.polimi.se2018;
import it.polimi.se2018.Exceptions.NoWinnerException;
import it.polimi.se2018.Model.ChooseWinner;
import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard.ShadesOfBlue;
import it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard.ShadesOfGreen;
import it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard.ShadesOfRed;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.ColourDiagonals;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.DeepShades;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.PublicObjectiveCard;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.RowColourVariety;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.Table.RoundTrack;
import it.polimi.se2018.Model.WPC.WPC;
import it.polimi.se2018.Model.WPC.WpcGenerator;
import org.junit.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for the methods that choose a winner.
 * @author Pietro Ghiglio
 */
public class TestChooseWinner {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    private ArrayList<Player> players;
    private RoundTrack roundTrack;
    private ArrayList<PublicObjectiveCard> puCards;

    @Before
    public void setup(){
        WpcGenerator gen = new WpcGenerator();
        WPC wpc = gen.getWPC(1);
        WPC wpc2 = gen.getWPC(1);

        //Set up wpcs
        filler(wpc);
        filler2(wpc2);

        //Set up players
        p1 = new Player(1);
        p1.setWpc(wpc);
        p1.addPrCard(new ShadesOfBlue());
        p1.setFavorTokens(5);

        p2 = new Player(2);
        p2.setWpc(wpc);
        p2.addPrCard(new ShadesOfRed());
        p2.setFavorTokens(5);

        p3 = new Player(2);
        p3.setWpc(wpc);
        p3.addPrCard(new ShadesOfBlue());
        p3.setFavorTokens(5);


        //Set up roundtrack and public obj cards
        roundTrack = new RoundTrack(2);
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
        wpc2.setDie(2, 3, new Die(3, Colour.RED));
        wpc2.setDie(2, 4, new Die(3, Colour.GREEN));
        wpc2.setDie(3, 0, new Die(3, Colour.RED));
        wpc2.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc2.setDie(3, 2, new Die(3, Colour.RED));
        wpc2.setDie(3, 4, new Die(3, Colour.RED));

    }

    @Test
    public void test1() {
        ChooseWinner chooseWinner = new ChooseWinner(players, puCards, roundTrack);
        //Average game, no particolar scenario
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        try {
            assertEquals(chooseWinner.getWinner(), p2);
        } catch (NoWinnerException e) {
            fail();
        }
    }

    @Test
    public void test2(){
        //p1 and p3 have the same boards and cards -> same points, p2 should win due to roundtrack rule
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
}
