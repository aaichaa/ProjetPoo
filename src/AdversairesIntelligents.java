import java.util.ArrayList;


/* les adversaires intelligents tentent d’avancer le plus efficacement 
*  possible en direction de (respectivement dans la direction inverse du) joueur,
*  en tenant compte des zones de blocages
*/


    public class AdversairesIntelligents extends Adversaire {
        

        public AdversairesIntelligents(int inertie, int energie, Joueur joueur) {
            super(inertie, energie, joueur);
        }

        @Override
        public void avance() {
            if (this.estNeutralise()) return;

            // On recupere la direction vers le joueur
            Direction versJoueur = this.getDirectionVersJoueur();
            Direction directionCible;

            // On décide : fuir ou poursuivre
            if (this.getForce() >= getJoueur().getForce()) {
                System.out.println("L'adversaire intelligent poursuit le joueur.");
                directionCible = versJoueur;
            } else {
                System.out.println("L'adversaire intelligent fuit le joueur.");
                directionCible = versJoueur.getInverse();
            }

            // On cherche toutes les directions dans l’ordre de proximité
            ArrayList<Direction> directions = getDirectionsParProximite(directionCible);
            for (int i = 0; i < directions.size(); i++) {
            Direction d = directions.get(i);
            Salle destination = this.getPosition().getVoisine(d);
            if (destination instanceof SalleDedans sd && sd.getPersonnage() == null) {
                System.out.println("AI avance vers : " + d);
                destination.entre(this);
                return;
            }
        }

            System.out.println("AI ne peut pas bouger (toutes les directions sont bloquées). Il passe son tour.");
        }

        /**
         * Renvoie les directions triées par proximité à une direction cible.
         * On commence par la direction la plus proche, puis les alternatives autour.
         */
        private ArrayList<Direction> getDirectionsParProximite(Direction cible) {
            ArrayList<Direction> directions = new ArrayList<>();

            // La direction principale
            directions.add(cible);

            // Les deux directions voisines (sens trigonométrique)
            Direction gauche = cible.getPred();
            Direction droite = cible.getSucc();

            if (gauche != null) directions.add(gauche);
            if (droite != null) directions.add(droite);

            // Les autres directions aléatoirement (pour variété) si elles ne sont pas déjà présentes
            Direction[] toutes = {
            Direction.HAUT, Direction.BAS, Direction.GAUCHE,
            Direction.DROITE, Direction.HAUT_DROITE,
            Direction.HAUT_GAUCHE, Direction.BAS_DROITE, Direction.BAS_GAUCHE
        };

        for (int i = 0; i < toutes.length; i++) {
            Direction d = toutes[i];
            if (!directions.contains(d)) {
                directions.add(d);
            }
        }
          
            return directions;
        }
        }