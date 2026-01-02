package exo.exo1;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.concurrent.atomic.AtomicInteger;


public class exo1 {

        public void start(Stage stage) {
            AtomicInteger incr= new AtomicInteger(0);

            //exo.exo1.ecran 1
            //attribut
            Button buttonchangeto12 = new Button("ecran2");
            Button buttonchangeto13= new Button("ecran3");
            Button buttonchangeto11= new Button("ecran1");
            Label label = new Label("compteur :" + incr.get());
            label.setStyle("-fx-text-fill: green;");
            Button button = new Button("Clique-moi");

            //methode
            button.setOnAction(event -> {
                incr.incrementAndGet();
                label.setText("compteur : " + incr.get());

                setcolorlabel(label,incr);
            } );
            //setscene1
            VBox layout1 = new VBox(20);
            layout1.setAlignment(Pos.CENTER);
            HBox menu = new HBox(20,buttonchangeto11, buttonchangeto12,buttonchangeto13);
            menu.setAlignment(Pos.TOP_CENTER);
            layout1.getChildren().addAll(menu,label,button);
            Scene scene1 = new Scene(layout1, 400, 300);
            //exo.exo1.ecran 2
            //cree attribut
            Button buttonchangeto22= new Button("ecran2");
            Button buttonchangeto23= new Button("ecran3");
            Button buttonchangeto21= new Button("ecran1");
            Label label1 = new Label("Bienvenue sur l'exo.exo1.ecran 2");
            //methode
            //setscene2
            VBox layout2 = new VBox(20);
            layout2.setAlignment(Pos.CENTER);
            HBox menu2 = new HBox(20,buttonchangeto21, buttonchangeto22,buttonchangeto23);
            menu.setAlignment(Pos.TOP_CENTER);
            layout2.getChildren().addAll(menu2,label1);
            Scene stage2 = new Scene(layout2, 450, 300);
            //exo.exo1.ecran 3
            //attribut
            Button buttonchangeto32= new Button("ecran2");
            Button buttonchangeto33= new Button("ecran3");
            Button buttonchangeto31= new Button("ecran1");
            Label label2 = new Label("Ecran 3 ici");
            //setscene3
            VBox layout3 = new VBox(20);
            layout3.setAlignment(Pos.CENTER);
            HBox menu3 = new HBox(20,buttonchangeto31, buttonchangeto32,buttonchangeto33);
            menu3.setAlignment(Pos.TOP_CENTER);
            layout3.getChildren().addAll(menu3,label2);
            Scene stage3 = new Scene(layout3, 450, 300);
            buttonchangeto11.setOnAction(event -> stage.setScene(scene1));
            buttonchangeto12.setOnAction(event -> stage.setScene(stage2));
            buttonchangeto13.setOnAction(event -> stage.setScene(stage3));
            buttonchangeto21.setOnAction(event -> stage.setScene(scene1));
            buttonchangeto22.setOnAction(event -> stage.setScene(stage2));
            buttonchangeto23.setOnAction(event -> stage.setScene(stage3));
            buttonchangeto31.setOnAction(event -> stage.setScene(scene1));
            buttonchangeto32.setOnAction(event -> stage.setScene(stage2));
            buttonchangeto33.setOnAction(event -> stage.setScene(stage3));
            //affichage final
            stage.setTitle("Exo");
            stage.setScene(scene1);
            stage.show();
        }
        public void setcolorlabel(Label label,AtomicInteger incr){
            if (incr.get()>5 && incr.get()<=10) {
                label.setStyle("-fx-text-fill: orange;");
            }
            else if(incr.get()>10) {
                label.setStyle("-fx-text-fill: red;");
            }
        }

    }

