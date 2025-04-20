
/**
 * Représente une salle interne au plateau, qui contient potentiellement un personnage
 *
 * @author jo
 */
public class SalleDedans extends Salle {

    /*
    * Déclaration des attributs d'une salle et accesseurs.
     */
    /**
     * Crée une salle interne au plateau initialement sans occupant ni bidon
     *
     * @param lig Numéro de ligne de la salle
     * @param col Numéro de colonne de la salle
     * @param p Plateau auquel appartient la salle
     */
    public SalleDedans(int lig, int col, Plateau p) {
        super(lig, col, p, null, null);
    }

    /**
     * Restitue la salle voisine dans une direction donnée
     *
     * @param d Une direction
     * @return Une nouvelle salle, qui peut être au bord du plateau
     */
    public Salle getVoisine(Direction d) {
        return this.getPlateau().getVoisine(this, d);
    }

    public Salle getVoisine(int i) {
        return getVoisine(new Direction(i));
    }

    public Salle getVoisine(String directionTexte) {
        return getVoisine(new Direction(directionTexte));
    }

    /**
     * Action à effectuer quand un personnage se présente pour entrer dans la
     * salle C'est le personnage entrant qui exécute cette méthode quand il
     * avance vers cette salle Si la salle a un occupant, il faut gérer
     * l'interaction avec lui Puis, si la salle est inocuppée, p peut y entrer
     * effectivement (migre(…)).
     *
     * @param p référence un personnage, joueur ou adversaire
     */
    @Override
    public void entre(Personnage p) {
        Personnage occupant = this.getPersonnage();

        if (occupant != null) {
            p.rencontre(occupant); // déclenche le combat

            // ✅ Si le combat libère la salle, migration du gagnant
            if (this.getPersonnage() == null) {
                p.migre(this);
            }
        } else {
            p.migre(this); // Salle vide
        }
    }


    /**
     * Si on affiche les symboles nous avons un gros décalage c'est lié aux tailles des symboles   
     *  .A -> adversaire, .J -> Joueur, .. -> Case vide, XX -> Bordures, .0 c'est le bidon
     *
     * @return String
     */
    @Override
    public String toString() {
        if (getPersonnage() instanceof Joueur) return "J";
        if (getPersonnage() instanceof Adversaire) return "A";
        if (getBidon() != null) return "0";
        return ".";
    }

}
