package Asmat;

public class Presence {
    private float Day;
    private float HeureArrive;
    private float HeureDepart;

    private float IndRepas;
    private float IndEntretien;
    private float Ajustement;
    private String Commentaire="";

    public Presence(float Day) {
        this.Day=Day;
    }
    public Presence(){

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
    public float getTotalHeures() {
        return getHeureDepart()-getHeureArrive();
    }
    public void setAjustement(float Ajustement) {
        this.Ajustement = Ajustement;
    }
    public float getAjustement() {
        return Ajustement;
    }
}
