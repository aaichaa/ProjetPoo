
/**
 * Salle interne au plateau qui contient un bidon.
 * En plus de ce que font les autres instances de SalleDedans, elles doivent gérer l'interaction entre le personnage entrant et le bidon qu'elles contiennent — pour y prendre de l'énergie
 *
 * @author jo
 */
public class SalleBidon extends SalleDedans {

    /*
    * Déclaration des attributs d'une salle et accesseurs.
     */
    /**
     * Création d'une salle qui contient un bidon. Elle doit s'affecter un
     * nouveau bidon plein.
     *
     * @param lig Numéro de la ligne dans le plateau
     * @param col Numéro de la colonne dans le plateau
     * @param p Le plateau qui contient la salle
     */
    public SalleBidon(int lig, int col, Plateau p) {
        super(lig, col, p);
        this.setBidon(new Bidon());
    }

    /**
     * La chaîne qui représente une telle salle doit restituer le symbole d'un
     * bidon en plus de celui de leur occupant éventuel
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Si le personnage p est entré, il peut prendre de l'énergie dans le bidon
     *
     * @param p
     */
    @Override
    public void entre(Personnage p) {
        if (this.getPersonnage() != null) {
            p.rencontre(this.getPersonnage());
        } else {
            p.migre(this);
            if (this.getBidon() != null) {
                p.prendEnergie(this.getBidon());
            }
        }
    }

}
