package Asmat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PAS STOCK
 * classe pour réaliser la logique de semaines a cheval et gérer les calculs sans stocké
 */
public class FpService {
    public FpService() {

    }
    /**
     * method pour construire les semaines pour une fp
     * @param fp la fp a remplir
     * @return la list de semaine qui va servir a remplir la fp
     */
    public List<PresenceSemaine> construireSemaines(Fp fp) {
        List<PresenceSemaine> semaines = new ArrayList<>();

        for (Presence jour : fp.getJours()) {
            LocalDate date = jour.getDateLocal();
            LocalDate debutSemaine = getLundi(date);

            PresenceSemaine semaine = getSemaineParDebut(semaines, debutSemaine);
            semaine.ajouterPresence(jour);
        }

        return semaines;
    }

    /**
     * method pour avoir les heures non supp du mois
     * @param fp la fp
     * @return les heures non supp du mois
     */
    public float calculHeuresNormales(Fp fp) {
        float total = 0;
        for (Presence jour : fp.getJours()) {
            total += jour.getHeureDepart()- jour.getHeureArrive();
        }
        return total;
    }

    /**
     * method pour obtenir les heures supp de la fp bien pris en compte
     * @param fp la Fp
     * @param seuilHebdo le seuil qui vient de config enfant
     * @return les heures supp
     */
    public float calculHeuresSupp(Fp fp, int seuilHebdo) {

        List<PresenceSemaine> semaines = construireSemaines(fp);

        float totalSupp = 0;

        for (PresenceSemaine s : semaines) {

            float totalHeuresSemaine = (float) s.getTotalHeures();

            float totalAjustements = 0;

            for (Presence p : s.getSemaine()) {
                totalAjustements += p.getAjustement();
            }

            if (totalHeuresSemaine > seuilHebdo) {
                totalSupp += totalAjustements;
            }
        }

        return totalSupp;
    }

    /**
     * method calculSalaireNet permet d'obtenir le salaire net des semaines du moi meme a cheval
     * @param fp la fp a calcule
     * @param config la config avec toutes les infos
     * @return le salairenet
     */
    public float calculSalaireNet(Fp fp, ConfigurationEnfant config) {

        float salaire = 0;
        float tauxHoraire = config.getTauxHoraireNet();

        if (fp.ismoiscomplet()) {

            // Mois complet : mensualisation prévue au contrat
            salaire = config.getMensualisation();

            int seuilHebdo = (int) config.getNbHeuresSemaine();

            float heuresSupp = calculHeuresSupp(fp, seuilHebdo);

            salaire += heuresSupp * tauxHoraire;

        } else {

            // Mois incomplet : paiement des heures réellement travaillées
            float heuresTravaillees = calculHeuresNormales(fp);

            salaire = heuresTravaillees * tauxHoraire;
        }

        // Indemnités repas + entretien
        float totalIndemnites = fp.getJours().stream()
                .map(p -> p.getIndEntretien() + p.getIndRepas())
                .reduce(0f, Float::sum);

        salaire += totalIndemnites;

        // Ajustements
        float ajustements = fp.getJours().stream()
                .map(Presence::getAjustement)
                .reduce(0f, Float::sum);

        salaire += ajustements;

        return salaire;
    }


    /**
     * method pour obtenir la date du lundi de la semaine a partir d'une date
     * @param date la date d'une semaine
     * @return la date du lundi
     */
    private LocalDate getLundi(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() - 1);
    }

    /**
     *  Cherche une semaine par son début ou crée une nouvelle semaine
     * @param semaines les semaines d'un mois ou autre
     * @param debut la date du debut de la semaine
     * @return la semaine voulue
     */
    private PresenceSemaine getSemaineParDebut(List<PresenceSemaine> semaines, LocalDate debut) {
        for (PresenceSemaine s : semaines) {
            if (s.getDebutSemaine().equals(debut)) {
                return s;
            }
        }
        // numéro de semaine ISO
        int numeroSemaine = debut.get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear());
        PresenceSemaine nouvelle = new PresenceSemaine(numeroSemaine, debut.getYear(), debut);
        semaines.add(nouvelle);
        return nouvelle;
    }
}