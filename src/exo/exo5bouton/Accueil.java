package exo.exo5bouton;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Accueil {
    private Scene accueil;
    private VBox buttonsLayout = new VBox(5);
    private VBox mainLayout = new VBox(10);
    private Label label = new Label("Clique sur Ajouter pour créer un bouton");
    private Button addButton = new Button("Ajouter un bouton");
    public Accueil() {
        buttonsLayout.setAlignment(Pos.CENTER);
        mainLayout.setAlignment(Pos.CENTER);
        this.accueil = new Scene(mainLayout, 400, 300);
        mainLayout.getChildren().addAll(label, addButton, buttonsLayout);
    }
    public Button getButtonsLayout() {
        return this.addButton;
    }
    public void setButtonsLayout(Button addButton) {
        this.buttonsLayout.getChildren().add(addButton) ;
    }
    public VBox getmainLayout() {
        return mainLayout;
    }
    public Scene getScene() {
        return accueil;
    }

}
