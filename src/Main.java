
/**
 *
 * @author Aissatou Bobo
 */
public class Main {

    public static void main(String[] args) {

        // Création d'un jeu avec une petite taille de plateau
        Jeu jeu = new Jeu(5, 7); // 5 lignes, 7 colonnes (hors bordures)

        // Affichage du plateau à l'initialisation
        System.out.println("Affichage initial du plateau :");
        System.out.println(jeu);

    }
}
