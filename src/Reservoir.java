/**
 * Réserve d'énergie qui peut être vide.
 * Elle est représentée par une quantité d'énergie entière positive ou nulle.
 *
 * @author jo
 */
public abstract class Reservoir {

    /*
    * Déclaration des attributs d'un réservoir et accesseurs.
     */
    // Attribut contenant la quantité d’énergie actuelle
    private int energie;

    // Valeur maximale par défaut, peut être utilisée par les classes filles
    protected static final int ENERGIE_MAX = 10;

    /**
     * Restitue la quantité d'énergie disponible
     *
     * @return un nombre entier positif ou nul
     */
    public int getContenu() {
        return energie;
    }

    /**
     * Affecte une quantité d'énergie initiale
     *
     * @param energie une valeur positive
     */
    private void setEnergie(int energie) {
        if (energie < 0) {
            this.energie = 0;
        } else {
            this.energie = energie;
        }
    }

    /**
     * Restitue true si la réserve est vide
     *
     * @return un booléen
     */
    public boolean estVide() {
        return this.energie == 0;
    }

    /**
     * Ajustement du montant positif que l'on veut ajouter à un réservoir
     * d'énergie. Le code dépend du type de réservoir
     *
     * @param montant La quantité d'énergie à ajouter au réservoir
     * @return La part du montant qu'il est possible d'ajouter au réservoir
     */
    public abstract int ajustementAjout(int montant);

    /**
     * Ajustement du montant positif que l'on veut retirer au réservoir. Le
     * montant que l'on peut retirer ne peut pas dépasser la quantité
     * disponible.
     *
     * @param montant La quantité d'énergie à retirer au réservoir
     * @return La part du montant qu'il est possible de retirer au réservoir
     */
    public int ajustementRetrait(int montant) {
        if (montant < 0) {
            return 0;
        }
        return Math.min(montant, this.energie);
    }

    /**
     * Modifie la quantité d'énergie disponible d'un montant spécifié positif ou
     * négatif
     *
     * @param montant la variation (peut être négative)
     */
    public void modifEnergie(int montant) {
        this.energie += montant;
        if (this.energie < 0) {
            this.energie = 0;
        }
    }

    /**
     * Création d'un nouveau réservoir avec une énergie initiale
     *
     * @param energie
     */
    public Reservoir(int energie) {
        setEnergie(energie);
    }

    /**
     * Transfert d'un montant positig d'énergie d'un réservoir externe au
     * réservoir courant
     *
     * @param montant souhaité
     * @param autre autre le réservoir source
     */
    public void transfereDe(int montant, Reservoir autre) {
        if (montant <= 0 || autre == null) {
            return;
        }

        int possibleADonner = autre.ajustementRetrait(montant);
        int possibleARecevoir = this.ajustementAjout(possibleADonner);
        int montantEffectif = Math.min(possibleADonner, possibleARecevoir);

        autre.modifEnergie(-montantEffectif); // retire de la source
        this.modifEnergie(montantEffectif);   // ajoute à la destination
    }
}
