package Asmat;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

public class EnfantController {
    //data
    List<Enfant> enfants;
    //Page de base
    // année -> (mois -> fiche)
    private static final String[] COULEURS = {
            "#6aa84f", // vert
            "#e69138", // orange
            "#6fa8dc"  // bleu
    };
    private Enfant enfant;
    private File file;
     ComboBox<Double> choice;
    @FXML
    private TextField Entretienmoins;
    @FXML
    private TextField Entretienplus;
    @FXML
    private Label titleEnfant;
    @FXML
    private Button AjouterButton;
    @FXML
    private Button DossierButton;
    @FXML
    private Button ModiferConfigButton;
    //Page de Ajout/modif
    @FXML
    private VBox AjoutForm;
    @FXML
    private ComboBox<String> Moisfiche;
    @FXML
    private TextField AnneeFiche;
    @FXML
    private Button ValiderButton;
    @FXML
    private Button Affichage;
    @FXML
    private ScrollPane ScrollDay;
    @FXML
    private VBox Daysvbox;
    @FXML
    private VBox Ajoutjour;
    @FXML
    private Button ExporterPDF;
    //Recap
    @FXML
    private Text Nbrdejoursactivites;
    @FXML
    private Button createenfant1;
    @FXML
    private Text Heures;
    @FXML
    private Text Repas;
    @FXML
    private Text IndemniteEntretien;
    @FXML
    private Text Ajustement;
    @FXML
    private Text SalaireNet;
    //Page d'ajout de jour
    @FXML
    private TextField Heurearrive;
    @FXML
    private TextField heuredepart;
    @FXML
    private TextField IndRepas;
    @FXML
    private TextField IndEntretien;
    @FXML
    private TextField TextAjustement;
    @FXML
    private TextField Commentaire;
    @FXML
    private Button ValiderButtonDay;
    @FXML
    private Button CancelButtonDay;
    //config
    @FXML
    private VBox config;
    @FXML
    private ComboBox<String> TypeDuContrat;
    @FXML
    private ComboBox<String> DureeContrat;
    @FXML
    private HBox RepasBox;
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
    public void initialize()
    {


        Entretienmoins.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                enfant.setCoefficientBIndem(Double.parseDouble(Entretienmoins.getText()));
            }
        });
        Entretienplus.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                enfant.setCoefficientHIndem(Double.parseDouble(Entretienplus.getText()));
            }
        });
        choice = new ComboBox<>();
        choice.setValue(0.0);
        choice.getItems().addAll(4.50,5.50,6.50);
        choice.setEditable(true);
        enfants=Main.enfants;
        //calculHeurenet
        final boolean[] converted = {false};
        Tauxhoraire.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                converted[0] = false; // nouvelle valeur => prochaine perte de focus recalculera
            }
        });

        Tauxhoraire.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !converted[0]) {
                try {
                    float valeur = Float.parseFloat(Tauxhoraire.getText());
                    Tauxhoraire.setText(String.valueOf(valeur * 0.78f));
                    converted[0] = true;
                } catch (NumberFormatException e) {
                    Tauxhoraire.setText("");
                }
            }
        });
        Mensualisation.setOnMouseClicked(event -> {
            if(Tauxhoraire.getText().trim().equals("") || NbHSemaine.getText().trim().equals("") || Semaines.getText().trim().equals("")){
                affichemessagealert("le TauxHoraire et les Semaines ainsi que le Nombre d'heures par semaine ne doivent pas etre vide");
            }
            else {
                Mensualisation.setText(String.valueOf(Float.parseFloat(Tauxhoraire.getText())*Float.parseFloat(NbHSemaine.getText())*Float.parseFloat(Semaines.getText())/12));
            }
        });

        // Charger tous les enfants existants dans l'UI

        AnneeFiche.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        AjouterButton.setOnAction(e -> {
            AjoutForm.setVisible(true);
            AnneeFiche.setText(LocalDate.now().getYear()+"");

        });
            Affichage.setOnAction(ev -> {
                String anneeText = AnneeFiche.getText();
                String moisChoisi = Moisfiche.getSelectionModel().getSelectedItem();

                if (anneeText.isEmpty() || moisChoisi == null || moisChoisi.isEmpty()) {
                    affichemessagealert("Veuillez saisir l'année et sélectionner un mois.");
                    return;
                }

                int year = Integer.parseInt(anneeText);
                AffichageFp(year, moisChoisi);
            });

            ValiderButton.setOnAction(eve -> AjoutForm.setVisible(false));

        DossierButton.setOnAction(e -> {
            creerdossier();
            openFolder(file);

        });
        ModiferConfigButton.setOnAction(ev -> {
            repas.setOnAction(eve->{
                repasverif();
            });
            ToggleGroup referentGroup = new ToggleGroup();
            rbReferentMere.setToggleGroup(referentGroup);
            rbReferentPere.setToggleGroup(referentGroup);
            ToggleGroup sexeGroup = new ToggleGroup();
            Filletoggle.setToggleGroup(sexeGroup);
            Garcontoggle.setToggleGroup(sexeGroup);
            config.setVisible(true);
            ConfigurationEnfant c = enfant.getConfiguration();
            prenomchamp.setText(c.getPrenom());
            nomchamp.setText(c.getNom());
            merechamp.setText(c.getMere());
            perechamp.setText(c.getPere());
            lieuvie.setText(c.getLieudeVie());
            adrmere.setText(c.getAdresseMere());
            adrpere.setText(c.getAdressePere());
            telmere.setText(c.getTelmere());
            telpere.setText(c.getTelpere());
            Nemployeur.setText(c.getNemployeur());
            Semaines.setText(String.valueOf(c.getSemaines()));
            NbHSemaine.setText(String.valueOf(c.getNbHeuresSemaine()));
            Majoration.setText(String.valueOf(c.getMajoration()));
            Mensualisation.setText(String.valueOf(c.getMensualisation()));
            Tauxhoraire.setText(String.valueOf(c.getTauxHoraireNet()));
            MajorationFevrier.setText(String.valueOf(c.getMajorationfevrier()));
            datenaiss.setValue(c.getDateNaissance());
            DateEmbauche.setValue(c.getDateEmbauche());
            rbReferentPere.setSelected(c.isPereReferent());
            Garcontoggle.setSelected(c.isGarcon());
            repas.setSelected(c.isRepasFourni());
            repasverif();
            TypeDuContrat.setValue(c.getTypeContrat());
            DureeContrat.setValue(c.getDureeContrat());
           if(!choice.getItems().contains(c.getRepasPrix())) {
                choice.getItems().add(c.getRepasPrix());
           }
            choice.setValue(c.getRepasPrix());
        });
        createenfant1.setOnAction(ev -> {
            ConfigurationEnfant c = enfant.getConfiguration();

            // Identité
            c.setPrenom(prenomchamp.getText().trim());
            c.setNom(nomchamp.getText().trim());
            c.setMere(merechamp.getText());
            c.setPere(perechamp.getText());
            c.setLieudeVie(lieuvie.getText());
            c.setAdresseMere(adrmere.getText());
            c.setAdressePere(adrpere.getText());
            c.setTelmere(telmere.getText());
            c.setTelpere((telpere.getText()));
            //repas
          if (choice.getValue() != null) {
              c.setRepasPrix(Double.valueOf(String.valueOf(choice.getValue())));

          }


            // Référent
            c.setPereReferent(rbReferentPere.isSelected());

            // Sexe
            c.setGarcon(Garcontoggle.isSelected());

            // Données contrat
            c.setNemployeur(Nemployeur.getText().trim());

            c.setSemaines(Integer.parseInt(Semaines.getText().trim()));
            c.setNbHeuresSemaine(Float.parseFloat(NbHSemaine.getText().trim()));
            c.setTauxHoraireNet(Float.parseFloat(Tauxhoraire.getText().trim()));
            c.setMajoration(Float.parseFloat(Majoration.getText().trim()));
            c.setMajorationfevrier(Float.parseFloat(MajorationFevrier.getText().trim()));
            c.setMensualisation(Float.parseFloat(Mensualisation.getText().trim()));

            // Dates
            c.setDateNaissance(datenaiss.getValue());
            c.setDateEmbauche(DateEmbauche.getValue());
            //contrat
           c.setTypeContrat(TypeDuContrat.getValue());
           c.setDureeContrat(DureeContrat.getValue());

            // Options
            c.setRepasFourni(repas.isSelected());


            // Fermer la fenêtre config
            config.setVisible(false);

            EnfantRepository.save(Main.enfants);
        });
        annulerbutton.setOnAction(ev -> config.setVisible(false));
        ExporterPDF.setOnAction(e -> {
            String anneeText = AnneeFiche.getText();
            String moisChoisi = Moisfiche.getSelectionModel().getSelectedItem();

            if (anneeText.isEmpty() || moisChoisi == null || moisChoisi.isEmpty()) {
                affichemessagealert("Veuillez saisir l'année et sélectionner un mois.");
                return;
            }

            int year = Integer.parseInt(anneeText);
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);

            moisChoisi = moisChoisi.toLowerCase(Locale.FRENCH);

            Month month = Month.from(formatter.parse(moisChoisi));
            YearMonth mois = YearMonth.of(year, month);
            Fp fp = enfant.getOrCreateFp(year, month);
            creerdossier();
            // crée si besoin

            File filePDF = new File(this.file, "Fiche_" + moisChoisi.toUpperCase() + "_" + year + ".pdf");

// ensuite ton code reste identique
            pdf p = new pdf(enfant, fp, filePDF);
            affichemessagealert("le pdf à était crée voir le dossier pour le trouver");
        });


    }

    /**
     * method pour ouvrir le dossier associé en fonction de l'os
     * @param folder le chemin du dossier a ouvrir
     */
    private void openFolder(File folder) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("mac")) {
                new ProcessBuilder("open", folder.getAbsolutePath()).start();
            } else if (os.contains("win")) {
                new ProcessBuilder("explorer", folder.getAbsolutePath()).start();
            } else { // Linux
                new ProcessBuilder("xdg-open", folder.getAbsolutePath()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * methode pour gerer la box repas et sa logique
     */
    private void repasverif() {

        if(repas.isSelected()){
            if(!RepasBox.getChildren().contains(choice)) {
                RepasBox.getChildren().add(choice);
            }
        }
        else
        {
            RepasBox.getChildren().remove(choice);
        }
    }

    /**
     * methode pour creer un dossier pour l'enfant
     */
    private void creerdossier() {

        // Dossier racine de l'application (autorisé en écriture)
        File appDir = new File(System.getProperty("user.home"), "Documents");
        File folder = new File(appDir, "AsmatFp");
        if (!folder.exists()) folder.mkdirs();

        if (enfant == null) {
            System.err.println("Erreur : enfant non défini !");
            return;
        }

        this.file = new File(folder, enfant.getId());
        if (!this.file.exists()) this.file.mkdirs();

        System.out.println("Dossier créé : " + this.file.getAbsolutePath());
    }

    /**
     * method pour afficher un message popup
     * @param Message le message a affiché
     */
    private void affichemessagealert(String Message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(Message);
        alert.showAndWait();
    }

    //Set le label TitleEnfant
    public void setEnfantData(Enfant enfant) {
        this.enfant = enfant;
        titleEnfant.setText(titleEnfant.getText()+enfant.getId());
        Entretienmoins.setText(String.valueOf(enfant.getCoefficientBIndem()));
        Entretienplus.setText(String.valueOf(enfant.getCoefficientHIndem()));
    }


    /**
     * methode pour afficher tous les jours du mois sélectionné //TODO
     * @param year l'année choisi
     * @param moisChoisi le mois a afficher
     * logic : ici la classe FP sert a l'affichage donc notre methods doit prendre toute les semaines du mois meme ceux a cheval faire les semaines grace
     * a presenceSemaine et mettre tous dans presencemois ensuite prendre une list des presences du mois qui seront affiché donc pas ceux a calcule puis réalise les calculs
     * grace a presencemois probablement grace a une autre methodes
     */
    public void AffichageFp(int year, String moisChoisi) {
        if (moisChoisi == null) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);
        moisChoisi = moisChoisi.toLowerCase(Locale.FRENCH);
        Month month = Month.from(formatter.parse(moisChoisi));

        Fp fp = enfant.getOrCreateFp(year, month);

        // Récupérer la FP du mois précédent
        String moisPrecedentStr = moisPrecendent(month.name());
        Month moisPrecedent = Month.valueOf(moisPrecedentStr);
        int yearPrecedent = moisPrecedentStr.equals("DECEMBER") ? year - 1 : year;
        Fp fpPrecedente = enfant.getOrCreateFp(yearPrecedent, moisPrecedent);

        // Recalculer le récap avec la FP précédente
        fp.recalculerRecap(enfant.getConfiguration(), fpPrecedente);

        // Mettre à jour l'affichage du récap
        Nbrdejoursactivites.setText("Nombre de jours d'activités : " + fp.getNombredejoursactivites());
        Heures.setText("Heures: " + fp.getHeures());
        Repas.setText("Repas: " + fp.getRepas());
        IndemniteEntretien.setText("Indemnité: " + fp.getIndmenitesEntretien());
        Ajustement.setText("Ajustement: " + fp.getAjustementT());
        SalaireNet.setText("Salaire net: " + fp.getSalaireNet());

        Daysvbox.getChildren().clear();

        // Afficher tous les jours du MOIS (pas du mois précédent)
        YearMonth yearMonth = YearMonth.of(year, month);
        int totalJours = yearMonth.lengthOfMonth();

        for (int day = 1; day <= totalJours; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            String jourFrancais = date.getDayOfWeek().getDisplayName(
                    java.time.format.TextStyle.FULL, Locale.FRENCH
            );

            Button newButton = new Button(day + " " + jourFrancais);
            newButton.setStyle(
                    "-fx-background-color: " + randomColorCss() + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 15"
            );
            newButton.setMaxWidth(Double.MAX_VALUE);
            newButton.setMaxHeight(Double.MAX_VALUE);

            final int finalDay = day;
            newButton.setOnAction(e -> {
                Ajoutjour.setVisible(true);
                CancelButtonDay.setOnAction(ev -> Ajoutjour.setVisible(false));
                ajoutjour(fp, finalDay, fpPrecedente);
            });

            Daysvbox.getChildren().add(newButton);
        }

        ScrollDay.setContent(Daysvbox);
        ScrollDay.setVvalue(0.0);
    }

    /**
     * method pour ajouter un jour dans une fp donc ici il faut changer la logic pour l'ajouter dans les presences semaines et surttout les calculs par mois
     * donc ici l'affichage doit etre a jour sur les calculs a chaque fois grace peut etre a une methode //TODO
     * @param fp la fp
     * @param day le jour
     */
    public void ajoutjour(Fp fp, int day) {
        // Clear des champs
        Heurearrive.clear();
        heuredepart.clear();
        IndEntretien.clear();
        Commentaire.clear();
        TextAjustement.clear();

        // Récupérer ou créer la présence pour ce jour
        Presence p = fp.getOrCreatePresence(day, enfant.getConfiguration());

        // Remplir les champs avec les valeurs existantes
        if (p.getAjustement() != 0.0) {
            TextAjustement.setText(String.valueOf(p.getAjustement()));
        }
        if (p.getHeureArrive() != 0.0) {
            Heurearrive.setText(String.valueOf(p.getHeureArrive()));
        }
        if (p.getHeureDepart() != 0.0) {
            heuredepart.setText(String.valueOf(p.getHeureDepart()));
        }
        if (enfant.getConfiguration().isRepasFourni()) {
            IndRepas.setText(String.valueOf(p.getIndRepas()));
        }
        if (p.getIndEntretien() != 0.0) {
            IndEntretien.setText(String.valueOf(p.getIndEntretien()));
        }
        Commentaire.setText(p.getCommentaire() != null ? p.getCommentaire() : "");

        // Listener pour calculer l'indemnité d'entretien automatiquement
        Heurearrive.setOnKeyTyped(e -> calculententretien());
        heuredepart.setOnKeyTyped(e -> calculententretien());

        // Action du bouton Valider
        final Presence pFinal = p;
        ValiderButtonDay.setOnAction(e -> {
            // Reset des valeurs
            pFinal.setHeureArrive(0);
            pFinal.setIndEntretien(0);
            pFinal.setIndRepas(0);
            pFinal.setHeureDepart(0);
            pFinal.setAjustement(0);

            // Remplissage avec les nouvelles valeurs
            if (!TextAjustement.getText().trim().isEmpty() &&
                    TextAjustement.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
                pFinal.setAjustement(Float.parseFloat(TextAjustement.getText()));
            }
            if (!Heurearrive.getText().trim().isEmpty() &&
                    Heurearrive.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
                pFinal.setHeureArrive(Float.parseFloat(Heurearrive.getText()));
            }
            if (!IndEntretien.getText().trim().isEmpty() &&
                    IndEntretien.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
                pFinal.setIndEntretien(Float.parseFloat(IndEntretien.getText()));
            }
            if (!IndRepas.getText().trim().isEmpty() &&
                    IndRepas.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
                pFinal.setIndRepas(Float.parseFloat(IndRepas.getText()));
            }
            if (!heuredepart.getText().trim().isEmpty() &&
                    heuredepart.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
                pFinal.setHeureDepart(Float.parseFloat(heuredepart.getText()));
            }

            pFinal.setCommentaire(Commentaire.getText());

            // Fermer le formulaire
            Ajoutjour.setVisible(false);

            // Recalculer le récap
            fp.recalculerRecap(enfant.getConfiguration(), fpPrecedente);

            // Mettre à jour l'affichage
            Nbrdejoursactivites.setText("Nombre de jours d'activités : " + fp.getNombredejoursactivites());
            Heures.setText("Heures: " + fp.getHeures());
            Repas.setText("Repas: " + fp.getRepas());
            IndemniteEntretien.setText("Indemnité: " + fp.getIndmenitesEntretien());
            Ajustement.setText("Ajustement: " + fp.getAjustementT());
            SalaireNet.setText("Salaire net: " + fp.getSalaireNet());

            // Sauvegarder
            EnfantRepository.save(Main.enfants);
        });
    }

    /**
     * methode pour calculer les entretien en direct sur une fp qui sera modifie a l'affichage
     */
    private void calculententretien() {
        if(!heuredepart.getText().trim().equals("") && !Heurearrive.getText().trim().equals("")){
            Float Heuredepart = Float.parseFloat(heuredepart.getText().trim());
            Float heurearrive = Float.parseFloat(Heurearrive.getText().trim());
            float totalheuredepart = Heuredepart - heurearrive;
            if(totalheuredepart <0 )
                IndEntretien.setText(String.valueOf(0));
            else if(totalheuredepart <= 6.23){
                IndEntretien.setText(String.valueOf(enfant.getCoefficientBIndem()));
            }
            if(totalheuredepart >= 6.23){
                IndEntretien.setText(String.valueOf(totalheuredepart*enfant.getCoefficientHIndem()));
            }

        }
    }

    /**
     * algorithme qui retourne une couleur aléatoire
     * @return une couleur aléatoire
     */
    private String randomColorCss () {
            int index = (int) (Math.random() * COULEURS.length);
            return COULEURS[index];
        }
        //ici se trouve les methodes d'implementation mieux faut les éviter elles sont très limitées
    private String moisPrecendent(String mois) {
        switch(mois) {
            case "JANUARY": return "DECEMBER";
            case "FEBRUARY": return "JANUARY";
            case "MARCH": return "FEBRUARY";
            case "APRIL": return "MARCH";
            case "MAY": return "APRIL";
            case "JUNE": return "MAY";
            case "JULY": return "JUNE";
            case "AUGUST": return "JULY";
            case "SEPTEMBER": return "AUGUST";
            case "OCTOBER": return "SEPTEMBER";
            case "NOVEMBER": return "OCTOBER";
            case "DECEMBER": return "NOVEMBER";
            default: throw new IllegalArgumentException("Mois invalide: " + mois);
        }
    }
    private String Moisenanglais(String mois) {
        switch(mois) {
            case "janvier": return "JANUARY";
            case "février": return "FEBRUARY";
            case "mars": return "MARCH";
            case "avril": return "APRIL";
            case "mai": return "MAY";
            case "juin": return "JUNE";
            case "juillet": return "JULY";
            case "août": return "AUGUST";
            case "septembre": return "SEPTEMBER";
            case "octobre": return "OCTOBER";
            case "novembre": return "NOVEMBER";
            case "décembre": return "DECEMBER";
            default: throw new IllegalArgumentException("Mois invalide: " + mois);
        }
    }
    private String moisEnFrancais(String mois) {
        switch (mois) {
            case "JANUARY": return "janvier";
            case "FEBRUARY": return "février";
            case "MARCH": return "mars";
            case "APRIL": return "avril";
            case "MAY": return "mai";
            case "JUNE": return "juin";
            case "JULY": return "juillet";
            case "AUGUST": return "août";
            case "SEPTEMBER": return "septembre";
            case "OCTOBER": return "octobre";
            case "NOVEMBER": return "novembre";
            case "DECEMBER": return "décembre";
            default:
                throw new IllegalArgumentException("Mois invalide: " + mois);
        }
    }


}