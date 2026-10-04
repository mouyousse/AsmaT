package Asmat;

import java.time.LocalDate;

public class Presence {

    private String day;

    private float heureArrive;
    private float heureDepart;
    private float indRepas;
    private float indEntretien;
    private float ajustement;
    private String commentaire = "";
    private boolean finis=false;


    public Presence() {
    }

    public Presence(LocalDate date) {
        this.day = date.toString();
    }

    // ===== DATE =====
    public LocalDate getDateLocal() {
        return LocalDate.parse(day);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public void finis() {
        this.finis = true;
    }

    /**
     * method pour verifier si la presence est terminee ou non
     * @return true si terminee sinon false
     */
    public boolean isFinis() {
        return finis;
    }
    // ===== HEURES =====
    public float getHeureArrive() {
        return heureArrive;
    }

    public void setHeureArrive(float heureArrive) {
        this.heureArrive = heureArrive;
    }

    public float getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(float heureDepart) {
        this.heureDepart = heureDepart;
    }

    // ===== INDEMNITÉS =====
    public float getIndRepas() {
        return indRepas;
    }

    public void setIndRepas(float indRepas) {
        this.indRepas = indRepas;
    }

    public float getIndEntretien() {
        return indEntretien;
    }

    public void setIndEntretien(float indEntretien) {
        this.indEntretien = indEntretien;
    }

    public float getAjustement() {
        return ajustement;
    }

    public void setAjustement(float ajustement) {
        this.ajustement = ajustement;
    }

    // ===== COMMENTAIRE =====
    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire != null ? commentaire : "";
    }

    // ===== LOGIQUE =====
    public float getTotalHeures() {
        return (heureDepart - heureArrive) + ajustement;
    }
}