import java.util.Random;

public class AdversaireVelleitaires extends Adversaire{

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
        Direction d;
        int chance = rand.nextInt(100);

        // 70% de chances d’aller dans le bon sens
        if (chance < 70) {
            d = (this.getForce() >= getJoueur().getForce()) ? versJoueur : versJoueur.getInverse();
            System.out.println("AdversaireVelleitaire : suit comportement principal (course/fuite).");
        }
        // 5% de chances de ne pas bouger du tout
        else if (chance >= 95) {
            System.out.println("AdversaireVelleitaire : ne bouge pas (comportement aléatoire).");
            return;
        }
        // 25% de chance de faire un déplacement complètement aléatoire
        else {
            d = Direction.getDirectionQuelconque();
            System.out.println("AdversaireVelleitaire : se déplace de manière aléatoire.");
        }

        Salle destination = getPosition().getVoisine(d);
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
