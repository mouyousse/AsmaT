package exo.exo4save;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class exo4Main extends Application {
    public void start(Stage stage) {
        CompteurScreen screen1 = new CompteurScreen();
        affichagescreen screen2 = new affichagescreen();
        screen2.getButtonchangescene1().setOnAction(e -> stage.setScene(screen1.getScene()));
        screen1.getButtonchangescene2().setOnAction(e -> stage.setScene(screen2.getScene()));
        stage.setScene(screen1.getScene());
        stage.setTitle("Compteur persistant");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
