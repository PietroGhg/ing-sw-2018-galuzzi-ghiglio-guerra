package it.polimi.se2018.model.wpc;

import javax.xml.parsers.*;
import it.polimi.se2018.model.Colour;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pietro Ghiglio
 *WPCGenerator generates a wpc given its numberID.
 *WPCs are stored as xml files in the directory /src/main/java/it.polimi.se2018/model/wpc/wpcs
 *xml format is
 *<wpc>
 *   <restriction type = "colour" or "value" row = "0,..,3" col = "0,..,4" value = "colour or number"></restriction>
 *  ... tags for name and favor token
 *</wpc>
 */
public class WpcGenerator {
    private  WPC temp;
    private Logger LOGGER = Logger.getLogger(WpcGenerator.class.getName());

    //The parser
    private class MyParser extends DefaultHandler {

        //This method is called with every opening tag in the xml file
        //reads row and column and sets restrictions in the correspondent cell
        @Override
        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                {
            if (qName.equals("restriction")) {
                String type = atts.getValue("type");
                int r = Integer.parseInt(atts.getValue("row"));
                int c = Integer.parseInt(atts.getValue("col"));
                if (type.equals("colour")) {
                    temp.getCell(r, c).setColourR(Colour.valueOf(atts.getValue("value")));
                }
                if (type.equals("value")) {
                    temp.getCell(r, c).setValueR(Integer.valueOf(atts.getValue("value")));
                }

                if (type.equals("favToken")) {
                    temp.setFavorTokens(Integer.valueOf(atts.getValue("value")));
                }
            }

            if(qName.equals("favorTokens")) {
                temp.setFavorTokens(Integer.parseInt(atts.getValue("value")));
            }

            if(qName.equals("name")) {
                temp.setName(atts.getValue("value"));
            }
        }

    }

    //returns the wpc identified by the ID
    public WPC getWPC (int wpcID) {
        temp = new WPC();
        try {
            //Opens the right file
            String workingDir = System.getProperty("user.dir");
            File in = new File(workingDir + "/src/main/java/it/polimi/se2018/model/wpc/wpcs/" + wpcID +".xml");

            //Standard instantation and use of a sax parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MyParser par = new MyParser();
            saxParser.parse(in, par);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return  temp;
    }

    public int getNumWpcs(){
        String workingDir = System.getProperty("user.dir");
        int numWpcs = 0;
        File in = new File(workingDir + "/src/main/java/it/polimi/se2018/model/wpc/wpcs/numWpcs.cfg");
        try(Scanner scanIn = new Scanner(in)) {
            //Opens the right file
           numWpcs = scanIn.nextInt();

        }
        catch(FileNotFoundException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return numWpcs;
    }

}
