/**
 * Réserve d'énergie bornée positivement (Il y a un maximum à ne pas dépasser)
 *
 * @author Aissatou Bobo
 */
public class ReserveLimitee extends Reservoir {

    /*
    * Déclaration des attributs d'une réserve limitee et accesseurs.
     */
    private int max;

    /**
     * Création d'une réserve limitée avec un contenu initial
     *
     * @param energie initiale
     * @param maxEnergie la capacité maximale de la réserve
     */
    public ReserveLimitee(int energie, int maxEnergie) {
        super(energie);
        this.max = maxEnergie;
    }

    /**
     * Création d'une réserve avec une capacité maximale par défaut (ex: 10)
     *
     * @param energie
     */
    public ReserveLimitee(int energie) {
        this(energie, 10); // par défaut : max = 10
    }

    /**
     * Ajustement du montant positif que l'on veut ajouter à une réserve
     * d'énergie. Il ne faut pas dépasser le maximum d'énergie possible
     *
     * @param montant La quantité d'énergie à ajouter à la réserve
     * @return La part du montant qu'il est possible d'ajouter à la réserve
     */
    @Override
    public int ajustementAjout(int montant) {
        if (montant <= 0) {
            return 0;
        }

        int manque = max - getContenu(); //ce qui manque avant d'atteindre la limite de la reserve
        return Math.min(montant, manque);
    }

    /**
     * Optionnel : obtenir la capacité max
     *
     * @return max
     */
    public int getMax() {
        return max;
    }
}
