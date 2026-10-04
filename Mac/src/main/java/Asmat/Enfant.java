package Asmat;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class Enfant {
    private String id;
    private ConfigurationEnfant configuration;


    // année -> (mois -> fiche)
    private Map<Integer, HashMap<Integer,Fp>> fiches = new HashMap<>();

    public Enfant(ConfigurationEnfant configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }
    public Enfant(){

    }
    public ConfigurationEnfant getConfiguration() {
        return configuration;
    }
    public void setConfiguration(ConfigurationEnfant configuration) {
        this.configuration = configuration;
    }

    /**
     * method pour avoir une Fp
     * @param year l'annee de la fp
     * @param mois pour la fp
     * @return la fp a afficher
     * @throws IndexOutOfBoundsException
     */
    public Fp getFp(int year,Integer mois)throws IndexOutOfBoundsException{
        if(!fiches.containsKey(year)){
            throw new IndexOutOfBoundsException("the year doesn't exist");
        }
        if(!fiches.get(year).containsKey(mois)){
            throw new IndexOutOfBoundsException("the month doesn't exist");
        }
        return fiches.get(year).get(mois);

    }
    /**
     * method pour ajouter une fp ou recuperer a partir de l'année et du mois
     * @param year l'annee pour la fp
     * @param month le mois de la fp
     */
    public Fp getOrCreateFp(int year, int month) {

        fiches.putIfAbsent(year, new HashMap<>());

        Map<Integer, Fp> fichesAnnee = fiches.get(year);

        return fichesAnnee.computeIfAbsent(month, m -> new Fp(m, year));
    }

    /**
     * method pour retourner la hashmap de fiches
     * @return fiches
     */
    public Map<Integer, HashMap<Integer,Fp>> getFiches() {
        return fiches;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}

