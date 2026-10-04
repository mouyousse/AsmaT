package Asmat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FP = stockage des données du mois
 * PAS de logique métier ici
 */
public class Fp {

    private int year;
    private int month;

    // données saisies
    private float salaire;
    private int nombreDeJoursActivites;
    private float Heures;
    private float repas;
    private float indemnitesEntretien;
    private float ajustement;
    private boolean moiscomplet=true;

    // données saisies utilisateur (jours)
    private List<Presence> jours = new ArrayList<>();

    // état validation
    private boolean valide = false;

    public Fp(int month, int year) {
        this.month = month;
        this.year = year;
    }
    public Fp(){

    }
    public List<Presence> getJours() {
        return jours;
    }
    public boolean ismoiscomplet(){
        return moiscomplet;
    }
    public void setmoiscomplet(boolean moiscomplet){
    this.moiscomplet=moiscomplet;}

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * method pour avoir le nombre de jours d'activite
     * @return le nombres de jours d'activites
     */
    public int getNombreDeJoursActivites() {
        nombreDeJoursActivites = 0;
        for (Presence presence : jours) {
            if(presence.getTotalHeures() > 0) {
                System.out.println("presence"+nombreDeJoursActivites+": "+ presence.getDateLocal());
                nombreDeJoursActivites++;
            }
        }
        return nombreDeJoursActivites;
    }

    /**
     * method pour avoir le total du prix du repas
     * @return le prix de tous les repas sur le mois
     */
    public float getRepas() {
        repas = 0;
        for(Presence presence : jours) {
            if(presence.getTotalHeures() > 0) {
                repas += presence.getIndRepas();
            }
        }
        return repas;
    }

    /**
     * method qui retourne le salairenet sur la fp
     * @return le salaire net fp
     */
    public float getSalaire() {
        return salaire;
    }
    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }
    /**
     * method pour avoir le nombre de jours d'activite
     * @return le nombres de jours d'activites
     */
    public float getIndemnitesEntretien() {
        indemnitesEntretien = 0;
        for (Presence presence : jours) {
            if(presence.getTotalHeures() > 0) {
                indemnitesEntretien+= presence.getIndEntretien();
            }
        }
        return indemnitesEntretien;
    }
    /**
     * methode pour avoir Ajustement
     * @return l'ajustement sur la semaine
     */
    public float getAjustement() {
        ajustement = 0;
        for (Presence presence : jours) {
            if(presence.getTotalHeures() > 0) {
                ajustement+= presence.getAjustement();
            }
        }
        return ajustement;
    }
    /**
     * method pour avoir le nombres d'heures
     * @return les heures effecutées dans le mois
     */
    public float getHeures() {
        Heures = 0;
        for (Presence presence : jours) {
            if(presence.getTotalHeures() > 0) {
                Heures+= presence.getTotalHeures();
            }
        }
        return Heures;
    }

    /**
     * method pour récuperer un jour dans la liste ou l'ajouter
     * @param date le jour a recup a creer
     * @return le jour
     * @throws IndexOutOfBoundsException est throw si le jour n'existe pas
     */
    public Presence getOrCreatePresence(LocalDate date) {

        // 1. chercher si la présence existe déjà
        for (Presence p : jours) {
            if (p.getDateLocal().equals(date)) {
                return p;
            }
        }

        // 2. sinon créer
        Presence newPresence = new Presence(date);

        jours.add(newPresence);

        return newPresence;
    }

    public boolean isValide() {
        return valide;
    }
    public void setValide(boolean valide) {
        this.valide = valide;
    }


    /**
     * method pour reset la liste
     */
    public void clearJours() {
        this.jours.clear();
    }

}