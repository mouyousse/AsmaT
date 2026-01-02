package exo.exo5bouton;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class pagesecondaire {
    private Scene scene;
    private Button backButton = new Button("Retour");
    public pagesecondaire(Stage stage,Scene mainScene) {
        VBox secondaryLayout = new VBox(20, new Label("Tu es sur l'autre page !"));
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.getChildren().add(backButton);
        this.scene = new Scene(secondaryLayout, 400, 300);
        backButton.setOnAction(e -> stage.setScene(mainScene));
    }
    public Scene getScene() {
        return scene;
    }
}
