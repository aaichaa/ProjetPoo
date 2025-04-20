
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author jo
 */
public class Jeu {

    /*
    * Déclaration des attributs d'un jeu (un joueur, un plateau…) et accesseurs.
     */
    private Plateau plateau;
    private Joueur joueur;
    private int typeAdversaireChoisi;
    private ArrayList<Adversaire> adversaires;
    private Collecteur collecteur;
    private boolean fini = false;

    private static final int ENERGIE_MAX = 10;
    private static final int NB_ADVERSAIRES = 5; // tu peux rendre ça paramétrable
    private static final int NB_BIDONS = 5;

    // Accesseurs utiles
    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Collecteur getCollecteur() {
        return collecteur;
    }

    public void setFini(boolean f) {
        this.fini = f;
    }

    public boolean estFini() {
        return fini;
    }

    /**
     * Initialisation du jeu : - Création du joueur au centre - Placement
     * aléatoire des adversaires (hors centre et bidons) - Placement aléatoire
     * des bidons (hors centre)
     */
    public void initJeu() {
        int ligCentre = plateau.getNbLig() / 2 + 1;
        int colCentre = plateau.getNbCol() / 2 + 1;
        SalleDedans salleCentre = (SalleDedans) plateau.getSalle(ligCentre, colCentre);

        // Création du joueur
        joueur = new Joueur(plateau, ENERGIE_MAX, this);
        joueur.setPosition(salleCentre);
        salleCentre.setPersonnage(joueur);

        // Liste de toutes les salles valides (hors bordure et hors salleCentre)
        ArrayList<SalleDedans> sallesDispo = new ArrayList<>();
        for (int i = 1; i <= plateau.getNbLig(); i++) {
            for (int j = 1; j <= plateau.getNbCol(); j++) {
                Salle s = plateau.getSalle(i, j);
                if (s instanceof SalleDedans sd && s != salleCentre) {
                    sallesDispo.add(sd);
                }
            }
        }

        // Mélange aléatoire de toutes les positions disponibles
        Collections.shuffle(sallesDispo);

        // Placement des bidons
        int bidonsPlaces = 0;
        int index = 0;
        while (bidonsPlaces < NB_BIDONS && index < sallesDispo.size()) {
            SalleDedans sd = sallesDispo.get(index);
            int lig = sd.getLig();
            int col = sd.getCol();
            SalleBidon sb = new SalleBidon(lig, col, plateau);
            plateau.setSalle(sb, lig, col); // remplace la salle
            bidonsPlaces++;
            index++;
        }

        // Placement des adversaires (sur les cases suivantes qui ne sont pas des SalleBidon)
        int advPlaces = 0;
        while (advPlaces < NB_ADVERSAIRES && index < sallesDispo.size()) {
            Salle s = plateau.getSalle(sallesDispo.get(index).getLig(), sallesDispo.get(index).getCol());
            if (s instanceof SalleDedans sd && !(s instanceof SalleBidon)) {

                Adversaire a;
                switch (typeAdversaireChoisi) {
                    case 1 -> a = new AdversairesDetermines(4, 3, joueur);         // Déterminés
                    case 2 -> a = new AdversaireVelleitaires(4, 3, joueur);        // Velléitaires
                    case 3 -> a = new AdversairesIntelligents(4, 3, joueur);       // Intelligents
                    default -> {
                        System.out.println("Type inconnu, on choisit des Déterminés par défaut.");
                        a = new AdversairesDetermines(4, 3, joueur);
                    }
                }

                a.setPosition(sd);
                sd.setPersonnage(a);
                adversaires.add(a);
                advPlaces++;
            }
            index++;
        }
    }

    /**
     * Gère les tours de jeu. Fait jouer le joueur et les adversaires tant que
     * le joueur n'est pas neutralisé et n'a pas choisi d'arrêter le jeu
     *
     * Boucle principale du jeu. Le joueur joue 2 fois, puis chaque adversaire
     * joue 1 fois. À chaque tour, on affiche l’état du plateau.
     */
    public void joue() {
        while (!fini && !joueur.estNeutralise()) {
            System.out.println(plateau);
            System.out.println("Énergie du joueur : " + joueur.getEnergie() );
            System.out.println("Collecteur : " + collecteur.getContenu());
            System.out.println("Adversaires restants : " + adversaires.size());
            System.out.println();

            // Le joueur joue 2 fois
            joueur.avance();
            if (!joueur.estNeutralise()) {
                joueur.avance();
            }

            // Les adversaires jouent
            ArrayList<Adversaire> aSupprimer = new ArrayList<>();
            for (Adversaire a : adversaires) {
                if (!a.estNeutralise()) {
                    a.avance();
                } else {
                    if (a.getPosition() != null) {
                        a.getPosition().setPersonnage(null);
                    }
                    aSupprimer.add(a);
                }
            }
            adversaires.removeAll(aSupprimer);

            // Vérifie victoire
            if (adversaires.isEmpty()) {
                System.out.println("Tous les adversaires sont neutralisés !");
                int choix = Lire.i("Voulez-vous terminer la partie ? (1=oui, 0=non) ");
                if (choix == 1) {
                    fini = true;
                    System.out.println("Score final (énergie dans collecteur) : " + collecteur.getContenu());
                }
            }
        }

        if (joueur.estNeutralise()) {
            System.out.println("Le joueur a été neutralisé. GAME OVER.");
        }
    }

    /**
     * Crée une instance de jeu. Cela crée un joueur, un plateau rempli de
     * bidons et d'adversaires, et lance le jeu (méthode joue())
     *
     * @param nbLig ligne
     * @param nbCol colonnes
     */
    public Jeu(int nbLig, int nbCol) {
        this.plateau = new Plateau(nbLig, nbCol, this);
        this.adversaires = new ArrayList<>();
        this.collecteur = new Collecteur();
        this.fini = false;

        String nomJoueur = Lire.S("Entrez votre nom");

        // Choix du type d'adversaire
        System.out.println("Choisissez le type d’adversaires à affronter");
        System.out.println("1 - Adversaires Déterminés (ils vous poursuivent/fuient sans réfléchir)");
        System.out.println("2 - Adversaires Velléitaires (se déplacent avec un peu de hasard)");
        System.out.println("3 - Adversaires Intelligents (analysent les alentours)");

        int choixType = Lire.i("Votre choix : ");
        while (choixType < 1 || choixType > 3) {
            choixType = Lire.i("Veuillez entrer 1, 2 ou 3");
        }

        this.typeAdversaireChoisi = choixType;

        initJeu();
        joue();
    }
}
