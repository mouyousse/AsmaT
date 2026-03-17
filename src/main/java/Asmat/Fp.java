package Asmat;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.IsoFields;
import java.time.DayOfWeek;
import java.util.*;

/**
 * classe pour l'affichage des semaines d'un mois pour une FP (fiche de présence)
 */
public class Fp {
    private int year;
    private String month;

    //presences des jours a afficher
    private List<Presence> presences ;


    public Fp(String month, int year,List<Presence> presences) {
        this.month = month;
        this.presences = presences;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * method pour récuperer un jour associé
     * @param jour le jour a recuperer
     * @return la Presence (jour)
     * @throws IndexOutOfBoundsException throw si le jour n'existe pas
     */
    public Presence getPresences(int jour)throws IndexOutOfBoundsException {
        if (presences.size() > jour) {
            throw new IndexOutOfBoundsException();
        }
        return presences.get(jour);
    }

    /**
     * method pour ajouter un jour dans la liste
     * @param presence jour a ajoutes
     */
    public void addPresences( Presence presence) {
        presences.add(presence);
    }


}