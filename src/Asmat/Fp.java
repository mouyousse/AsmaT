package Asmat;

import java.util.HashMap;

public class Fp {

    private int year;
    private Month month;
    private int Nombredejoursactivites;
    private float Heures;
    private float Repas;
    private float IndmenitesEntretien;
    private float Tarif;
    private float SalaireNet;
    private HashMap<Integer,Presence> daysfpData = new HashMap<>();


    public Fp(Month month,int year) {
        this.year = year;
        this.month = month;
        for(int i=1;i<=this.month.getDays();i++) {
            daysfpData.put(i,new Presence(i));
        }
    }
    public Month getMonth() {
        return month;
    }

    public void ajouterJour(int i,Presence data) {
        daysfpData.put(i, data);
    }

    public HashMap<Integer, Presence> getJours() {
        return daysfpData;
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
    public void setTarif(float tarif) {this.Tarif = tarif;}
    public float getTarif() { return Tarif; }
    public void setSalaireNet(float net) {this.SalaireNet = net;}
    public float getSalaireNet() { return SalaireNet; }



        public void recalculerRecap(ConfigurationEnfant config) {
            float totalHeures = 0;
            float totalRepas = 0;
            float totalEntretien = 0;
            float salaire = 0;
            int joursActifs = 0;

            for (Presence p : daysfpData.values()) {
                if (p.getTotalHeures() > 0) {
                    totalHeures += p.getTotalHeures();
                    totalRepas += p.getIndRepas();
                    totalEntretien += p.getIndEntretien();
                    salaire += p.getTotalHeures() * config.getTauxHoraireNet();
                    joursActifs++;
                }
            }

            this.Heures = totalHeures;
            this.Repas = totalRepas;
            this.IndmenitesEntretien = totalEntretien;
            this.SalaireNet = salaire;
            this.Nombredejoursactivites = joursActifs;
        }

}

