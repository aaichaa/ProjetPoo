
import java.util.Random;

/* les adversaires velléitaires se déplacent avec un tirage aléatoire biaisé : ils ont plus de chance
*  d’aller vers/de fuir le joueur, mais ils peuvent aller aussi ponctuellement dans la direction
*  opposée. Ils peuvent même ne pas bouger ponctuellement. 
*

 */
public class AdversaireVelleitaires extends Adversaire {

    private Random rand = new Random();

    public AdversaireVelleitaires(int inertie, int energie, Joueur joueur) {
        super(inertie, energie, joueur);
    }

    @Override
    public void avance() {
        if (this.estNeutralise()) {
            System.out.println("AdversaireVelleitaire : neutralisé, il ne bouge pas.");
            return;
        }

        Direction versJoueur = getDirectionVersJoueur();
        Direction d; // variable qui contiendra la direction choisie à la fin
        int chance = rand.nextInt(100);

        // 70% de chances d’aller dans le bon sens
        if (chance < 70) {
            //s'il est plus fort que le joueur il va vers lui sinon il le fuit
            //d = (this.getForce() >= getJoueur().getForce()) ? versJoueur : versJoueur.getInverse();
            if (this.getForce() >= getJoueur().getForce()) {
                d = versJoueur;
            } else {
                d = versJoueur.getInverse();
            }
            System.out.println("AdversaireVelleitaire : suit comportement principal (course/fuite).");
        } // 5% de chances de ne pas bouger du tout
        else if (chance >= 95) {
            System.out.println("AdversaireVelleitaire : ne bouge pas (comportement aléatoire).");
            return;
        } // 25% de chance de faire un déplacement complètement aléatoire
        else {
            // Tire aléatoirement une direction parmi les directions valides
            d = Direction.getDirection(new Random().nextInt(8)); // 0 à 7
            System.out.println("AdversaireVelleitaire : se déplace de manière aléatoire.");
        }

        Salle destination = getPosition().getVoisine(d);//cherche la salle voisine dans la direction d
        //on regarde si la destination est une SalleDedans et n'a pas de personnage dedans
        if (destination instanceof SalleDedans sd && sd.getPersonnage() == null) {
            System.out.println("AdversaireVelleitaire : se déplace vers (" + d.getdLig() + "," + d.getdCol() + ").");
            destination.entre(this);
        } else {
            System.out.println("AdversaireVelleitaire : déplacement impossible (salle occupée ou invalide), il passe son tour.");
        }
    }

    @Override
    public String toString() {
        return "V";
    }

}
