
package Asmat;
import java.util.Random;
import Asmat.ressources.Exception.FicheExistanteException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class EnfantController {
    //Page de base
    // année -> (mois -> fiche)
    private static final String[] COULEURS = {
            "#6aa84f", // vert
            "#e69138", // orange
            "#6fa8dc"  // bleu
    };
    private Enfant enfant;
    private File file;
    @FXML
    private Label TitleEnfant;
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
    private Text Heures;
    @FXML
    private Text Repas;
    @FXML
    private Text IndemniteEntretien;
    @FXML
    private Text Tarif;
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
    private TextField Commentaire;
    @FXML
    private Button ValiderButtonDay;
    @FXML
    private Button CancelButtonDay;
    //config
    @FXML
    private VBox config;
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
    public void initialize()
    {
        AnneeFiche.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        AjouterButton.setOnAction(e -> {
            AjoutForm.setVisible(true);
        });
            Affichage.setOnAction(ev -> {
                String anneeText = AnneeFiche.getText();
                String moisChoisi = Moisfiche.getSelectionModel().getSelectedItem();

                if (anneeText.isBlank() || moisChoisi == null || moisChoisi.isBlank()) {
                    affichemessagealert("Veuillez saisir l'année et sélectionner un mois.");
                    return;
                }

                int year = Integer.parseInt(anneeText);
                Month month = Month.valueOf(moisChoisi.toUpperCase());
                AffichageFp(year, month);
            });

            ValiderButton.setOnAction(eve -> {
                AjoutForm.setVisible(false);
            });

        DossierButton.setOnAction(e -> {
            try {

                File folder = new File(System.getProperty("user.dir"), "AsmatFp");
                File Enfant=new File(folder, TitleEnfant.getText());
                if (!folder.exists() || !folder.isDirectory()) {
                    boolean created = folder.mkdirs();
                    if (!created) {
                        System.err.println("Impossible de créer le dossier : " + folder.getAbsolutePath());
                        return;
                    }
                }
                if (!Enfant.exists()) {
                    boolean created = Enfant.mkdirs();
                    if (!created) {
                        System.err.println("impossible de creer le dossier : " + Enfant.getAbsolutePath());
                        return;
                    }
                }

                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Desktop API non supportée");
                    return;
                }

                Desktop.getDesktop().open(Enfant);

            } catch (IOException event) {
                event.printStackTrace();
            }
        });
        ModiferConfigButton.setOnAction(ev -> {
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
            TauxhoraireNet.setText(String.valueOf(c.getTauxHoraireNet()));
            MajorationFevrier.setText(String.valueOf(c.getMajorationfevrier()));
            datenaiss.setValue(c.getDateNaissance());
            DateEmbauche.setValue(c.getDateEmbauche());
            rbReferentPere.setSelected(c.isPereReferent());
            Garcontoggle.setSelected(c.isGarcon());
            repas.setSelected(c.isRepasFourni());
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


            // Référent
            c.setPereReferent(rbReferentPere.isSelected());

            // Sexe
            c.setGarcon(Garcontoggle.isSelected());

            // Données contrat
            c.setNemployeur(Nemployeur.getText().trim());

            c.setSemaines(Integer.parseInt(Semaines.getText().trim()));
            c.setNbHeuresSemaine(Float.parseFloat(NbHSemaine.getText().trim()));
            c.setTauxHoraireNet(Float.parseFloat(TauxhoraireNet.getText().trim()));
            c.setMajoration(Float.parseFloat(Majoration.getText().trim()));
            c.setMajorationfevrier(Float.parseFloat(MajorationFevrier.getText().trim()));
            c.setMensualisation(Float.parseFloat(Mensualisation.getText().trim()));

            // Dates
            c.setDateNaissance(datenaiss.getValue());
            c.setDateEmbauche(DateEmbauche.getValue());

            // Options
            c.setRepasFourni(repas.isSelected());

            // Fermer la fenêtre config
            config.setVisible(false);
        });
        annulerbutton.setOnAction(ev -> {config.setVisible(false);});
        ExporterPDF.setOnAction(e -> {
            String anneeText = AnneeFiche.getText();
            String moisChoisi = Moisfiche.getSelectionModel().getSelectedItem();

            if (anneeText.isBlank() || moisChoisi == null || moisChoisi.isBlank()) {
                affichemessagealert("Veuillez saisir l'année et sélectionner un mois.");
                return;
            }

            int year = Integer.parseInt(anneeText);
            Month month = Month.valueOf(moisChoisi.toUpperCase());
            Fp fp = enfant.getOrCreateFp(year, month);

            File filePDF = new File("AsmatFp/" + enfant.getConfiguration().getNom() + "_Fiche_" + month + ".pdf");
            pdf p = new pdf(enfant, fp, filePDF);
        });

    }

    private void affichemessagealert(String Message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(Message);
    }

    //Set le label TitleEnfant
    public void setEnfantData(Enfant enfant) {
        this.enfant = enfant;
    }

    //affiche tous les jours de la Fp
    public void AffichageFp(int year,Month moisChoisi) {
        if (moisChoisi == null) {
            return;
        }
        Fp fp = enfant.getOrCreateFp(year, moisChoisi);

        Daysvbox.getChildren().clear();

        fp.getJours().forEach((jour, data) -> {
            Button newButton = new Button(jour.toString() + " " + fp.getMonth());
            newButton.setStyle(
                    "-fx-background-color: " + randomColorCss() + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 15"
            );
            newButton.setMaxWidth(Double.MAX_VALUE);
            newButton.setMaxHeight(Double.MAX_VALUE);
            newButton.setOnAction(e -> {
                Ajoutjour.setVisible(true);
                CancelButtonDay.setOnAction(ev -> {
                    Ajoutjour.setVisible(false);
                });
                ajoutjour(fp, jour);
            });
            Daysvbox.getChildren().add(newButton);

        });


    }
        //ajoute les donnee d'un jour
        public void ajoutjour (Fp fp,int Day){
            Heurearrive.clear();
            heuredepart.clear();
            IndEntretien.clear();
            IndRepas.clear();
            Commentaire.clear();
            Presence p = fp.getJours().get(Day);
            //get les donne du jour si il yen a
            Heurearrive.setText(String.valueOf(p.getHeureArrive()));
            heuredepart.setText(String.valueOf(p.getHeureDepart()));
            Commentaire.setText(p.getCommentaire());
            IndRepas.setText(String.valueOf(p.getIndRepas()));
            IndEntretien.setText(String.valueOf(p.getIndEntretien()));

            ValiderButtonDay.setOnAction(e -> {

                p.setHeureArrive(Float.parseFloat(Heurearrive.getText()));
                p.setHeureDepart(Float.parseFloat(heuredepart.getText()));
                p.setCommentaire(Commentaire.getText());
                p.setIndRepas(Float.parseFloat(IndRepas.getText()));
                p.setIndEntretien(Float.parseFloat(IndEntretien.getText()));
                p.setTotalHeures();
                Ajoutjour.setVisible(false);
                p.setTotalHeures();
                fp.recalculerRecap(enfant.getConfiguration());
                Ajoutjour.setVisible(false);
                Nbrdejoursactivites.setText(String.valueOf(fp.getNombredejoursactivites()));
                Heures.setText(String.valueOf(fp.getHeures()));
                Repas.setText(String.valueOf(fp.getRepas()));
                IndemniteEntretien.setText(String.valueOf(fp.getIndmenitesEntretien()));
                Tarif.setText(String.valueOf(fp.getTarif()));
                SalaireNet.setText(String.valueOf(fp.getSalaireNet()));
                ScrollDay.setContent(Daysvbox);
            });


        }
        private String randomColorCss () {
            int index = (int) (Math.random() * COULEURS.length);
            return COULEURS[index];
        }
    }