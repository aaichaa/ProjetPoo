
/**
 * Représente le plateau du jeu défini par un tableau de salles.
 *
 * @author Aissatou Bobo
 */
public class Plateau {

    /*
    * Déclaration des attributs d'un plateau et accesseurs.
     */
    private int nbLig;
    private int nbCol;
    private Salle[][] grille;
    private Jeu jeu; // pour relier au contexte global du jeu

    /**
     * Restitue le nombre effectif de lignes occupables (sans compter les
     * bords).
     *
     * @return un nombre entier
     */
    public int getNbLig() {
        return nbLig;
    }

    /**
     * Restitue le nombre effectif de colonnes occupables (sans compter les
     * bords).
     *
     * @return un nombre entier
     */
    public int getNbCol() {
        return nbCol;
    }

    /**
     * Restitue la salle d'indices lig et col. Si lig et col ne sont pas valide,
     * la méthode restitue null
     *
     * @param lig numéro de ligne
     * @param col idem lignes
     * @return la salle de numéro de ligne lig et de numéro de colonne col, et
     * null si lig ou col n'est pas valide
     */
    public Salle getSalle(int lig, int col) {
        if (!isValide(lig, col)) {
            return null;
        }
        return grille[lig][col];
    }

    /**
     * Affecte une salle dans le plateau à la position lig-col si cette position
     * est valide
     *
     * @param s La salle à affecter
     * @param lig numéro de ligne valide
     * @param col numéro de colonne valide
     */
    public void setSalle(Salle s, int lig, int col) {
        if (lig >= 0 && lig < grille.length && col >= 0 && col < grille[0].length) {
            grille[lig][col] = s;
        }
    }

    /**
     * Restitue true si et seulement si les numéros de ligne lig et de colonne
     * col sont valides
     *
     * @param lig valide entre 0 et le nombre de lignes occupables +1 (la
     * première (0) et la dernière (nbLig()+1) sont occupées par des salles de
     * type Bordure)
     * @param col idem lig
     * @return un booléen qui vaut true en cas de validité des paramètres
     */
    private boolean isValide(int lig, int col) {
        return lig >= 1 && lig <= nbLig && col >= 1 && col <= nbCol;
    }

    /**
     * Restitue la salle immédiatement voisine d'une salle interne dans la
     * direction donnée. Le numéro de ligne de la nouvelle salle est obtenu en
     * faisant la somme du numéro de ligne lig de la salle en paramètre et du
     * dLig de la direction. Idem pour les colonnes.
     *
     * @param s Une salle interne au plateau (SalleDedans)
     * @param d Une direction quelconque, y compris en diagonales.
     * @return Une salle quelconque, y compris une salle de type Bordure
     */
    public Salle getVoisine(SalleDedans s, Direction d) {
        //Calcule la nouvelle position
        //Ajoute le déplacement demandé (Direction) aux coordonnées actuelles
        int lig = s.getLig() + d.getdLig();
        int col = s.getCol() + d.getdCol();

        //  Empêche de sortir de la zone JOUABLE (évite les bordures)
        if (lig >= 1 && lig <= nbLig && col >= 1 && col <= nbCol) {
            return grille[lig][col];
        }

        return null; // Si c’est une bordure, retourne null
    }

    /**
     * Initialisation du contenu du plateau. Placement de salles de type Bordure
     * tout autour (aux ligne et colonne 0, ainsi qu'à la ligne getNbLig()+1 et
     * à la colonne getNbCol()+1). Placement aléatoire de salles de type
     * SalleBidon (sauf au centre) Placement aléatoire d'adversaires (pas dans
     * les salles SalleBidon ni au centre) Placement du joueur au centre.
     */
    private void initContenu() {
        grille = new Salle[nbLig + 2][nbCol + 2];

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (i == 0 || i == nbLig + 1 || j == 0 || j == nbCol + 1) {
                    grille[i][j] = new Bordure(i, j, this);
                } else {
                    grille[i][j] = new SalleDedans(i, j, this);
                }
            }
        }
    }

    /**
     * Restitue l'état du plateau (C'est cette chaîne qu'il faut afficher à
     * chaque tour). On effectue une boucle sur les lignes et les colonnes du
     * plateau en demandant à concaténant les toString() des différentes salles
     * — plus "\n" à la fin de chaque ligne.
     *
     * @return La chaîne de caractères qui représente le plateau
     */
    /*  @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Salle[] ligne : grille) {
            for (Salle s : ligne) {
                sb.append(s.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); //pour utiliser la meme chaine de caractere pour tout le texte
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                sb.append(grille[i][j].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Création d'une instance de type Plateau. Il faut au moins spécifier le
     * nombre de lignes et de colonnes effectifs ainsi que le jeu auquel
     * appartient le plateau. Il peut y avoir d'autres paramètres (proportion
     * d'adversaires et de bidons dans le plateau)
     *
     * @param nbLig
     * @param nbCol
     * @param jeu
     */
    public Plateau(int nbLig, int nbCol, Jeu jeu) {
        this.nbLig = nbLig;
        this.nbCol = nbCol;
        this.jeu = jeu;
        initContenu(); // création des salles
    }

}
