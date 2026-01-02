package Asmat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Asmat/ressources/AccueilViews.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/Asmat/ressources/images/icon.png"))
        );
        stage.setTitle("AsmaT");
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
