package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import org.junit.*;

public class TestGrozingPliers {
    private PlayerMoveParameters param;
    private Player p;

    @Before
    public void setUp(){
        param = new PlayerMoveParameters(p);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(1);
        //da continuare


    }

}