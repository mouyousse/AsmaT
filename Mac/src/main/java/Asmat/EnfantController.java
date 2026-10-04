package Asmat;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
import java.util.*;
import java.time.LocalDate;

public class EnfantController {
    //data
    List<Enfant> enfants;
    //Page de base
    // année -> (mois -> fiche)
    TextField hrssupptext;
    private Enfant enfant;
    private ParametresEntretien ParametresEntretien;
    private File file;
    ComboBox<Double> choice;
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
    @FXML
    private Label jourP;
    @FXML
    private Button Automatiquebutton;
    @FXML
    private FpService service = new FpService();
    @FXML
    private CheckBox moisincomplet;
    @FXML
    private TextField Jourssemaine;
    @FXML
    private CheckBox hrssupp;
    @FXML
    private HBox hrssuppbox;
    @FXML

    public void initialize()
    {
        ParametresEntretien=  ParametresEntretienRepository.load();
        choice = new ComboBox<>();
        choice.setValue(0.0);
        choice.getItems().addAll(4.50,5.50,6.50);
        choice.setEditable(true);
        hrssupptext =new TextField();
        hrssupptext.setPromptText("Hrssupp");
        //calculHeurenet
        final boolean[] converted = {false};
        Tauxhoraire.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                converted[0] = false; // nouvelle valeur => prochaine perte de focus recalculera
            }
        });
        moisincomplet.setOnAction(event -> {

            String anneeText = AnneeFiche.getText();
            String moisChoisi = Moisfiche.getSelectionModel().getSelectedItem();


            int year = Integer.parseInt(anneeText);
            Month month = getMonthFromFrench(moisChoisi.toLowerCase(Locale.FRENCH));

            Fp fp = enfant.getOrCreateFp(year, month.getValue());

            fp.setmoiscomplet(!moisincomplet.isSelected());
            AffichageFp(fp.getYear(),moisChoisi);
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
            if(c.getHrssupp()>0){
                hrssupp.setSelected(true);
                hrssuppbox.getChildren().add(hrssupptext);
                hrssupptext.setText(String.valueOf(c.getHrssupp()));
            }
            TypeDuContrat.setValue(c.getTypeContrat());
            DureeContrat.setValue(c.getDureeContrat());
            Jourssemaine.setText(String.valueOf(c.getJourmois()));
            if(!choice.getItems().contains(c.getRepasPrix())) {
                choice.getItems().add(c.getRepasPrix());
            }
            choice.setValue(c.getRepasPrix());
        });
        createenfant1.setOnAction(ev -> {
            ConfigurationEnfant c = enfant.getConfiguration();
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
            if(hrssupp.isSelected() && !hrssupptext.getText().isEmpty()) {
                try {
                    c.setHrssupp(Integer.parseInt(hrssupptext.getText()));
                }
                catch (NumberFormatException e) {
                    affichemessagealert("rentrez un nombre");
                }
            }
            else if(!hrssupp.isSelected()) {
                hrssupp.setSelected(false);
                hrssupptext.setText("");
                c.setHrssupp(0);
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
            c.setJourmois(Integer.parseInt(Jourssemaine.getText().trim()));

            // Fermer la fenêtre config
            config.setVisible(false);

            EnfantRepository.save(this.enfants);
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

            Fp fp = enfant.getFp(year, Moisenint(moisChoisi));
            creerdossier();
            // crée si besoin

            File filePDF = new File(this.file, "Fiche_" + moisChoisi.toUpperCase() + "_" + year + ".pdf");

// ensuite ton code reste identique
            pdf p = new pdf(enfant, fp, filePDF);
            affichemessagealert("le pdf à était crée voir le dossier pour le trouver");
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
    public void setenfants(List<Enfant> enfants){
        this.enfants = enfants;
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
    }


    /**
     * methode pour afficher tous les jours du mois sélectionné
     * @param year l'année choisi
     * @param moisChoisi le mois a afficher
     * a fp puis réalise les calculs
     */
    public void AffichageFp(int year, String moisChoisi) {
        moisincomplet.setDisable(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);
        moisChoisi = moisChoisi.toLowerCase(Locale.FRENCH);
        Month month = getMonthFromFrench(moisChoisi);

        int moisInt = month.getValue();

        Fp fp = enfant.getOrCreateFp(year, moisInt);
        moisincomplet.setSelected(!fp.ismoiscomplet());
        refreshFp(fp);

        Daysvbox.getChildren().clear();

        // Afficher tous les jours du MOIS (pas du mois précédent)
        YearMonth yearMonth = YearMonth.of(year, month);
        int totalJours = yearMonth.lengthOfMonth();
        String colorsave="#7393B3";
        for (int day = 1; day <= totalJours; day++) {

            LocalDate date = LocalDate.of(year, month, day);

            String jourFrancais = date.getDayOfWeek().getDisplayName(
                    java.time.format.TextStyle.FULL,
                    Locale.FRENCH
            );

            Button newButton = new Button(day + " " + jourFrancais);
            String color=colorsave;
            Presence presenceDuJour = fp.getOrCreatePresence(date);
            boolean aDesHeures = presenceDuJour.getHeureArrive() != 0 || presenceDuJour.getHeureDepart() != 0;
            if((jourFrancais.equals("samedi")) || (jourFrancais.equals("dimanche")) ){
                color="#B2BEB5"; //gris
            }
            else if(presenceDuJour.isFinis() && aDesHeures){
                color="green";
            }
            newButton.setCursor(Cursor.HAND);
            newButton.setStyle(
                    "-fx-background-color: " + color + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 15"
            );

            newButton.setMaxWidth(Double.MAX_VALUE);
            newButton.setMaxHeight(Double.MAX_VALUE);

            final int finalDay = day;

            String finalMoisChoisi = moisChoisi;
            newButton.setOnAction(e -> {
                Ajoutjour.setVisible(true);
                CancelButtonDay.setOnAction(ev -> Ajoutjour.setVisible(false));

                LocalDate selectedDate = LocalDate.of(year, month, finalDay);

                ajoutjour(fp, selectedDate,jourFrancais,finalDay, finalMoisChoisi);
                AffichageFp(year, finalMoisChoisi);
            });

            Daysvbox.getChildren().add(newButton);
        }

        ScrollDay.setContent(Daysvbox);
    }

    /**
     * method pour ajouter un jour dans une fp donc ici il faut changer la logic pour l'ajouter dans la fp et surtout les calculs par mois
     * @param fp la fp
     * @param day le jour
     * @param jourfr le jour en fr
     * @param finalDay le jour final
     * @param mois le mois
     */
    public void ajoutjour(Fp fp, LocalDate day,String jourfr,int finalDay,String mois) {
        // Clear des champs
        mois = mois.toLowerCase(Locale.FRENCH);
        Month month = getMonthFromFrench(mois);
        Heurearrive.clear();
        heuredepart.clear();
        IndEntretien.clear();
        Commentaire.clear();
        TextAjustement.clear();
        jourP.setText(jourfr + " " + finalDay + " " + month.getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH));

        // Récupérer ou créer la présence pour ce jour
        Presence p = fp.getOrCreatePresence(day);

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
        Heurearrive.setOnKeyTyped(e -> calculententretien());
        heuredepart.setOnKeyTyped(e -> calculententretien());


        String finalMois = mois;
        ValiderButtonDay.setOnAction(e -> {

            validerjour(p,fp);
            AffichageFp(fp.getYear(), finalMois);
        });
        Automatiquebutton.setOnAction(e -> {
            for (Presence presence : fp.getJours()){
                String jourFrancais = presence.getDateLocal().getDayOfWeek().getDisplayName(
                        java.time.format.TextStyle.FULL,
                        Locale.FRENCH
                );
                if (!jourFrancais.equals("samedi") && !jourFrancais.equals("dimanche")){
                    validerjour(presence,fp);
                }
            }
            AffichageFp(fp.getYear(), finalMois);
        });
    }
    /**
     * methode pour ajouter un jour lorsque qu'il a etait validé
     * @param p la presence
     * @param fp la fiche de presence
     */
    private void validerjour(Presence p,Fp fp) {
        p.setHeureArrive(0);
        p.setHeureDepart(0);
        p.setIndEntretien(0);
        p.setIndRepas(0);
        p.setAjustement(0);

        // remplissage sécurisé
        if (isNumber(TextAjustement.getText())) {
            p.setAjustement(Float.parseFloat(TextAjustement.getText()));
        }
        if (isNumber(Heurearrive.getText())) {
            p.setHeureArrive(Float.parseFloat(Heurearrive.getText()));
        }
        if (isNumber(heuredepart.getText())) {
            p.setHeureDepart(Float.parseFloat(heuredepart.getText()));
        }
        if (isNumber(IndEntretien.getText())) {
            p.setIndEntretien(Float.parseFloat(IndEntretien.getText()));
        }
        if (isNumber(IndRepas.getText())) {
            p.setIndRepas(Float.parseFloat(IndRepas.getText()));
        }

        p.setCommentaire(Commentaire.getText());

        Ajoutjour.setVisible(false);

        refreshFp(fp);
        if(isNumber(Heurearrive.getText()) && isNumber(heuredepart.getText())){
            p.finis();
        }
    }
    /**
     *methode pour mettre a jour l'affichage
     * * @param fp la fp a utilisé pour mettre a jour
     */
    private void refreshFp(Fp fp) {

        fp.setSalaire(service.calculSalaireNet(fp, enfant.getConfiguration()));

        Nbrdejoursactivites.setText("Nombre de jours d'activités : " + fp.getNombreDeJoursActivites());
        Heures.setText("Heures: " + arrondi2(enfant.getConfiguration().getNbHeuresSemaine() * enfant.getConfiguration().getSemaines() / 12.0));
        Repas.setText("Repas: " + arrondi2(fp.getRepas()));
        IndemniteEntretien.setText(String.format(Locale.FRANCE, "Indemnité: %.2f", fp.getIndemnitesEntretien()));
        Ajustement.setText("Ajustement: " + arrondi2(fp.getAjustement()));
        SalaireNet.setText((String.format(Locale.FRANCE, "Salaire Net: %.2f", fp.getSalaire())));
    }
    /**
     * helper pour savoir si le text est un chiffre
     * @param text le text a testé
     * @return vrai si le text est un nombre faux sinon
     */
    private boolean isNumber(String text) {
        return text != null && text.matches("^[0-9]+(\\.[0-9]+)?$");
    }
    /**
     * methode pour calculer les entretien en direct sur une fp qui sera modifie a l'affichage
     */
    private void calculententretien() {
        if(!heuredepart.getText().trim().equals("") && !Heurearrive.getText().trim().equals("")){
            Float Heuredepart = Float.parseFloat(heuredepart.getText().trim());
            Float heurearrive = Float.parseFloat(Heurearrive.getText().trim());

            float totalheuredepart = Heuredepart - heurearrive;
            if(totalheuredepart <=0 )
                IndEntretien.setText(arrondi2(0));
            else if(totalheuredepart <= 6.23){
                IndEntretien.setText(arrondi2(this.ParametresEntretien.getCoefficientBIndem()));
            }
            if(totalheuredepart >= 6.23){
                IndEntretien.setText(arrondi2(totalheuredepart*this.ParametresEntretien.getCoefficientHIndem()));
            }

        }
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
    private int Moisenint(String mois) {
        switch(mois) {

            case "janvier": return 1;
            case "février": return 2;
            case "mars": return 3;
            case "avril": return 4;
            case "mai": return 5;
            case "juin": return 6;
            case "juillet": return 7;
            case "août": return 8;
            case "septembre": return 9;
            case "octobre": return 10;
            case "novembre": return 11;
            case "décembre": return 12;
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
    private Month getMonthFromFrench(String mois) {
        return switch (mois.toLowerCase()) {
            case "janvier" -> Month.JANUARY;
            case "février" -> Month.FEBRUARY;
            case "mars" -> Month.MARCH;
            case "avril" -> Month.APRIL;
            case "mai" -> Month.MAY;
            case "juin" -> Month.JUNE;
            case "juillet" -> Month.JULY;
            case "août" -> Month.AUGUST;
            case "septembre" -> Month.SEPTEMBER;
            case "octobre" -> Month.OCTOBER;
            case "novembre" -> Month.NOVEMBER;
            case "décembre" -> Month.DECEMBER;
            default -> throw new IllegalArgumentException("Mois invalide: " + mois);
        };
    }


}