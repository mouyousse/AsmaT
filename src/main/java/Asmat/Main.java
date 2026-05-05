package Asmat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Locale;

public class Main extends Application {

    public List<Enfant> enfants;

    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(Locale.FRENCH);
        enfants = EnfantRepository.load();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Asmat/AccueilViews.fxml")
        );
        Parent root = loader.load();

        AccueilController controller = loader.getController();
        controller.setenfants(enfants);

        Scene scene = new Scene(root);

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
