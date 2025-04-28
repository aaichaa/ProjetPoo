
/**
 * Représente le joueur de l'énoncé.
 * Dans cette version du jeu, cette classe a une seule instance, dont la position doit être connue de ses adversaires.
 * Il doit pouvoir accéder au collecteur
 *
 * @author Aissatou Bobo
 */
public class Joueur extends Personnage {

    /*
    * Déclaration des attributs spécifiques à Joueur et accesseurs (Ceux qui ne sont pas déjà déclarés dans Personnage)
     */
    private Jeu jeu; //pour contôler la fin du jeu et acceder au collecteur

    /**
     * Action à effectuer quand un joueur (le joueur) rencontre un autre
     * personnage. p peut uniquement être un adversaire dans cette version du
     * jeu, mais la possibilité est ouverte pour une autre version du jeu où un
     * joueur rencontre un autre joueur ou, pourquoi pas, d'autres types de
     * personnages. Si p est un autre adversaire (Ce sera toujours le cas), il y
     * a combat.
     *
     * @param p Un personnage quelconque (En fait, un adversaire).
     */
    @Override
    public void rencontre(Personnage p) {
      Personnage.combat(this, p);

    }

    /**
     * Action à effectuer après la perte d'un combat contre un adversaire. Si le
     * joueur est neutralisé, il a perdu. Le jeu est terminé.
     */
    @Override
    public void perd() {
        if (this.estNeutralise()) {
            System.out.println("Le joueur a été neutralisé. Fin du jeu !");
            if (this.getPosition() != null) {
                this.getPosition().setPersonnage(null); // libère la salle
                this.setPosition(null);                 // sécurité anti-bug
            }
            jeu.setFini(true); //on termine la partie
        }
    }



    /**
     * Avancée du joueur dans une direction demandée à l'utilisateur humain.
     * L'avancée induit l'exécution de la méthode entre(…) dans la salle de
     * destination.
     */
    @Override
    public void avance() {
        Direction d = Direction.getDirectionQuelconque(); // demande à l'utilisateur
        Salle destination = this.getPosition().getVoisine(d); // on suppose que getPosition() est défini dans jeu
        if (destination != null) {
            destination.entre(this);
        } else {
            System.out.println("Déplacement impossible.");
        }
    }

    /**
     * Action spécifique au joueur pour prendre de l'énergie dans une réserve
     * limitée. La quantité à prendre est demandée à l'utilisateur, puis ajustée
     * aux possibilités de la réserve. L'utilsateur choisit ensuite s'il
     * reconstitue sa propre énergie ou s'il ajoute l'énergie prise au
     * collecteur.
     *
     * @param r La réserve d'énergie limitée (bidon ou réserve d'énergie d'un
     * adversaire)
     */
    @Override
    public void prendEnergie(ReserveLimitee r) {
        System.out.println("Énergie disponible : " + r.getContenu());

        int demande = Lire.i("Combien d'énergie voulez-vous prendre ?");
        if (demande < 0) {
            demande = 0;
        }
        if (demande > r.getContenu()) {
            demande = r.getContenu();
        }

        // Retirer l'énergie du bidon ou adversaire
        r.modifEnergie(-demande);

        int choix = Lire.i("1 - Recharger ma propre énergie\n2 - Stocker dans le collecteur\n Veuillez choisir 1 ou 2");
        if (choix == 1) {
            this.ajouteEnergie(demande); // méthode à ajouter dans Personnage
        } else {
            int accepte = jeu.getCollecteur().ajustementAjout(demande);//demande au collecteur si on peut ajouter demande
            jeu.getCollecteur().modifEnergie(accepte);//et on ajoute cette energie (demande) au collecteur
        }
    }
    
    //Constructeur

    public Joueur(Plateau plateau, int energieMax, Jeu jeu) {
        super(5, energieMax);
        this.jeu = jeu;

        // On le placera au centre dans la classe Jeu/Plateau, pas ici
    }

    /**
     * Restitue le symbole du joueur. Utile pour afficher l'état du plateau.
     *
     * @return une chaîne de caractère qui contient le symbole du joueur (Par
     * exemple ♜)
     */
    @Override
    public String toString() {
        return "J"; // 1 seul caractère → sera préfixé dans SalleDedans
    }

}
