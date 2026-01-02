package exo.exo1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicInteger;

public class ecran {
    private Scene scene;
    private Button buttonchangescene;
    AtomicInteger incr= new AtomicInteger(0);

    public ecran(String textebtn,String texteaccueil) {
        this.buttonchangescene= new Button(textebtn);
        Label label1= new Label(texteaccueil);
        VBox vbox = new VBox(10,label1,buttonchangescene);
        vbox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vbox,400,400);
    }
    public Scene getScene() {
        return scene;
    }
    public Button getButtonchangescene() {
        return this.buttonchangescene;
    }
}
