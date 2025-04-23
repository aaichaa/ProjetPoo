
/**
 * Représente une salle du plateau.
 * Toutes les salles ont une position lig-col et appartiennent à un plateau donné.
 * Elles ont toutes une méthode entre(Personnage p) qui est exécutée quand un personnage se présente à l'entrée de la salle (il avance vers la salle)
 *
 * @author Aissatou Bobo
 */
public abstract class Salle {

    /*
    * Déclaration des attributs d'une salle et accesseurs.
     */
    private int lig;
    private int col;
    private Plateau plateau;
    private Bidon bidon;
    private Personnage personnage;

    public Salle(int lig, int col) {
        this.setLig(lig);
        this.setCol(col);
    }

    public Salle(int lig, int col, Plateau plateau, Bidon bidon, Personnage personnage) {
        this.setLig(lig);
        this.setCol(col);
        this.setPersonnage(personnage);
        this.setBidon(bidon);
        this.setPlateau(plateau);

    }

    public Bidon getBidon() {
        return bidon;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    protected void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public int getLig() {
        return lig;
    }

    private void setLig(int lig) {
        this.lig = lig;
    }

    public int getCol() {
        return col;
    }

    private void setCol(int col) {
        this.col = col;
    }

    protected Plateau getPlateau() {
        return plateau;
    }

    private void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    protected void setBidon(Bidon bidon) {
        if (bidon == null) {
            this.bidon = null;
        } else {
            this.bidon = bidon;
        }
    }

    public boolean hasBidon() {
        return this.bidon != null;
    }

    public boolean hasPersonnage() {
        return this.personnage != null;
    }

    /**
     * Méthode lancée par un personnage qui tente d'accéder à une salle au cours
     * de son déplacement (méthode avance du personnage). Elle gère
     * l'interaction entre le personnage et son contenu, et entérine
     * éventuellement l'entrée dans la salle. Elle n'a pas de corps ici car
     * l'intéraction dépend du type de salle (sous-classes)
     *
     * @param p
     */
    public abstract void entre(Personnage p);

    @Override
    public String toString() {
        return "S"; // comme une salle vide
    }

}
