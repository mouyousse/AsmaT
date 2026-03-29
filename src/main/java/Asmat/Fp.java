package Asmat;


import Asmat.PresenceSemaine;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.time.Month;
import java.util.*;

//classe pour stocker et calculer une fp contient toutes les semaines meme a cheval a corriger
public class Fp {
    private int year;
    private String month;
    private int Nombredejoursactivites;
    private float Heures;
    private float Repas;
    private float IndmenitesEntretien;
    private float AjustementT;
    private float SalaireNet;

    private List<PresenceSemaine> Semaines = new HashMap<>();


    public Fp(String month,int year) {
        this.year = year;
        this.month = month;

    }
    public Fp(){

    }
    public String getMonth() {
        return month;
    }

    public int getYear() { return year; }
    public void Nombredejoursactivites(int i) {
        Nombredejoursactivites = i;
    }
    public int getNombredejoursactivites() {
        return Nombredejoursactivites;
    }
    public void setNombredejoursactivites(int Nombredejoursactivites) {
        this.Nombredejoursactivites = Nombredejoursactivites;
    }
    public void setHeures(float i) {this.Heures = i;}
    public float getHeures() { return Heures; }
    public float getRepas() { return Repas; }
    public void setRepas(float repas) {this.Repas = repas;}
    public float getIndmenitesEntretien() {return IndmenitesEntretien;}
    public void setIndmenitesEntretien(float i){this.IndmenitesEntretien=i;}
    public void setAjustementT(float AjustementT) {this.AjustementT = AjustementT;}
    public float getAjustementT() { return AjustementT; }
    public void setSalaireNet(float net) {this.SalaireNet = net;}
    public float getSalaireNet() { return SalaireNet; }


    public void initialiserSemaines(ConfigurationEnfant config) {
        //todo
    }


    public List<Presence> getJoursSemaine(int semaine){
        //todo
    }
    public void recalculerRecap(ConfigurationEnfant config) {
        //todo
    }


    private int moisEnInt(String mois) {
        switch(mois) {
            case "JANUARY": return 1;
            case "FEBRUARY": return 2;
            case "MARCH": return 3;
            case "APRIL": return 4;
            case "MAY": return 5;
            case "JUNE": return 6;
            case "JULY": return 7;
            case "AUGUST": return 8;
            case "SEPTEMBER": return 9;
            case "OCTOBER": return 10;
            case "NOVEMBER": return 11;
            case "DECEMBER": return 12;
            default: throw new IllegalArgumentException("Mois invalide: " + mois);
        }
    }

}