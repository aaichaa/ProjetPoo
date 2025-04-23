
/**
 * Représente les adversaires de l'énoncé, qui doivent courser/fuir le joueur suivant leur force.
 * Cette classe peut avoir des classes dérivées correspondant à des types d'adversaires distincts.
 *
 * @author BOBO
 */
public abstract class Adversaire extends Personnage {

    /*
    * Déclaration des attributs spécifiques à Adversaire (Ceux qui ne sont pas déjà déclarés dans Personnage)
     */
    private Joueur joueur;

    public Joueur getJoueur() {
        return joueur;
    }


    /**
     * Restitue la direction que l'on doit prendre pour aller vers le joueur
     *
     * @return new Direction
     */
    public Direction getDirectionVersJoueur() {

        if (joueur == null || joueur.estNeutralise() || joueur.getPosition() == null) {
            return new Direction(0, 0);
        }

        int ligJ = joueur.getPosition().getLig();
        int colJ = joueur.getPosition().getCol();

        int ligThis = this.getPosition().getLig();
        int colThis = this.getPosition().getCol();

        int dLig = Integer.compare(ligJ - ligThis, 0);
        int dCol = Integer.compare(colJ - colThis, 0);

        return new Direction(dLig, dCol);
    }

    /**
     * Avancée de l'adversaire. Il fuit/course le joueur suivant que sa force
     * est plus petite/plus grande que la sienne. Il est possible de faire
     * plusieurs types d'adversaires (sous classes) avec d'autres types de
     * comportements. Avancer consiste à tenter de changer de salle dans une
     * direction donnée, avec la possibilité d'interagir avec le contenu de la
     * salle.
     */
    @Override
    public abstract void avance();


    /**
     * Action spécifique à un adversaire pour prendre de l'énergie dans une
     * réserve limitée. Par principe, un adversaire prend toute l'énergie qu'il
     * peut dans une réserve (bidon ou l'énergie du joueur)
     *
     * @param r La réserve d'énergie limitée (bidon ou réserve d'énergie du
     * joueur)
     */
    @Override
    public void prendEnergie(ReserveLimitee r) {
        int aPrendre = Math.min(r.getContenu(), 10 - this.getEnergie());
        r.modifEnergie(-aPrendre);
        this.ajouteEnergie(aPrendre);
    }

    /**
     * Action à effectuer quand un adversaire rencontre un autre personnage
     * (C'est le cas quand un des protagoniste est dans une salle et l'autre a
     * déclenché la méthode entre de cette salle). Si p est un autre adversaire,
     * il ne se passe rien. Si c'est le joueur, il y a combat.
     *
     * @param p Le joueur ou un autre adversaire.
     */
    @Override
    public void rencontre(Personnage p) {
        if (p instanceof Joueur) {
            Personnage.combat(this, p);
        }
    }

    /**
     * Action à effectuer après la perte d'un combat contre le joueur. Si
     * l'adversaire est neutralisé (Ce n'est pas obligatoire ; le joueur n'a pas
     * forcément pu prendre toute son énergie), il libère la place dans la
     * salle.
     */
    @Override
    public void perd() {
        if (this.estNeutralise()) {
            if (this.getPosition() != null) {
                this.getPosition().setPersonnage(null);
                this.setPosition(null);
            }
        }
    }


    /**
     * Initialise un Adversaire avec une inertie aléatoire entre 2 et 9, le
     * joueur qu'il référencera et la salle initiale dans laquelle il est placé
     * (éventuellement)
     *
     * @param inertie inertie du joueur
     * @param energie energie
     * @param joueur le joueur
     */
    public Adversaire(int inertie, int energie, Joueur joueur) {
        super(inertie, energie);
        this.joueur = joueur;
    }

    /**
     * Restitue le symbole d'un adversaire. Utile pour afficher l'état du
     * plateau.
     *
     * @return une chaîne de caractère qui contient le symbole d'un adversaire
     * (Par exemple Par exemple ♟)
     */
    @Override
    public String toString() {
        return "A";
    }

}
