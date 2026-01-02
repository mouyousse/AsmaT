package exo.exo5bouton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

        @Override
        public void start(Stage stage) {

            // Layout pour les boutons dynamiques
            Accueil accueil = new Accueil();
            pagesecondaire pages = new pagesecondaire(stage,accueil.getmainLayout().getScene());


            // Action pour créer un nouveau bouton à chaque clic
            accueil.getButtonsLayout().setOnAction(e -> {
                Button newButton = new Button("Aller sur l'autre page");
                newButton.setOnAction(ev -> stage.setScene(pages.getScene()));
                accueil.setButtonsLayout(newButton);
            });

            stage.setScene(accueil.getScene());
            stage.setTitle("Boutons dynamiques");
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }

