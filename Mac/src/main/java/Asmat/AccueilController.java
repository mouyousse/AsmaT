package Asmat;

import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccueilController {
    //data
    List<Enfant> enfants;
    //app graphique
    ComboBox<Double> choice;
    TextField hrssupptext;

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
    private TextField Entretienmoins;
    @FXML
    private TextField Entretienplus;
    @FXML
    private Button okInd;
    @FXML
    private Button annuleInd;
    @FXML
    private StackPane settingsInd;
    @FXML
    private VBox IndBox;
    @FXML
    private TextField Asmatnom;
    @FXML
    private TextField Asmatprenom;
    @FXML
    private TextField Asmatanum;
    @FXML
    private TextField Asmatanumsalary;
    @FXML
    private TextField Asmatadr;
    @FXML
    private TextField Jourssemaine;
    @FXML
    private CheckBox hrssupp;
    @FXML
    private HBox hrssuppbox;
    @FXML
    private void initialize() {
        hrssupptext=new TextField();
        hrssupptext.setPromptText("Hrssupp");
        choice = new ComboBox<>();
        choice.setValue(0.0);
        choice.getItems().addAll(4.50,5.50,6.50);
        choice.setEditable(true);
        if (enfants == null) {
            enfants = new ArrayList<>();
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
            IndBox.setVisible(false);

        });
        final boolean[] converted = {false};

        Tauxhoraire.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                converted[0] = false; // nouvelle valeur => prochaine perte de focus recalculera
            }
        });

        Mensualisation.setOnMouseClicked(event -> {
            if(Tauxhoraire.getText().trim().equals("") || NbHSemaine.getText().trim().equals("") || Semaines.getText().trim().equals("")){
                affichemessagealert("le TauxHoraire et les Semaines ainsi que le Nombre d'heures par semaine ne doivent pas etre vide");
            }
            else {
                if(Majoration.getText().trim().equals("")){
                    Majoration.setText("0");
                }
                if(hrssupptext.getText().trim().equals("")){
                    hrssupptext.setText("0");
                }
                Mensualisation.setText(arrondi2(Float.parseFloat(Tauxhoraire.getText())*(Float.parseFloat(NbHSemaine.getText())+Integer.parseInt(hrssupptext.getText())*Float.parseFloat(Majoration.getText()))*Float.parseFloat(Semaines.getText())/12));
            }
        });

        annulerbutton.setOnAction(ev -> {
            reaform();
        });
        createenfant1.setOnAction(evf -> {
            if(Tauxhoraire.getText().trim().equals("") || NbHSemaine.getText().trim().equals("") || Semaines.getText().trim().equals("")){
                affichemessagealert("le TauxHoraire et les Semaines ainsi que le Nombre d'heures par semaine ne doivent pas etre vide");
            }
            else {
                if(Majoration.getText().trim().equals("")){
                    Majoration.setText("0");
                }
                if(hrssupp.getText().trim().equals("")){
                    hrssupp.setText("0");
                }
                Mensualisation.setText(arrondi2(Float.parseFloat(Tauxhoraire.getText())*(Float.parseFloat(NbHSemaine.getText())+Integer.parseInt(hrssupptext.getText())*Float.parseFloat(Majoration.getText()))*Float.parseFloat(Semaines.getText())/12));
            }
            senform();
        });

        // Paramètres globaux d'indemnité d'entretien : indépendants de tout Enfant
        ParametresEntretien parametres = ParametresEntretien.getInstance();
        AsmatInfo info = AsmatInfo.getInstance();
        Asmatnom.setText(info.getNom());
        Asmatprenom.setText(info.getPrenom());
        Asmatanum.setText(info.getTelephone());
        Asmatanumsalary.setText(info.getNumeroSalarie());
        Asmatadr.setText(info.getAdresse());
        Entretienmoins.setText(String.valueOf(parametres.getCoefficientBIndem()));
        Entretienplus.setText(String.valueOf(parametres.getCoefficientHIndem()));

        okInd.setOnAction(ev -> {
            try {
                double nouvelleValeurB =
                        Double.parseDouble(Entretienmoins.getText().trim());

                double nouvelleValeurH =
                        Double.parseDouble(Entretienplus.getText().trim());


                parametres.setCoefficientBIndem(nouvelleValeurB);
                parametres.setCoefficientHIndem(nouvelleValeurH);

                ParametresEntretienRepository.save(parametres);

            } catch (NumberFormatException e) {
                affichemessagealert("Veuillez entrer des valeurs numériques valides.");
            }
            try{
                String nouveaunom = Asmatnom.getText().trim();
                String nouveauprenom = Asmatprenom.getText().trim();
                String nouvelleadr = Asmatadr.getText().trim();
                String nouveau_num= Asmatanum.getText().trim();
                String nouveau_Salary_num= Asmatanumsalary.getText().trim();

                info.setNom(nouveaunom);
                info.setPrenom(nouveauprenom);
                info.setTelephone(nouveau_num);
                info.setAdresse(nouvelleadr);
                info.setNumeroSalarie(nouveau_Salary_num);

                AsmatInfoRepo.save(info);
            }
            catch (NumberFormatException e) {
                affichemessagealert("Veuillez entrer du texte valide.");
            }
            IndBox.setVisible(false);
            enfant.setVisible(true);
        });
        annuleInd.setOnAction(ev -> {

            Entretienmoins.setText(
                    String.valueOf(parametres.getCoefficientBIndem())
            );

            Entretienplus.setText(
                    String.valueOf(parametres.getCoefficientHIndem())
            );
            Asmatanum.setText(info.getTelephone());
            Asmatadr.setText(info.getAdresse());
            Asmatanumsalary.setText(info.getNumeroSalarie());
            Asmatnom.setText(info.getNom());
            Asmatprenom.setText(info.getPrenom());
            IndBox.setVisible(false);
            enfant.setVisible(true);
        });
      settingsInd.setOnMouseClicked(mouseEvent ->{
            form.setVisible(false);
            enfant.setVisible(false);
        IndBox.setVisible(true);
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
        hrssupp.setOnAction(actionEvent ->{
            if(hrssupp.isSelected()){
                hrssuppbox.getChildren().add(hrssupptext);
            }
            else
            {
                hrssupptext.clear();
                hrssuppbox.getChildren().remove(hrssupptext);
            }
        });

    }
    /**
     * Arrondit et formate un nombre à 2 décimales (format FR, virgule).
     */
    private String arrondi2(double value) {
        return String.format(Locale.US, "%.2f", value);
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
        lieuvie.clear();
        repas.setSelected(false);
        RepasBox.getChildren().remove(choice);
        NbHSemaine.clear();
        Semaines.clear();
        choice.setValue(0.0);
        Filletoggle.setSelected(false);
        Garcontoggle.setSelected(false);
        rbReferentPere.setSelected(false);
        rbReferentMere.setSelected(false);
        hrssupptext.clear();
        hrssuppbox.getChildren().remove(hrssupptext);
        hrssupp.setSelected(false);

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
            boolean repasFourni=repas.isSelected();
            if(repas.isSelected()) {
                RepasPrix = Double.parseDouble(String.valueOf(choice.getValue()));
            }
            int hrssuppv=0;

            if(hrssupp.isSelected() && !hrssupptext.getText().isEmpty()) {
                try {
                    hrssuppv = Integer.parseInt(hrssupptext.getText());
                }
                catch (NumberFormatException e) {
                    affichemessagealert("rentrez un nombre");
                }
            }
            String typecontrat=TypeDuContrat.getValue();
            String dureecontrat=DureeContrat.getValue();
            int jourmois=0;
            try{
                jourmois= Integer.parseInt(Jourssemaine.getText());
            }
           catch(NumberFormatException e){
                affichemessagealert("rentrez un nombre pour les jours par mois");
           }
            String id=nom+ " " + pr;
            Button newButton = new Button(id);
            String color ="";
            if (Garcontoggle.isSelected()) {
                color = "blue";
            }
            else {
                color = "pink";
            }
            ConfigurationEnfant configuration = new ConfigurationEnfant(nom,pr,isGarcon,idpapa,idmere,adressePere,adresseMere,telMere,telPere,isPereReferent,tauxhoraire,majoration,majorationFevrier,mensualisation,dateNaissance,dateEmbauche,semaines,nbHeuresSemaine,repasFourni,lieudeVie,nemployeur,RepasPrix,typecontrat,dureecontrat,jourmois,hrssuppv);
            Enfant e = new Enfant(configuration,id);
            enfants.add(e);  // ajouter à la liste globale
            EnfantRepository.save(enfants); // sauvegarder immédiatement
            ajouterEnfantUI(e);
            reaform();
        }
        else {
            affichemessagealert("Veuillez rentrer un nom et prenom");
        }

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
        newButton.setCursor(Cursor.HAND);
        newButton.setPrefWidth(300);
        newButton.setPrefHeight(100);

        Button deleteButton = new Button("🗑");
        deleteButton.setStyle("-fx-background-radius: 15; -fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12; -fx-padding: 3 6;");
        deleteButton.setPrefWidth(69);
        deleteButton.setPrefHeight(50);
        deleteButton.setCursor(Cursor.HAND);
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
                controller.setenfants(enfants);
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
    private void affichemessagealert(String Message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(Message);
        alert.showAndWait();
    }
    public void setenfants(List<Enfant> enfants){
        this.enfants = enfants;
        updateUI();
    }
    private void updateUI() {
        enfant.getChildren().clear();

        if (enfants == null) return;

        for (Enfant e : enfants) {
            ajouterEnfantUI(e);
        }
    }
}