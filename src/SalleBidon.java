
/**
 * Salle interne au plateau qui contient un bidon.
 * En plus de ce que font les autres instances de SalleDedans, elles doivent gérer l'interaction entre le personnage entrant et le bidon qu'elles contiennent — pour y prendre de l'énergie
 *
 * @author Aissatou Bobo
 */
public class SalleBidon extends SalleDedans {

  
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
        this.setBidon(new Bidon()); //crée un bidon avec 10 unités d'énergie et l'assigne a la salle
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
     * @param p personnage
     */
    @Override
    public void entre(Personnage p) {
        Personnage occupant = this.getPersonnage();

        if (occupant != null) {
            p.rencontre(occupant);

            // Si le combat libère la salle, migration du gagnant
            if (this.getPersonnage() == null) {
                p.migre(this);

                if (this.getBidon() != null) {
                    p.prendEnergie(this.getBidon());
                }
            }
        } else {
            // Salle vide -> migration directe
            p.migre(this);

            if (this.getBidon() != null) {
                p.prendEnergie(this.getBidon());
            }
        }
    }

}
