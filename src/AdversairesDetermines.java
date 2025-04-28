

/* les adversaires déterminés avancent systématiquement dans la direction du joueur ou dans la
* direction inverse, sauf s’ils sont bloqués par des accumulations d’autres personnages
*
*/



public class AdversairesDetermines extends Adversaire{
    
    
    public AdversairesDetermines(int inertie, int energie, Joueur joueur) {
        super(inertie, energie, joueur);
    }

    @Override
    public void avance() {
        if (this.estNeutralise()) {
            System.out.println("AdversaireDéterminé : neutralisé, il ne bouge pas.");
            return;
        }

        Direction d = getDirectionVersJoueur();

        // Vérifie qu’on n’est pas déjà sur la même case que le joueur
        if (d.getdLig() == 0 && d.getdCol() == 0) {
            System.out.println("AdversaireDéterminé : déjà sur la case du joueur, il ne bouge pas.");
            return;
        }

        // Si plus faible que le joueur, on fuit (inverse de la direction)
        if (this.getForce() < getJoueur().getForce()) {
            d = d.getInverse();
            System.out.println("AdversaireDéterminé : fuit le joueur.");
        } else {
            System.out.println("AdversaireDéterminé : poursuit le joueur.");
        }

        Salle destination = getPosition().getVoisine(d);

        if (destination instanceof SalleDedans sd && sd.getPersonnage() == null) {
            System.out.println("AdversaireDéterminé : se déplace vers (" + d.getdLig() + "," + d.getdCol() + ").");
            destination.entre(this);
        } else {
            System.out.println("AdversaireDéterminé : déplacement impossible (salle occupée ou invalide), il passe son tour.");
        }
    }

}
