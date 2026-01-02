package exo.exo2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicInteger;

public class compteurscreen {
    private Scene scene;
    private Integer counter=0;
    private Label label;
    private Button button;
    private Button buttoncompt;
    private Button to3;
    public compteurscreen() {
        this.label = new Label("compteur :" + counter);
        this.button = new Button("to2");
        this.to3 = new Button("to3");
        this.buttoncompt = new Button("incr");
        this.buttoncompt.setOnAction(e -> {
            this.counter++;
            this.label.setText("compteur :" + counter);
        });
        HBox hBox = new HBox(10,to3,button);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(10,this.label,hBox,this.buttoncompt);
        vbox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vbox,400,400);
    }

    public Scene getScene() {
        return scene;
    }
    public Button getButtonchangescene2() {
        return button;
    }
    public Button getButtonchangescene3() {
        return this.to3;
    }
}
