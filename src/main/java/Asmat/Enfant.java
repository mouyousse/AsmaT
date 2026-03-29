package Asmat;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class Enfant {
    private String id;
    private ConfigurationEnfant configuration;
    private double CoefficientHIndem;
    private double CoefficientBIndem;

    // année -> (mois -> fiche)
    private Map<Integer, HashMap<Integer,Fp>> fiches = new HashMap<>();

    public Enfant(ConfigurationEnfant configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }

    public ConfigurationEnfant getConfiguration() {
        return configuration;
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
     * method pour ajouter une fp
     * @param year l'annee pour la fp
     * @param mois le mois de la fp
     */
    public void setFp(int year, int mois) {

        fiches.putIfAbsent(year, new HashMap<>());

        LocalDate debutMois = LocalDate.of(year, mois, 1);
        LocalDate finMois = debutMois.withDayOfMonth(debutMois.lengthOfMonth());

        Fp fp = new Fp(mois,year);
        // on parcourt toutes les semaines possibles de l'année
        for (int week = 1; week <= 53; week++) {

            LocalDate monday;

            try {
                monday = LocalDate.of(year, 1, 4)
                        .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
                        .with(WeekFields.ISO.dayOfWeek(), 1);
            } catch (Exception e) {
                continue; // semaine invalide (année 52/53)
            }

            LocalDate start = monday;
            LocalDate end = monday.plusDays(6);

            // chevauchement avec le mois
            if (!start.isAfter(finMois) && !end.isBefore(debutMois)) {

                PresenceSemaine semaine = new PresenceSemaine(week, year, start.toString());

                fp.SetSemaine(semaine);
            }
        }


        // tu stockes la FP du mois
        fiches.get(year).put(mois, fp);
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

