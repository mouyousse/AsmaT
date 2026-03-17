package Asmat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * classe pour le calcul elle ne stocke pas les donnees
 */
public class PresenceMois {
    private YearMonth mois;
    private List<PresenceSemaine> semaines;
    private List<Presence> jours;

    public PresenceMois(YearMonth mois) {
        this.mois = mois;
        this.semaines = new ArrayList<>();
        this.jours = new ArrayList<>();
    }

    /**
     * method pour récuperer la semaine associée
     * @param Semaine la Semaine a récuperer
     * @return la semaine de la liste
     * @throws IndexOutOfBoundsException throw si la semaine n'est pas dans la liste
     */
    public PresenceSemaine getSemaine(int Semaine) throws IndexOutOfBoundsException {
        if (semaines.size() >Semaine  ) {
            throw new IndexOutOfBoundsException();
        }
        return semaines.get(Semaine);
    }
    /**
     * method pour ajouter une semaine
     * @param semaine a ajouter
     */
    public void addSemaine(PresenceSemaine semaine) {
        this.semaines.add(semaine);
    }
    /**
     * method pour récuperer le jour associée
     * @param Day le jour a récuperer
     * @return le jour de la liste
     * @throws IndexOutOfBoundsException throw si le jour n'est pas dans la liste
     */
    public Presence getJour(int Day) throws IndexOutOfBoundsException {
        if (jours.size() >Day) {
            throw new IndexOutOfBoundsException();
        }
        return jours.get(Day);
    }

    /**
     * method pour ajouter un jour
     * @param jour a ajouter
     */
    public void addJour(Presence jour) {
        this.jours.add(jour);
    }
}
