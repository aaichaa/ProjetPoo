
/**
 * Il n'y a qu'un seul collecteur, qui doit être accessible uniquement du joueur.
 * L'instance de la classe Joueur peut le référencer.
 *
 * @author Aissatou Bobo
 */
public class Collecteur extends Reservoir {

    /**
     * Ajustement du montant positif que l'on veut ajouter à un Collecteur. Il
     * n'y a aucun ajustement à faire car la quantité d'énergie que peut
     * contenir un collecteur n'est pas limitée.
     *
     * @param montant La quantité d'énergie à ajouter au collecteur
     * @return la quantité d'énergie que l'on peut ajouter en fonction du
     * montant spécifié… c'est-à-dire ce montant
     */
    @Override
    public int ajustementAjout(int montant) {
        return Math.max(0, montant); // accepte tout montant positif (0 si négatif)
    }

    /**
     * Ajustement du montant positif que l'on veut retirer au collecteur. Le
     * montant est ajusté à 0 parce qu'on ne peut pas retirer d'énergie à un
     * collecteur. Il est possible d'afficher aussi une erreur si cette méthode
     * est lancée.
     *
     * @param montant La quantité d'énergie à retirer au collecteur (Aucune
     * quantité au dessus de 0 n'est valide)
     * @return La part du montant qu'il est possible de retirer au collecteur
     * (0)
     */
    @Override
    public int ajustementRetrait(int montant) {
        if (montant > 0) {
            System.out.println("Erreur : impossible de retirer de l'énergie au collecteur.");
        }
        return 0;

    }

    public Collecteur() {
        super(0);
    }
}
