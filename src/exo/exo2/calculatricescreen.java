package exo.exo2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class calculatricescreen {
    private Scene scene;
    private Label label;
    private Button to1;
    private Button to2;
    private Button addition;
    private TextField input1;
    private TextField input2;
    public calculatricescreen() {
        this.label = new Label("resultat :");
        this.to1 = new Button("to1");
        this.to2 = new Button("to2");
        this.addition = new Button("addition");
        this.input1 = new TextField();
        this.input2 = new TextField();
        this.addition.setOnAction(e -> {

                int res = Integer.parseInt(input1.getText())+Integer.parseInt(input2.getText());

            this.label.setText("resultat :"+ res);

        });
        HBox hBox = new HBox(10,to1,to2);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(10,hBox,this.addition,this.input1,this.input2,this.label);
        vbox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vbox,300,300);
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
