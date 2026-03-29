package Asmat;

import java.util.ArrayList;
import java.util.List;

public class PresenceSemaine {
    private int Semaine;
    private List<Presence> presence;
    private int annees;
    private String startOfWeek;
    public PresenceSemaine(int Semaine, int annees,String startOfWeek) {
        this.Semaine = Semaine;
        this.annees = annees;
        this.startOfWeek = startOfWeek;
        this.presence = new ArrayList<>();
    }

    /**
     * method pour récuperer les heures réalise dans la semaine pour le calcul
     * @return toutes les heures de la semaine
     */
    public double getTotalHeures(){
        double totalHeure = 0;
        for(Presence p : presence){
            totalHeure+=p.getTotalHeures();
        }
        return totalHeure;
    }
    /**
     * method pour recuperer toute les Indemnite de la semaine
     * @return le total de la semaine
     */
    public float getInd(){
        float total = 0;
        for(Presence p : presence){
            total+=p.getIndRepas();
            total+=p.getIndEntretien();
        }
        return total;
    }

    /**
     * method pour recuperer la semaine en question
     * @return la semaine
     */
    public int getSemaine() {
        return Semaine;
    }
    /**
     * method pour avoir le premier jour de la semaine
     * @return premier jour ex:"2026-03-02"
     */
    public String getStartOfWeek() {
        return startOfWeek;
    }

    /**
     * method pour set la semaine
     * @param Semaine la semaine a set
     */
    public void setSemaine(int Semaine) {
        this.Semaine = Semaine;
    }

    /**
     * method pour récuperer la liste de presence a utilisé avec précaution
     * @return
     */
    public List<Presence> getPresence() {
        return presence;
    }
    /**
    *method pour return une presence dans la liste de presence de la semaines
    * @IndexOutOfBoundsException throw si le jour n'existe pas dans la liste
     * @param jour le jour de la presence
     **/
    public Presence getPresence(int jour) throws IndexOutOfBoundsException{
        if(presence.size()<jour){
            throw new IndexOutOfBoundsException();
        }
        return presence.get(jour-1);
    }
}
