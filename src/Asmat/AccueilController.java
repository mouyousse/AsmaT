package Asmat;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import java.util.ResourceBundle;

public class AccueilController {

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
    private TextField TauxhoraireNet;
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
        annulerbutton.setOnAction(e -> {
            reaform();
        });
        createenfant1.setOnAction(e -> {
            senform();
        });

    }
    private void reaform() {
        nomchamp.clear();
        prenomchamp.clear();
        perechamp.clear();
        merechamp.clear();
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
            float tauxHoraireNet = 0f;
            if (!TauxhoraireNet.getText().trim().isEmpty()) {
                tauxHoraireNet = Float.parseFloat(TauxhoraireNet.getText().trim());
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

            boolean repasFourni = repas.isSelected();

            String id=nom+ " " + pr;
            Button newButton = new Button(id);
            String color ="";
            if (Garcontoggle.isSelected()) {
                color = "blue";
            }
            else {
                color = "pink";
            }
            ConfigurationEnfant configuration = new ConfigurationEnfant(nom,pr,isGarcon,idpapa,idmere,adressePere,adresseMere,telMere,telPere,isPereReferent,tauxHoraireNet,majoration,majorationFevrier,mensualisation,dateNaissance,dateEmbauche,semaines,nbHeuresSemaine,repasFourni,lieudeVie,nemployeur);
            Enfant e = new Enfant(configuration,id);
            newButton.setStyle(
                    "-fx-background-radius: 20;" +               // coins arrondis
                            "-fx-border-radius: 20;" +                   // coins arrondis pour la bordure
                            "-fx-border-color: #2c2c2c;" +              // couleur de bordure
                            "-fx-border-width: 2;" +                     // épaisseur de la bordure
                            "-fx-background-color: linear-gradient(to bottom, beige, " + color + ");" + // dégradé jaune clair
                            "-fx-text-fill: #333;" +                     // couleur du texte sombre
                            "-fx-font-weight: bold;" +                   // texte en gras
                            "-fx-font-size: 16;" +                       // taille du texte
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);" // légère ombre
            );
            // Optionnel : taille du bouton
            newButton.setPrefWidth(300);
            newButton.setPrefHeight(100);

            Button deleteButton = new Button("🗑"); // ou une image d'icône
            deleteButton.setStyle("-fx-background-radius: 15; -fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12; -fx-padding: 3 6;");
            deleteButton.setPrefWidth(69);
            deleteButton.setPrefHeight(50);

            VBox container = new VBox(5); // 5 px d'espacement
            container.setAlignment(Pos.CENTER);
            container.getChildren().addAll(newButton, deleteButton);

// Ajouter la VBox dans ton HBox principal
            enfant.getChildren().add(container);

            newButton.setOnAction(ev -> {
                // Création d'une nouvelle scène
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/Asmat/ressources/EnfantViews.fxml")
                );

                try {
                    Scene scene = new Scene(loader.load());
                    EnfantController controller = loader.getController();
 // id = nom + prénom
                    stage.setScene(scene);
                    stage.getIcons().add(
                            new Image(getClass().getResourceAsStream("/Asmat/ressources/images/icon.png"))
                    );
                    stage.setTitle("Page de " + id);
                    controller.setEnfantData(e);
                    stage.sizeToScene();
                    stage.centerOnScreen();
                    stage.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            });
// Action de suppression
            deleteButton.setOnAction(eve -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Voulez-vous vraiment supprimer "+ id);

                // Afficher la boîte de dialogue et attendre la réponse
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        enfant.getChildren().remove(container);
                    }
                });
            });

        }
        reaform();
    }

}
