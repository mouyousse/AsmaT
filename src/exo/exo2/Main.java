package exo.exo2;

import exo.exo3.affichagescreen;
import exo.exo3.compteurscreen;
import exo.exo3.Summaryscreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        compteurscreen screen1=new compteurscreen();
        affichagescreen screen2=new affichagescreen();
        Summaryscreen screen3=new Summaryscreen();

        screen2.getButtonchangescene1().setOnAction(e -> stage.setScene(screen1.getScene()));
        screen2.getButtonchangescene3().setOnAction(e -> {
            screen3.update(screen1.getCounter(),screen2.getlabel().getText());
            stage.setScene(screen3.getScene());
        });
        screen1.getButtonchangescene2().setOnAction(e -> stage.setScene(screen2.getScene()));
        screen1.getButtonchangescene3().setOnAction(e -> {
            screen3.update(screen1.getCounter(),screen2.getlabel().getText());
            stage.setScene(screen3.getScene());
        });
        screen3.getto1().setOnAction(e -> stage.setScene(screen1.getScene()));
        screen3.getto2().setOnAction(e -> stage.setScene(screen2.getScene()));


        stage.setScene(screen1.getScene());
        stage.setTitle("switch page pooo une classe");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
