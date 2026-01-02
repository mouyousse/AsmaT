package exo.exo3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Summaryscreen {
    private Scene scene;
    private Label label;
    private Label message2;
    private Button to1;
    private Button to2;

    public Summaryscreen() {
        this.label = new Label();
        this.message2 = new Label();
        this.to1 = new Button("to1");
        this.to2 = new Button("to2");


        HBox hBox = new HBox(10,to1,to2);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(10,hBox,this.message2,this.label);
        vbox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vbox,300,300);
    }
    public void update(int counter, String message){
        this.label.setText("counter :"+counter);
        this.message2.setText(message);
    }

    public Scene getScene() {
        return this.scene;
    }
    public Button getto1() {
        return this.to1;
    }
    public Button getto2() {
        return this.to2;
    }
}
