
/**
 * Représente les personnages du jeu qui, dans cette version, peuvent être un Joueur ou un Adversaire.
 * Les personnages ont tous une inertie, une réserve d'énergie initialisée à 10 et ont une position qui est une salle interne au plateau (SalleDedans)
 *
 * @author jo
 */
public abstract class Personnage {

    /*
    * Déclaration des attributs communs à tous les personnages et accesseurs.
     */
    private int inertie;
    private int energie;
    private SalleDedans position;

    public SalleDedans getPosition() {
        return position;
    }

    public void setPosition(SalleDedans position) {
        this.position = position;
    }

    public Personnage(int inertie, int energie) {
        this.setInertie(inertie);
        this.setEnergie(energie);
    }

    public int getInertie() {
        return inertie;
    }

    private void setInertie(int inertie) {
        if (inertie < 0 || inertie > 9) {
            System.out.println("L'inertie doit etre toujours entre 0 et 9");
        } else {
            this.inertie = inertie;
        }

    }

    protected void setEnergie(int energie) {
        if (energie < 0) {
            System.out.println("L'énergie doit etre positive");
        } else {
            this.energie = energie;
        }
    }

    /**
     * Restitue la quantité d'énergie restante pour le personnage. C'est la
     * quantité d'énergie de la réserve d'énergie (ReserveLimitee) associée au
     * personnage
     *
     * @return un entier positif ou nul
     */
    public int getEnergie() {
        return this.energie;
    }

    /**
     * Diminue l'énergie du personnage d'une unité (exécuté à chaque changement
     * de salle effectif)
     */
    public void decEnergie() {
        this.energie -= 1;
    }

    /**
     * Restitue la force du personnage (sont inertie fois son énergie).
     *
     * @return un entier positif ou nul
     */
    public int getForce() {
        return inertie * energie;
    }

    /**
     * Restitue true si la réserve d'énergie du personnage est vide
     *
     * @return
     */
    public boolean estNeutralise() {
        return this.getEnergie() == 0;
    }

    /**
     * Migration du personnage de sa salle courante à une salle de destination.
     * Elle est possible si la salle de destination n'est pas occupée. Cela
     * consiste à libérer la salle d'origine (la salle couramment occupée), à
     * affecter la salle de destination au personnage et à faire décroître son
     * énergie
     *
     * @param destination`
     */
    public void migre(SalleDedans destination) {
        if (destination.getPersonnage() == null) {
            // Libère la salle actuelle
            if (this.position != null) {
                this.position.setPersonnage(null);
            }

            // Affecte la nouvelle salle
            destination.setPersonnage(this);
            this.setPosition(destination);

            // Perte d’énergie
            this.decEnergie();
        } else {
            // La salle est occupée : on ne migre pas
            System.out.println("Migration échouée : salle occupée.");
        }
    }

    public void avance() {
    }

    /**
     * Action d'un personnage pour prendre de l'énergie à un autre personnage.
     * Il prend de l'énergie dans la réserve d'énergie de l'autre personnage.
     *
     * @param autre Le personnage à qui l'énergie est prise
     */
    public void prendEnergie(Personnage autre) {
        int aPrendre = Math.min(10 - this.getEnergie(), autre.getEnergie());
        autre.setEnergie(autre.getEnergie() - aPrendre); // on vide l’autre
        this.ajouteEnergie(aPrendre); // on remplit ce qu’on peut
    }


    public void ajouteEnergie(int qte) {
        this.energie = Math.min(this.energie + qte, 10); // ou une constante
    }

    /**
     * Action d'un personnage pour prendre de l'énergie dans une réserve
     * limitée. Le code dépend du type de personnage (Adversaire ou Joueur).
     *
     * @param r La réserve d'énergie limitée (bidon ou réserve d'énergie du
     * personnage)
     */
    public abstract void prendEnergie(ReserveLimitee r);

    /**
     * Action à effectuer après la perte d'un combat. Le code dépend du type de
     * Personnage.
     */
    public abstract void perd();

    /**
     *
     * @param p
     */
    public abstract void rencontre(Personnage p);

    /**
     * Action de combat entre deux personnages. En fait, ce sont un Joueur et un
     * Adversaire, mais il n'est pas besoin de le spécifier. Le gagnant est
     * celui qui a la plus grande force. Il prend de l'énergie au perdant
     * (utilisation de prendEnergie(Personnage autre)) et la méthode perd() est
     * lancée pour le perdant Le code ne dépend pas de qui est le joueur et qui
     * est l'adversaire parce c'est prendEnergie(…) et perd(…) qui seront
     * différent suivant qui l'exécute.
     *
     * @param p1 un Personnage
     * @param p2 un autre Personnage
     */
    public static void combat(Personnage p1, Personnage p2) {
        if (p1.getForce() >= p2.getForce()) {
            System.out.println("Énergie Avant combat — " + p1 + " : " + p1.getEnergie() + " / " + p2 + " : " + p2.getEnergie());
            p1.prendEnergie(p2);

            // 💡 Libère la position de p2 dans TOUS les cas, gagnant ou non
            p2.getPosition().setPersonnage(null);
            p2.perd();

        } else {
            System.out.println("Énergie Avant combat — " + p1 + " : " + p1.getEnergie() + " / " + p2 + " : " + p2.getEnergie());
            p2.prendEnergie(p1);
            p1.getPosition().setPersonnage(null);
            p1.perd();
        }

        System.out.println("Combat entre " + p1 + " (" + p1.getForce() + ") et " + p2 + " (" + p2.getForce() + ")");
        System.out.println("Énergie après combat — " + p1 + " : " + p1.getEnergie() + " / " + p2 + " : " + p2.getEnergie());
    }


}
