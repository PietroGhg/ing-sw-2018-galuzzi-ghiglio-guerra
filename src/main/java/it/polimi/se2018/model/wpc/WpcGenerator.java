package it.polimi.se2018.model.wpc;

import javax.xml.parsers.*;
import it.polimi.se2018.model.Colour;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    private static final String VALUE = "value";
    private static final Logger LOGGER = Logger.getLogger(WpcGenerator.class.getName());
    private List<WPC> extras;
    private static WpcGenerator instance;

    private WpcGenerator(){
        extras = new ArrayList<>();
        loadExtraWPCs();
    }

    public static WpcGenerator getInstance(){
        if(instance == null) instance = new WpcGenerator();
        return instance;
    }

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
                    temp.getCell(r, c).setColourR(Colour.valueOf(atts.getValue(VALUE)));
                }
                if (type.equals(VALUE)) {
                    temp.getCell(r, c).setValueR(Integer.valueOf(atts.getValue(VALUE)));
                }

                if (type.equals("favToken")) {
                    temp.setFavorTokens(Integer.valueOf(atts.getValue(VALUE)));
                }
            }

            if(qName.equals("favorTokens")) {
                temp.setFavorTokens(Integer.parseInt(atts.getValue(VALUE)));
            }

            if(qName.equals("name")) {
                temp.setName(atts.getValue(VALUE));
            }
        }

    }

    //returns the wpc identified by the ID
    public WPC getWPC(int wpcID){
        if(wpcID <= 24) return getStandardWPC(wpcID);
        else return getExtraWpc(wpcID);
    }

    private WPC getStandardWPC (int wpcID) {
        temp = new WPC();
        try {
            //Opens the right file

            String path = "/wpcs/" + wpcID +".xml";

            InputStream in = getClass().getResourceAsStream(path);

            //Standard instantation and use of a sax parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MyParser par = new MyParser();
            saxParser.parse(in, par);
            temp.setId(wpcID);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return  temp;
    }

    private WPC getExtraWpc(int wpcID){
        return extras.get(wpcID-25);
    }

    private void loadExtraWPCs(){
        try {
            String path = "./schemas";
            File wpcPath = new File(path);
            File[] wpcs = wpcPath.listFiles();
            for (int i = 0; i < wpcs.length; i++) {
                File f = wpcs[i];
                try {
                    temp = new WPC();
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    MyParser par = new MyParser();
                    saxParser.parse(f, par);
                    temp.setId(24 + i);
                    extras.add(new WPC(temp));
                    String s = temp.getName() + " loaded.";
                    LOGGER.log(Level.INFO, s);
                } catch (SAXException | IOException | ParserConfigurationException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
            }
        }
        catch(NullPointerException e){
            LOGGER.log(Level.INFO, "No extra schemas loaded.");
        }
    }

    public int getNumWpcs(){
        return 24 + extras.size();
    }

}
