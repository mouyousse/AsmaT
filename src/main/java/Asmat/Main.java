package Asmat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.List;

public class Main extends Application {

    public static List<Enfant> enfants;

    @Override
    public void start(Stage stage) throws Exception {
        enfants = EnfantRepository.load();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Asmat/AccueilViews.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/Asmat/images/icon.png"))
        );
        stage.setTitle("AsmaT");
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
        stage.setOnCloseRequest(event -> {
            EnfantRepository.save(enfants);
        });

    }
    public void stop() {
        EnfantRepository.save(enfants);
        System.out.println("Données sauvegardées");
    }

    public static void main(String[] args) {
        launch();
    }
}
