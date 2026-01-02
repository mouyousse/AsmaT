package exo.exo4save;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CompteurScreen {
    private Scene scene;
    private Label label;
    private TextField textField;
    private Button incrButton;
    private Button updateTextButton;
    private data data;
    private DataManager manager;
    private Button changescene2;

    public CompteurScreen() {
        manager = new DataManager();
        data = manager.load(); // charger les données depuis JSON

        changescene2 = new Button("changescene2");
        label = new Label("Compteur : " + data.getCounter());
        textField = new TextField(data.getText());
        incrButton = new Button("Incrémenter");
        updateTextButton = new Button("Changer texte");

        incrButton.setOnAction(e -> {
            data.setCounter(data.getCounter() + 1);
            label.setText("Compteur : " + data.getCounter());
            manager.save(data);
        });

        updateTextButton.setOnAction(e -> {
            data.setText(textField.getText());
            label.setText(data.getText());
            manager.save(data);
        });

        VBox layout = new VBox(15, label, incrButton, textField, updateTextButton, changescene2);
        layout.setAlignment(Pos.CENTER);
        scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    }

    public Scene getScene() {
        return scene;
    }
    public Button getButtonchangescene2() {
        return changescene2;
    }
}
