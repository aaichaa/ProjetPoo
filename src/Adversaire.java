
/**
 * Représente les adversaires de l'énoncé, qui doivent courser/fuir le joueur suivant leur force.
 * Cette classe peut avoir des classes dérivées correspondant à des types d'adversaires distincts.
 *
 * @author jo
 */
public class Adversaire extends Personnage {

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
            return new Direction(0, 0); // Pas de déplacement
        }

        int ligJ = joueur.getPosition().getLig();
        int colJ = joueur.getPosition().getCol();

        int ligThis = this.getPosition().getLig();
        int colThis = this.getPosition().getCol();

        int dLig = Integer.compare(ligJ - ligThis, 0); // renvoie -1, 0 ou 1
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
    public void avance() {
        // Si l’adversaire est neutralisé, il ne bouge pas
        if (this.estNeutralise()) {
            System.out.println(this + " est neutralisé. Il ne bouge pas.");
            return;
        }

        // Direction vers le joueur
        Direction d = getDirectionVersJoueur();

        //  Déjà sur la même case que le joueur
        if (d.getdLig() == 0 && d.getdCol() == 0) {
            System.out.println(this + " est déjà sur la même case que le joueur.");
            return;
        }

        //  4. Si plus faible, fuit
        if (this.getForce() < joueur.getForce()) {
            System.out.println(this + " est plus faible que le joueur (" + this.getForce() + " < " + joueur.getForce() + "), il fuit.");
            Direction dInverse = d.getInverse();

            if (dInverse.getdLig() == 0 && dInverse.getdCol() == 0) {
                System.out.println("Direction inverse invalide. Il ne bouge pas.");
                return;
            }

            d = dInverse;
        } else {
            System.out.println(this + " est plus fort ou égal au joueur (" + this.getForce() + " >= " + joueur.getForce() + "), il poursuit.");
        }

        // Déplacement dans la direction choisie
        Salle destination = this.getPosition().getVoisine(d);

        if (destination != null) {
            System.out.println(this + " tente de se déplacer vers la direction (" + d.getdLig() + "," + d.getdCol() + ").");
            destination.entre(this);
        } else {
            System.out.println(this + " ne peut pas se déplacer dans la direction (" + d.getdLig() + "," + d.getdCol() + "). Il passe son tour.");
        }
    }


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
                this.setPosition(null); // 🧹 Nettoyage complet
            }
        }
    }


    /**
     * Initialise un Adversaire avec une inertie aléatoire entre 2 et 9, le
     * joueur qu'il référencera et la salle initiale dans laquelle il est placé
     * (éventuellement)
     *
     * @param inertie permet
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
