
/**
 * Salles qui matérialisent le bord du plateau, auxquelles les personnages ne peuvent pas accéder.
 * Elle peuvent servir à gérer simplement les sorties de plateau — plutôt que de faire des tests systématiques.
 * Une tentative d'accès à ces salles est ignorée et peut éventuellement induire l'affichage d'un message d'erreur.
 * Si le plateau a nbCol colonnes et nbLig lignes effectives, numérotées de 1 à nbCol/nbLig, ces salles sont situées 
 * aux lignes/colonnes d'indice 0 et nbLig+1/nbCol+1
 *
 * @author Aissatou Bobo
 */
public class Bordure extends Salle {

    public Bordure(int lig, int col, Plateau p) {
        super(lig, col, p, null, null);

    }

    /**
     * Restitue l'aspect du bord du plateau. Par exemple, ██
     *
     * @return X
     */
    @Override
    public String toString() {
        return "X";
    }

    @Override
    public void entre(Personnage p) {
        System.out.println("Le personnage ne peut pas entrer dans une salle de bordure.");
    }
}
