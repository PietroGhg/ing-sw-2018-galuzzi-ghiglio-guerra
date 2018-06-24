package it.polimi.se2018.view;

import it.polimi.se2018.controller.vcmessagecreator.VCGUIMessageCreator;
import it.polimi.se2018.controller.vcmessagecreator.VCMessageCreator;
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Prova extends Application {
    private VCGUIMessageCreator vcMessageCreator;
    private GUIcontroller view;
    private ModelRepresentation modelRep;

    @Override
    public void start(Stage primarystage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameWindow.fxml"));

        Parent root = loader.load();
        view = loader.getController();
        modelRep = new ModelRepresentation();
        view.init(modelRep);
        vcMessageCreator = new VCGUIMessageCreator(view, modelRep);
        view.rawRegister(vcMessageCreator);
        primarystage.setTitle("Sagrada");
        primarystage.setScene(new Scene(root, 1200, 800));
        primarystage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        primarystage.setResizable(false);
        primarystage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
