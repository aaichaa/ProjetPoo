/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jo
 */
public class Direction
{
    public static final Direction DROITE = new Direction("droite");
    public static final Direction GAUCHE = new Direction("gauche");    
    public static final Direction HAUT = new Direction("haut");
    public static final Direction BAS = new Direction("bas");
    public static final Direction HAUT_GAUCHE = new Direction("haut gauche");
    public static final Direction HAUT_DROITE = new Direction("haut droite");
    public static final Direction BAS_GAUCHE = new Direction("bas gauche");
    public static final Direction BAS_DROITE = new Direction("bas droite");
    private static final Direction[] DIRECTIONS = {DROITE,HAUT_DROITE,HAUT,HAUT_GAUCHE,GAUCHE,BAS_GAUCHE,BAS,BAS_DROITE};

    private int dLig;// d sur l'axe vertical entre -1 et 1
    /**
     * restitue le décalage en ligne (vertical) entre -1 (bas) et 1 (haut) 
     * @return un entier entre -1 et 1 — sauf si le décalage est invalide (Dans ce cas, les valeurs peuvent être différentes)
     */
    public int getdLig(){return this.dLig;}
    private void setdLig(int dLig){this.dLig = dLig;}

    private int dCol;// d sur l'axe horizontal entre -1 et 1
    /**
     * restitue le décalage en colonne (horizontal) entre -1 (bas) et 1 (haut)
     * @return
     */
    public int getdCol(){return this.dCol;}
    private void setdCol(int dCol){this.dCol = dCol;}


    /**
     * Restitue le rang du décalage dans le sens trigonométrique (droite -> 0, haut droite -> 1, haut -> 2…)
     * @return un entier entre 0 et 7
     */
    public int getRang()
    {
        if(!isValide())throw new RuntimeException("Direction invalide");
        double dCol = this.dCol,dLig= this.dLig;
        if(Math.abs(dCol*dLig)==1) dCol = dCol*Math.cos(Math.PI/4);
        // dans les angles, on se ramène à un multiple de PI/4
        int rang = (int)Math.round(Math.acos(dCol)/Math.PI*4);
        /*
         * On obtient un nombre entre 0 et 4. Il n'y a pas de distinction entre les directions suivant leur décalage vertical (négatif ou positif)
         */
        if(dLig>0) rang = 7-rang+1; // pour générer les pas correspondant à un décalage vertical négatif
        return rang;
    }

    /**
     * Demande une direction sous forme texte à l'utilisateur, y compris les directions diagonales (haut droite par exemple)
     * @return une nouvelle instance de direction en fonction de la chaîne de caractères entrée par l'utilisateur
     */
    public static Direction getDirectionQuelconque()
    {
        Direction d;
        do
        {
            d = new Direction(Lire.S("Entrez une direction en combinant 'h','b','g','d' ou 'haut','bas','gauche','droite'"));
        }while(!d.isValide());
        return d;
    }

    /**
     * Demande une direction sous forme texte à l'utilisateur. Les directions sont uniquement verticales et horizontales (haut, bas, gauche, droite).
     * @return une nouvelle instance de direction en fonction de la chaîne de caractères entrée par l'utilisateur
     */
    public static Direction getDirectionHorizontaleVerticale()
    {
        Direction d;
        do
        {
            d = new Direction(Lire.S("Entrez une direction 'h','b','g','d' ou 'haut','bas','gauche','droite'"));
        }while(!d.isHorizontaleVerticale());
        return d;
    }

    /**
     * Restitue la direction prédéfinie correspondant au rang spécifié
     * @param rang
     * @return une instance de direction correspondant au rang spécifié : 0 : droite, 1 : haut droite, 2 : haut, 3 : haut gauche…
     */
    public static Direction getDirection(int rang)
    {
        rang = rang % 8;
        return Direction.DIRECTIONS[new Direction(rang).getRang()];
    }

    /**
     *
     * @param dLig la valeur : -1 (haut), 0 : sans changement ou 1 (bas) exprime un décalage de ligne.
     * @param dCol la valeur : -1 (gauche), 0 : sans changement ou 1 (droite) exprime un décalage de colonne.
     * @return une direction qui correspond aux décalages de ligne et de colonne spécifiés. Attention : la direction peut être invalide (0,0) par exemple
     */
    public static Direction getDirection(int dLig,int dCol)
    {   
        Direction nouvelle = new Direction(dLig,dCol);
        if(nouvelle.isValide()) return Direction.DIRECTIONS[nouvelle.getRang()];
        else return nouvelle;
    }

    /**
     * Restitue une direction correspondant à la description textuelle en paramètre
     * @param dirTexte : spécifie une direction (haut gauche, b, bas…)
     * @return une instance de direction qui peut être invalide
     */
    public static Direction getDirection(String dirTexte)
    {
        Direction nouvelle = new Direction(dirTexte);
        return getDirection(nouvelle.getdLig(),nouvelle.getdCol());
    }

    /**
     * Indique si la direction courante est valide ou non
     * @return true/false si la direction est valide/invalide
     */
    public boolean isValide()
    {
        boolean res = !(getdCol()==0 && getdLig()==0);
        if(res)res = getdCol()>=-1 && getdCol()<=1;
        if(res)res = getdLig()>=-1 && getdLig()<=1;
        return res;
    }

    /**
     * Vérifie que la direction courante est limitée à haut, bas, gauche ou droite
     * @return true la direction est horizontale ou verticale
     */
    public boolean isHorizontaleVerticale()
    {
        return this.isValide()&&(this.getRang()%2==0);
    }

    
    /**
     * Restitue la direction suivante dans le sens trigonométrique (0 : droite, 1 : haut droite, 2 : haut,…)
     * @return la direction qui suit la direction courante dans le sens trigonométrique
     */
    public Direction getSucc()
    {
        if(isValide())return Direction.getDirection(this.getRang()+1);
        return null;
    }

    /**
     * Restitue la direction précédente en sens trigonométrique inverse
     * @return la direction qui précède la direction courante dans le sens trigonométrique
     */
    public Direction getPred()
    {
        if(isValide())return Direction.getDirection(this.getRang()-1);
        return null;
    }
    
    private static String getNormalisation(String dirTexte)
    {
        String simplification = dirTexte.toLowerCase();
        simplification = simplification.replace("haut", "h");
        simplification = simplification.replace("bas", "b");
        simplification = simplification.replace("gauche", "g");
        simplification = simplification.replace("droite", "d");
        simplification = simplification.replace("-", "");
        simplification = simplification.replaceAll(" ", "");
        simplification = simplification.replaceAll("_", "");
        return simplification;
    }
    /**
     * Restitue l'adresse d'une nouvelle direction ayant dLig et dCol comme décalages respectifs en ligne et en colonne. Elle peut être invalide si les valeurs entrées son erronées
     * @param dCol entier dans l'intervalle [-1,1] qui représente le décalage en ligne
     * @param dLig entier dans l'intervalle [-1,1] qui représente le décalage en colonne
     */
    public Direction(int dLig, int dCol)
    {
        this.setdLig(dLig);
        this.setdCol(dCol);
    }
   /**
     * Direction par défaut à droite
     */
    public Direction()
    {
        this(DROITE.getdLig(),DROITE.getdCol());
    }

    /**
     * Restitue la direction correspondant à la chaîne en paramètre
     * @param dirTexte chaîne qui peut combiner l'indication d'une direction verticale (haut ou h, bas ou b) et horizontale (droite ou d, gauche ou g). La casse n'importe pas.
     */
    public Direction(String dirTexte)
    {
        dirTexte = getNormalisation(dirTexte);
        if (dirTexte != null)
        {
            int taille = dirTexte.length();
            if (taille > 2){setdCol(0);setdLig(0);}
            else if (taille > 0)
            {
                setDxDy(dirTexte.charAt(0));
                if (taille > 1) setDxDy(dirTexte.charAt(1));
            }
        }
    }

    /**
     * Restitue une direction correspondant à un angle repéré par rang. angle = rangxPI/4 : 0,±8,±16,…->droite, 1,9,17,… -7,-15,…->haut droite…, 2,10,18,… -> haut…
     * @param rang entier positif ou négatif
     */
    public Direction(int rang)
    {
        this(-(int)Math.round(Math.sin(rang*Math.PI/4)),(int)Math.round(Math.cos(rang*Math.PI/4)));
    }

    private void setDxDy(char dir)
    {
        switch (dir)
        {
            case 'h' -> this.dLig -= 1;
            case 'b' -> this.dLig += 1;
            case 'd' -> this.dCol += 1;
            case 'g' -> this.dCol -= 1;
        }
    }

    /**
     * Crée une nouvelle direction à partir de deux direction : haut droite et gauche donne haut
     * @param autre direction
     * @return une nouvelle direction qui combine deux directions
     */
    public Direction getCombinaison(Direction autre)
    {
        if(autre == null || !this.isValide() || !autre.isValide()) return null;
        Direction rep = Direction.getDirection(this.getdLig()+autre.getdLig(),this.getdCol()+autre.getdCol());
        if(rep.isValide())return rep;
        return null;
    }
    
    /**
     * Restitue la direction inverse de la direction courante (haut gauche -> bas droite, droite -> gauche)
     * @return
     */
    public Direction getInverse()
    {
        return Direction.getDirection(this.getRang()+4);
    }

    /**
     * Restitue une désignation textuelle de la direction courante
     * @return une chaîne de caractères
     */
    @Override
    public String toString()
    {
        if (isValide())
        {
            String[] tous = {"droite","haut droite","haut","haut gauche","gauche","bas gauche","bas","bas droite"};
            return tous[getRang()];
        }
        return "invalide";
    }

    /**
     *
     * @param autre
     * @return
     */
    @Override
    public boolean equals(Object autre)
    {
        if (this == autre) return true;
        if (autre == null)return false;
        if (getClass() != autre.getClass()) return false;
        Direction o = (Direction) autre;
        return this.getdLig()==o.getdLig() && this.getdCol()==o.getdCol();
    }
    
}