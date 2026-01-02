package Asmat;

public class Presence {
    private float Day;
    private float HeureArrive;
    private float HeureDepart;
    private float TotalHeures;
    private float IndRepas;
    private float IndEntretien;
;
    private String Commentaire="";

    public Presence(float Day) {
        this.Day=Day;
    }
    public float getDay() {
        return Day;
    }
    public void setHeureArrive(float HeureArrive) {
        this.HeureArrive = HeureArrive;
    }
    public float getHeureArrive() {
        return HeureArrive;
    }
    public void setHeureDepart(float HeureDepart) {
        this.HeureDepart = HeureDepart;
    }
    public float getHeureDepart() {
        return HeureDepart;
    }

    public void setIndRepas(float IndRepas) {
        this.IndRepas = IndRepas;
    }
    public float getIndRepas() {
        return IndRepas;
    }
    public void setIndEntretien(float IndEntretien) {
        this.IndEntretien = IndEntretien;
    }
    public float getIndEntretien() {
        return IndEntretien;
    }
    public void setCommentaire(String Commentaire) {
        this.Commentaire = Commentaire;
    }
    public String getCommentaire() {
        return Commentaire;
    }
    public void setTotalHeures() {
        this.TotalHeures = getHeureDepart() - getHeureArrive();
    }
    public float getTotalHeures() {
        return TotalHeures;
    }
}
