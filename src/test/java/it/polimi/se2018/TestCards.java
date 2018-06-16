package it.polimi.se2018;

import it.polimi.se2018.controller.toolcard.ToolCard;
import it.polimi.se2018.controller.toolcard.ToolCardFactory;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.model.objectivecards.ObjectiveCardFactory;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Tests that all the text resources are opened correctly
 */
public class TestCards {
    private void showFile(String name){
        String path = "/text/" + name;
        StringBuilder builder = new StringBuilder();
        InputStream in = getClass().getResourceAsStream(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = reader.readLine();
            }
            builder.append("\n");

        } catch (IOException e) { fail(); }
    }

    @Test
    public void test(){
        List<PublicObjectiveCard> puCards = new ArrayList<>();
        List<PrivateObjectiveCard> prCards = new ArrayList<>();
        List<ToolCard> toolCards = new ArrayList<>();
        ToolCardFactory toolCardFactory = new ToolCardFactory();
        ObjectiveCardFactory objectiveCardFactory = new ObjectiveCardFactory();

        for(int i = 0; i < 10; i++){
            try {
                puCards.add(objectiveCardFactory.getPublicObjectiveCard(i));
            }catch (InputNotValidException e){
                fail();
            }
        }

        for(int i = 0; i < 5; i++){
            try{
                prCards.add(objectiveCardFactory.getPrivateObjectiveCard(i));
            }
            catch (InputNotValidException e){
                fail();
            }
        }

        for(int i = 1; i <= 12; i++){
            try{
                toolCards.add(toolCardFactory.get(i));
            }
            catch (InputNotValidException e){
                fail();
            }
        }

        for(PrivateObjectiveCard card: prCards){
            showFile(card.getName());
        }
        for(PublicObjectiveCard card: puCards){
            showFile(card.getName());
        }
        for(ToolCard card: toolCards){
            showFile(card.getName());
        }
    }
}
