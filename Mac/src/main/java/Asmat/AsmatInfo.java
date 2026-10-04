package Asmat;

public class AsmatInfo {

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String numeroSalarie;

    private static AsmatInfo instance;

    public AsmatInfo(String nom, String prenom, String adresse,
                     String telephone, String numeroSalarie) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.numeroSalarie = numeroSalarie;
    }
    /**
     * Accès unique en mémoire, chargé depuis le JSON au premier appel.
     */
    public static AsmatInfo getInstance() {
        if (instance == null) {
            instance = AsmatInfoRepo.load();
        }
        return instance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumeroSalarie() {
        return numeroSalarie;
    }

    public void setNumeroSalarie(String numeroSalarie) {
        this.numeroSalarie = numeroSalarie;
    }
}