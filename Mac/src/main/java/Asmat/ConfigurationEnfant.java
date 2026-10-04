package Asmat;

import javafx.scene.control.ComboBox;

import java.time.LocalDate;

public class ConfigurationEnfant {

    private String nom;
    private String prenom;
    private boolean isGarcon;

    private String pere;
    private String mere;
    private String adressePere;
    private String adresseMere;
    private String telmere;
    private String telpere;
    private boolean isPereReferent;
    private String lieudevie;
    private String Nemployeur;

    private float tauxHoraireNet;
    private float majoration;
    private float majorationfevrier;
    private float mensualisation;

    private LocalDate dateNaissance;
    private LocalDate dateEmbauche;
    private String TypeDuContrat;
    private String DureeContrat;


    private int semaines;
    private float nbHeuresSemaine;

    private boolean repasFourni;
    private double RepasPrix;
    private int jourmois;
    private int hrssupp;

    // Constructeur vide
    public ConfigurationEnfant() {
    }

    // Constructeur complet
    public ConfigurationEnfant(
            String nom,
            String prenom,
            boolean isGarcon,
            String pere,
            String mere,
            String adressePere,
            String adresseMere,
            String telmere,
            String telpere,
            boolean isPereReferent,
            float tauxHoraireNet,
            float majoration,
            float majorationfevrier,
            float mensualisation,
            LocalDate dateNaissance,
            LocalDate dateEmbauche,
            int semaines,
            float nbHeuresSemaine,
            boolean repasFourni,
            String lieudevie,
            String Nemployeur,
            double RepasPrix,
            String TypeDuContrat,
            String DureeContrat,
            int jourmois,
            int hrssupp
    ) {
        this.nom = nom;
        this.prenom = prenom;
        this.isGarcon = isGarcon;
        this.pere = pere;
        this.mere = mere;
        this.adressePere = adressePere;
        this.adresseMere = adresseMere;
        this.telmere = telmere;
        this.telpere = telpere;
        this.isPereReferent = isPereReferent;
        this.tauxHoraireNet = tauxHoraireNet;
        this.majoration = majoration;
        this.majorationfevrier = majorationfevrier;
        this.mensualisation = mensualisation;
        this.dateNaissance = dateNaissance;
        this.dateEmbauche = dateEmbauche;
        this.semaines = semaines;
        this.nbHeuresSemaine = nbHeuresSemaine;
        this.repasFourni = repasFourni;
        this.lieudevie = lieudevie;
        this.Nemployeur = Nemployeur;
        this.RepasPrix = RepasPrix;
        this.TypeDuContrat = TypeDuContrat;
        this.DureeContrat = DureeContrat;
        this.jourmois = jourmois;
        this.hrssupp = hrssupp;
    }

    // Getters et setters

    public String getNom() {
        return nom;
    }
    public int getHrssupp() {
        return hrssupp;
    }
    public void setHrssupp(int hrssupp) {
        this.hrssupp = hrssupp;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public int  getJourmois() {
        return jourmois;
    }
    public void setJourmois(int jourmois) {
        this.jourmois = jourmois;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isGarcon() {
        return isGarcon;
    }

    public void setGarcon(boolean garcon) {
        isGarcon = garcon;
    }

    public String getPere() {
        return pere;
    }

    public void setPere(String pere) {
        this.pere = pere;
    }

    public String getMere() {
        return mere;
    }

    public void setMere(String mere) {
        this.mere = mere;
    }

    public String getAdressePere() {
        return adressePere;
    }

    public void setAdressePere(String adressePere) {
        this.adressePere = adressePere;
    }

    public String getAdresseMere() {
        return adresseMere;
    }

    public void setAdresseMere(String adresseMere) {
        this.adresseMere = adresseMere;
    }

    public String getTelmere() {
        return telmere;
    }

    public void setTelmere(String telmere) {
        this.telmere = telmere;
    }

    public String getTelpere() {
        return telpere;
    }

    public void setTelpere(String telpere) {
        this.telpere = telpere;
    }

    public boolean isPereReferent() {
        return isPereReferent;
    }

    public void setPereReferent(boolean pereReferent) {
        isPereReferent = pereReferent;
    }

    public float getTauxHoraireNet() {
        return tauxHoraireNet;
    }

    public void setTauxHoraireNet(float tauxHoraireNet) {
        this.tauxHoraireNet = tauxHoraireNet;
    }

    public float getMajoration() {
        return majoration;
    }

    public void setMajoration(float majoration) {
        this.majoration = majoration;
    }

    public float getMajorationfevrier() {
        return majorationfevrier;
    }

    public void setMajorationfevrier(float majorationfevrier) {
        this.majorationfevrier = majorationfevrier;
    }

    public float getMensualisation() {
        return mensualisation;
    }

    public void setMensualisation(float mensualisation) {
        this.mensualisation = mensualisation;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public int getSemaines() {
        return semaines;
    }

    public void setSemaines(int semaines) {
        this.semaines = semaines;
    }

    public float getNbHeuresSemaine() {
        return nbHeuresSemaine;
    }

    public void setNbHeuresSemaine(float nbHeuresSemaine) {
        this.nbHeuresSemaine = nbHeuresSemaine;
    }

    public boolean isRepasFourni() {
        return repasFourni;
    }

    public void setRepasFourni(boolean repasFourni) {
        this.repasFourni = repasFourni;
    }
    public String getLieudeVie() {
        return lieudevie;
    }
    public void setLieudeVie(String lieudevie) {
        this.lieudevie = lieudevie;
    }
    public String getNemployeur() {
        return Nemployeur;
    }
    public  void setNemployeur(String nemployeur) {
        this.Nemployeur = nemployeur;
    }
    public double getRepasPrix() {
        return RepasPrix;
    }
    public void setRepasPrix(double RepasPrix) {
        this.RepasPrix = RepasPrix;
    }
    public String getTypeContrat() {
        return this.TypeDuContrat;
    }
    public  void setTypeContrat(String typeContrat) {
        this.TypeDuContrat = typeContrat;
    }
    public  String getDureeContrat() {
        return this.DureeContrat;
    }
    public  void setDureeContrat(String dureeContrat) {
        this.DureeContrat = dureeContrat;
    }
}
