package Asmat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PAS STOCK
 * Représente une semaine réelle (lundi -> dimanche)
 * Contient des présences et permet les calculs
 */
public class PresenceSemaine {

    private int annee;
    private int numeroSemaine;

    private LocalDate debutSemaine;
    private LocalDate finSemaine;

    private final List<Presence> presences = new ArrayList<>();

    public PresenceSemaine(int annee, int numeroSemaine, LocalDate debutSemaine) {
        this.annee = annee;
        this.numeroSemaine = numeroSemaine;
        this.debutSemaine = debutSemaine;
        this.finSemaine = debutSemaine.plusDays(6);
    }
    public PresenceSemaine() {

    }
    public int getAnnee() {
        return annee;
    }

    public int getNumeroSemaine() {
        return numeroSemaine;
    }

    public LocalDate getDebutSemaine() {
        return debutSemaine;
    }

    public LocalDate getFinSemaine() {
        return finSemaine;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    /**
     * method pour ajouter une presence (jour)
     * @param presence
     */
    public void ajouterPresence(Presence presence) {
        this.presences.add(presence);
    }

    /**
     * method pour avoir le total d'heure de la semaine
     * @return le total d'heure
     */
    public double getTotalHeures() {
        float totalHeures = 0;
        for (Presence presence : presences) {
            totalHeures += presence.getHeureDepart();

        }
        return totalHeures;
    }

    /**
     * method pour obtenir le total d'indemnite
     * @return le total d'indemnite
     */
    public float getTotalIndemnites() {
        float total = 0;

        for (Presence p : presences) {
            total += p.getIndRepas();
            total += p.getIndEntretien();
        }

        return total;
    }

    /**
     * method pour avoir le une présence dans la semaine
     * @param index l'index du jour
     * @return la presence associée
     * @throws IndexOutOfBoundsException throw si l'index n'existe pas
     */
    public Presence getPresence(int index)throws IndexOutOfBoundsException {
        if (index < 0 || index >= presences.size()) {
            throw new IndexOutOfBoundsException("Présence inexistante");
        }
        return presences.get(index);
    }
    /**
     * methode pour récupérer la présence
     * return la liste de présences semaines
     */
    public List<Presence> getSemaine() {
        return this.presences;
    }
}