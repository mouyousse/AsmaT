package Asmat;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccueilController {
    //data
    List<Enfant> enfants;
    //app graphique
    ComboBox<Double> choice;

    @FXML
    private ComboBox<String> TypeDuContrat;
    @FXML
    private ComboBox<String> DureeContrat;
    @FXML
    private HBox RepasBox;
    @FXML
    private Button createenfant;
    @FXML
    private VBox form;
    @FXML
    private HBox enfant;
    @FXML
    private Button createenfant1;
    @FXML
    private Button annulerbutton;
    @FXML
    private RadioButton rbReferentPere;
    @FXML
    private RadioButton rbReferentMere;
    @FXML
    private TextField merechamp;
    @FXML
    private TextField perechamp;
    @FXML
    private RadioButton Garcontoggle;
    @FXML
    private RadioButton Filletoggle;
    @FXML
    private TextField nomchamp;
    @FXML
    private TextField prenomchamp;
    @FXML
    private TextField lieuvie;
    @FXML
    private TextField adrpere;
    @FXML
    private TextField adrmere;
    @FXML
    private TextField telmere;
    @FXML
    private TextField telpere;
    @FXML
    private TextField Nemployeur;
    @FXML
    private TextField Semaines;
    @FXML
    private TextField NbHSemaine;
    @FXML
    private TextField Mensualisation;
    @FXML
    private TextField Tauxhoraire;
    @FXML
    private TextField Majoration;
    @FXML
    private TextField MajorationFevrier;
    @FXML
    private  DatePicker datenaiss;
    @FXML
    private DatePicker DateEmbauche;
    @FXML
    private CheckBox repas;
    @FXML
    private void initialize() {
        choice = new ComboBox<>();
        choice.setValue(0.0);
        choice.getItems().addAll(4.50,5.50,6.50);
        choice.setEditable(true);
        enfants = Main.enfants;
        if (enfants == null) {
            enfants = new ArrayList<>();
        }
        // Charger tous les enfants existants dans l'UI
        for (Enfant e : enfants) {
            ajouterEnfantUI(e);
        }

        ToggleGroup sexeGroup = new ToggleGroup();
        Filletoggle.setToggleGroup(sexeGroup);
        Garcontoggle.setToggleGroup(sexeGroup);
        ToggleGroup referentGroup = new ToggleGroup();
        rbReferentMere.setToggleGroup(referentGroup);
        rbReferentPere.setToggleGroup(referentGroup);
        Filletoggle.setSelected(true);
        createenfant.setOnAction(e -> {
            form.setVisible(true);
            enfant.setVisible(false);


        });
        Tauxhoraire.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                Tauxhoraire.setText(String.valueOf(Float.parseFloat(Tauxhoraire.getText())*0.78));
            }
        });
        annulerbutton.setOnAction(ev -> {
            reaform();
        });
        createenfant1.setOnAction(evf -> {
            senform();
        });

        repas.setOnAction(ev ->{
            if(repas.isSelected()){
                RepasBox.getChildren().add(choice);
            }
            else
                {
                    RepasBox.getChildren().remove(choice);
                }
        });


    }
    private void reaform() {
        nomchamp.clear();
        prenomchamp.clear();
        perechamp.clear();
        merechamp.clear();
        telmere.clear();
        telpere.clear();
        adrpere.clear();
        adrmere.clear();
        Tauxhoraire.clear();
        Majoration.clear();
        MajorationFevrier.clear();
        TypeDuContrat.getSelectionModel().clearSelection();
        DureeContrat.getSelectionModel().clearSelection();;
        Mensualisation.clear();
        Nemployeur.clear();
        datenaiss.setValue(null);
        DateEmbauche.setValue(null);

        repas.setSelected(false);
        RepasBox.getChildren().remove(choice);
        NbHSemaine.clear();
        Semaines.clear();
        choice.setValue(0.0);
        Filletoggle.setSelected(false);
        Garcontoggle.setSelected(false);
        rbReferentPere.setSelected(false);
        rbReferentMere.setSelected(false);
        form.setVisible(false);
        enfant.setVisible(true);
    }
    private void senform() {


        if (!nomchamp.getText().equals("") && !prenomchamp.getText().equals("")) {
            String nom = nomchamp.getText().trim();
            String pr = prenomchamp.getText().trim();

            String idpapa = perechamp.getText();
            String idmere = merechamp.getText();

            String adressePere = adrpere.getText();
            String adresseMere = adrmere.getText();

// Téléphones → String (CHOIX TECHNIQUEMENT JUSTIFIÉ)
            String telMere = telmere.getText();
            String telPere = telpere.getText();
            String lieudeVie=lieuvie.getText();
            boolean isPereReferent = rbReferentPere.isSelected();
            boolean isGarcon = Garcontoggle.isSelected();
            String nemployeur = Nemployeur.getText();

// Taux horaire
            float tauxhoraire = 0f;
            if (!Tauxhoraire.getText().trim().isEmpty()) {
                tauxhoraire = Float.parseFloat(Tauxhoraire.getText().trim());
            }

// Majorations
            float majoration = 0f;
            if (!Majoration.getText().trim().isEmpty()) {
                majoration = Float.parseFloat(Majoration.getText().trim());
            }

            float majorationFevrier = 0f;
            if (!MajorationFevrier.getText().trim().isEmpty()) {
                majorationFevrier = Float.parseFloat(MajorationFevrier.getText().trim());
            }

// Mensualisation
            float mensualisation = 0f;
            if (!Mensualisation.getText().trim().isEmpty()) {
                mensualisation = Float.parseFloat(Mensualisation.getText().trim());
            }

// Dates (DatePicker → LocalDate)
            LocalDate dateNaissance = datenaiss.getValue();
            LocalDate dateEmbauche = DateEmbauche.getValue();

// Semaines
            int semaines = 0;
            if (!Semaines.getText().trim().isEmpty()) {
                semaines = Integer.parseInt(Semaines.getText().trim());
            }

// Heures par semaine
            int nbHeuresSemaine = 0;
            if (!NbHSemaine.getText().trim().isEmpty()) {
                nbHeuresSemaine = Integer.parseInt(NbHSemaine.getText().trim());
            }
            double RepasPrix=0.0;
            boolean repasFourni = repas.isSelected();
            if(repasFourni) {
                RepasPrix = Double.parseDouble(String.valueOf(choice.getValue()));
            }

            String typecontrat=TypeDuContrat.getValue();
            String dureecontrat=DureeContrat.getValue();

            String id=nom+ " " + pr;
            Button newButton = new Button(id);
            String color ="";
            if (Garcontoggle.isSelected()) {
                color = "blue";
            }
            else {
                color = "pink";
            }
            ConfigurationEnfant configuration = new ConfigurationEnfant(nom,pr,isGarcon,idpapa,idmere,adressePere,adresseMere,telMere,telPere,isPereReferent,tauxhoraire,majoration,majorationFevrier,mensualisation,dateNaissance,dateEmbauche,semaines,nbHeuresSemaine,repasFourni,lieudeVie,nemployeur,RepasPrix,typecontrat,dureecontrat);
            Enfant e = new Enfant(configuration,id);
            e.setCoefficientBIndem(2.65);
            e.setCoefficientHIndem(0.425);
            enfants.add(e);  // ajouter à la liste globale
            EnfantRepository.save(enfants); // sauvegarder immédiatement
            ajouterEnfantUI(e);
        }
        reaform();
    }
    private void ajouterEnfantUI(Enfant e) {
        String id = e.getId();
        Button newButton = new Button(id);
        String color = e.getConfiguration().isGarcon() ? "blue" : "pink";

        newButton.setStyle(
                "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: #2c2c2c;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-color: linear-gradient(to bottom, beige, " + color + ");" +
                        "-fx-text-fill: #333;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        );
        newButton.setPrefWidth(300);
        newButton.setPrefHeight(100);

        Button deleteButton = new Button("🗑");
        deleteButton.setStyle("-fx-background-radius: 15; -fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12; -fx-padding: 3 6;");
        deleteButton.setPrefWidth(69);
        deleteButton.setPrefHeight(50);

        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(newButton, deleteButton);

        enfant.getChildren().add(container);

        // Action ouverture page enfant
        newButton.setOnAction(ev -> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Asmat/EnfantViews.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                EnfantController controller = loader.getController();
                controller.setEnfantData(e);
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/Asmat/images/icon.png")));
                stage.setTitle("Page de " + id);
                stage.sizeToScene();
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Action suppression
        deleteButton.setOnAction(eve -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment supprimer " + id);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    enfant.getChildren().remove(container);
                    enfants.remove(e); // supprimer aussi de la liste en mémoire
                    EnfantRepository.save(enfants); // et sauvegarder
                }
            });
        });
    }


}
