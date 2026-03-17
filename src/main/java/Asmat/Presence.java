package Asmat;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
//classe représentant les jours des Fp
public class Presence {
    private String Day;
    private float HeureArrive;
    private float HeureDepart;
    private float IndRepas;
    private float IndEntretien;
    private float Ajustement;
    private String Commentaire="";

    public Presence(LocalDate l) {
        this.Day=l.toString();
    }
    public LocalDate getDateLocal() {
        return LocalDate.parse(Day);
    }
    public String getDay() {
        return this.Day;
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
        return getHeureDepart()-getHeureArrive()+this.getAjustement();
    }
    public void setAjustement(float Ajustement) {
        this.Ajustement = Ajustement;
    }
    public float getAjustement() {
        return Ajustement;
    }
    public static String capitalize(String inputString) {

        // get the first character of the inputString
        char firstLetter = inputString.charAt(0);

        // convert it to an UpperCase letter
        char capitalFirstLetter = Character.toUpperCase(firstLetter);

        // return the output string by updating
        //the first char of the input string
        return inputString.replace(inputString.charAt(0), capitalFirstLetter);
    }

}

