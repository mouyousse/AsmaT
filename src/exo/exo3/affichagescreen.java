package exo.exo3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class affichagescreen {
    private Scene scene;
    private Label label;
    private Button affichage;
    private Button to1;
    private Button to3;
    private TextField textfield;
    public affichagescreen() {
        this.label = new Label("ecran2 :");
        this.to1 = new Button("to1");
        this.to3 = new Button("to3");
        this.affichage = new Button("afficher");
        this.textfield = new TextField();
        this.affichage.setOnAction((e)->{
            this.label.setText(this.textfield.getText());
        });
        HBox hBox = new HBox(10,to1,to3);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(10,this.label,hBox,this.affichage,this.textfield);
        vbox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vbox,400,400);
    }

    public Scene getScene() {
        return scene;
    }
    public Button getButtonchangescene1() {
        return this.to1;
    }
    public Button getButtonchangescene3() {
        return this.to3;
    }
    public Label getlabel() {
        return label;
    }
}
