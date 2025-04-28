
/**
 * Représentent les bidons de l'énoncé.
 * Ce sont des réserves limitées d'énergie (avec un maximum d'énergie) auxquelles on ne peut pas ajouter d'énergie ; peut seulement en prendre.
 * Une instance de bidon est destinée à être référencée par une « SalleBidon ».
 *
 * @author Aissatou Bobo
 */
public class Bidon extends ReserveLimitee {

    /**
     * Création d'un bidon plein à sa capacité maximale (par défaut 10).
     */
    public Bidon() {
        super(10, 10); // bidon plein, max par défaut à 10
    }

    /**
     * Création d'un bidon avec énergie et capacité personnalisées
     *
     * @param energie
     * @param max
     */
    public Bidon(int energie, int max) {
        super(energie, max);
    }

    /**
     * Ajustement du montant positif que l'on veut ajouter à un bidon. Le
     * montant est ajusté à 0 parce qu'on ne peut pas ajouter d'énergie à un
     * bidon. Il est possible d'afficher aussi une erreur si cette méthode est
     * lancée.
     *
     * @param montant La quantité d'énergie à ajouter au bidon (Aucune quantité
     * au dessus de 0 n'est valable)
     * @return La part du montant qu'il est possible d'ajouter au bidon (0)
     */
    @Override
    public int ajustementAjout(int montant) {
        if (montant > 0) {
            System.out.println("Impossible d'ajouter de l'énergie dans un bidon !");
        }
        return 0;
    }

    /**
     * Restitue le symbole d'un bidon. Utile pour afficher l'état du plateau.
     *
     * @return une chaîne de caractère qui contient le symbole d'un bidon
     * d'énergie (Par exemple ☼)
     */
    @Override
    public String toString() {
        return "0";
    }

}
