package Asmat;

import java.util.HashMap;
import java.util.Map;

public class Enfant {
    private String id;
    private ConfigurationEnfant configuration;
    private double CoefficientHIndem;
    private double CoefficientBIndem;

    // année -> (mois -> fiche)
    private Map<Integer, Map<Integer , PresenceSemaine>> fiches = new HashMap<>();

    public Enfant(ConfigurationEnfant configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }

    public ConfigurationEnfant getConfiguration() {
        return configuration;
    }

    /**
     * method pour avoir une semaine
     * @param year l'annee de la semaine
     * @param Semaine la semaine
     * @return la semaine demande
     * @throws IndexOutOfBoundsException
     */
    public PresenceSemaine getSemaines(int year,int Semaine)throws IndexOutOfBoundsException{
        Map<Integer, PresenceSemaine> semaineMap = fiches.get(year);
        if(!semaineMap.containsKey(Semaine)){
            throw new IndexOutOfBoundsException("Semaine doesn't exist");
        }
        return semaineMap.get(Semaine);
    }

    /**
     * method pour ajouter une semaine
     * @param year l'annee de la semaine
     * @param Semaine la semaine
     */
    public void SetSemaine(int year, int Semaine) {
        fiches.putIfAbsent(year, new HashMap<>());
        Map<Integer, PresenceSemaine> semaineMap = fiches.get(year);
        semaineMap.putIfAbsent(Semaine, new PresenceSemaine(Semaine,year));
    }

    public String getId() {
        return id;
    }
    public double getCoefficientHIndem() {
        return CoefficientHIndem;
    }
    public double getCoefficientBIndem() {
        return CoefficientBIndem;
    }
    public void setCoefficientHIndem(double CoefficientHIndem) {
        this.CoefficientHIndem = CoefficientHIndem;
    }
    public void setCoefficientBIndem(double CoefficientBIndem) {
        this.CoefficientBIndem = CoefficientBIndem;
    }
}

