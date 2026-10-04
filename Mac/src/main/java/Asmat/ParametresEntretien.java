package Asmat;

/**
 * Paramètres globaux liés au calcul de l'indemnité d'entretien.
 * Indépendant de la classe Enfant : c'est Enfant (et le reste du code)
 * qui vient lire cette donnée, jamais l'inverse.
 */
public class ParametresEntretien {

    private static ParametresEntretien instance;

    private double coefficientBIndem = 2.65;
    private double coefficientHIndem = 0.425;

    public ParametresEntretien() {
    }

    public ParametresEntretien(double coefficientBIndem, double coefficientHIndem) {
        this.coefficientBIndem = coefficientBIndem;
        this.coefficientHIndem = coefficientHIndem;
    }

    /**
     * Accès unique en mémoire, chargé depuis le JSON au premier appel.
     */
    public static ParametresEntretien getInstance() {
        if (instance == null) {
            instance = ParametresEntretienRepository.load();
        }
        return instance;
    }

    public double getCoefficientBIndem() {
        return coefficientBIndem;
    }

    public void setCoefficientBIndem(double coefficientBIndem) {
        this.coefficientBIndem = coefficientBIndem;
    }

    public double getCoefficientHIndem() {
        return coefficientHIndem;
    }

    public void setCoefficientHIndem(double coefficientHIndem) {
        this.coefficientHIndem = coefficientHIndem;
    }
}
