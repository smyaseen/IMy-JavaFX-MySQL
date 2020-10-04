package org.smy.imy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.smy.imy.datasource.Database;
import org.smy.imy.util.SceneSelector;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Pane signIn = FXMLLoader.load(getClass().getResource("view/signIn.fxml"));

        primaryStage.setScene(new Scene(signIn,600,400));
        primaryStage.setTitle("Imy");
        primaryStage.show();

        SceneSelector sceneSelector = new SceneSelector(primaryStage.getScene());

        Pane signUp = FXMLLoader.load(getClass().getResource("view/signUp.fxml"));
        Pane inventory = FXMLLoader.load(getClass().getResource("view/inventory.fxml"));

        sceneSelector.addScreen("inventory",inventory);
        sceneSelector.addScreen("signIn",signIn);
        sceneSelector.addScreen("signUp",signUp);


    }

    @Override
    public void init() throws Exception {

        if (!Database.getInstance().openDb()) {
            System.out.println("error opening database exiting application..");
            Platform.exit();
        }

    }

    @Override
    public void stop() throws Exception {
        Database.getInstance().closeDb();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
