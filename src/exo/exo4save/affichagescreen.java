package exo.exo4save;

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
    private data data;
    private DataManager manager;
    public affichagescreen() {
        manager = new DataManager();
        data = manager.load();

        this.label = new Label(data.getText2());
        this.to1 = new Button("to1");
        this.affichage = new Button("afficher");
        this.textfield = new TextField();
        this.affichage.setOnAction((e)->{
           data.setText2(textfield.getText());
           label.setText(data.getText2());
           manager.save(data);
        });
        HBox hBox = new HBox(10,to1);
        hBox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(10,this.label,hBox,this.affichage,this.textfield);
        vbox.setAlignment(Pos.CENTER);
        this.scene = new Scene(vbox,400,400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }
    public Button getButtonchangescene1() {
        return this.to1;
    }
    public Label getlabel() {
        return label;
    }
}
