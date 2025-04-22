import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class EchecsGUI implements Observer {
    private JFrame frame;
    private JLabel[][] labels;
    private Plateau plateau;
    private Position caseSelectionnee = null;
    private Joueur joueurBlanc = new Joueur("Blanc", true);
    private Joueur joueurNoir = new Joueur("Noir", false);
    private Joueur joueurActuel = joueurBlanc;


    public EchecsGUI() {
        frame = new JFrame("Échecs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(8, 8));
        labels = new JLabel[8][8]; // Création de la grille des labels
        plateau = new Plateau();
        plateau.addObserver(this);

        // Initialisation des labels de la grille
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                labels[i][j] = new JLabel(); // Initialisation des labels à chaque case
                labels[i][j].setOpaque(true);
                labels[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                labels[i][j].setVerticalAlignment(SwingConstants.CENTER);
                int x = i, y = j;
                labels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gererClic(x, y);
                    }
                });
                frame.add(labels[i][j]); // Ajouter chaque label au frame
            }
        }

        // Initialisation des pièces
        initialiserGrille();

        // Affichage de la fenêtre
        frame.setVisible(true);
    }


    private void initialiserGrille() {

        // Initialisation des pièces noires

        // Reine noire
        plateau.placerPiece(
                new DeplacementDiagonale(
                        new DeplacementLigne(
                                new PieceNeutre(false, "Reine", "img/reine_noir.png")
                        )
                ),
                0, 3
        );

        // Roi noir
        plateau.placerPiece(
                new DecorateurRoi(
                        new PieceNeutre(false, "Roi", "img/roi_noir.png")
                ),
                0, 4
        );

        // Tours noires
        plateau.placerPiece(
                new DeplacementLigne(
                        new PieceNeutre(false, "Tour", "img/tour_noir.png")
                ),
                0, 0
        );
        plateau.placerPiece(
                new DeplacementLigne(
                        new PieceNeutre(false, "Tour", "img/tour_noir.png")
                ),
                0, 7
        );

        // Cavaliers noirs
        plateau.placerPiece(
                new DecorateurCavalier(
                        new PieceNeutre(false, "Cavalier", "img/cavalier_noir.png")
                ),
                0, 1
        );
        plateau.placerPiece(
                new DecorateurCavalier(
                        new PieceNeutre(false, "Cavalier", "img/cavalier_noir.png")
                ),
                0, 6
        );

        // Fous noirs
        plateau.placerPiece(
                new DeplacementDiagonale(
                        new PieceNeutre(false, "Fou", "img/fou_noir.png")
                ),
                0, 2
        );
        plateau.placerPiece(
                new DeplacementDiagonale(
                        new PieceNeutre(false, "Fou", "img/fou_noir.png")
                ),
                0, 5
        );

        // Pions noirs
        for (int i = 0; i < 8; i++) {
            plateau.placerPiece(
                    new DeplacementPion(
                            new PieceNeutre(false, "Pion", "img/pion_noir.png")
                    ),
                    1, i
            );
        }


        // Initialisation des pièces blanches

        // Reine blanche
        plateau.placerPiece(
                new DeplacementLigne(
                        new DeplacementDiagonale(
                                new PieceNeutre(true, "Reine", "img/reine_blanc.png")
                        )
                ),
                7, 3
        );

        // Roi blanc
        plateau.placerPiece(
                new DecorateurRoi(
                        new PieceNeutre(true, "Roi", "img/roi_blanc.png")
                ),
                7, 4
        );

        // Tours blanches
        plateau.placerPiece(
                new DeplacementLigne(
                        new PieceNeutre(true, "Tour", "img/tour_blanc.png")
                ),
                7, 0
        );
        plateau.placerPiece(
                new DeplacementLigne(
                        new PieceNeutre(true, "Tour", "img/tour_blanc.png")
                ),
                7, 7
        );

        // Cavaliers blancs
        plateau.placerPiece(
                new DecorateurCavalier(
                        new PieceNeutre(true, "Cavalier", "img/cavalier_blanc.png")
                ),
                7, 1
        );
        plateau.placerPiece(
                new DecorateurCavalier(
                        new PieceNeutre(true, "Cavalier", "img/cavalier_blanc.png")
                ),
                7, 6
        );

        // Fous blancs
        plateau.placerPiece(
                new DeplacementDiagonale(
                        new PieceNeutre(true, "Fou", "img/fou_blanc.png")
                ),
                7, 2
        );
        plateau.placerPiece(
                new DeplacementDiagonale(
                        new PieceNeutre(true, "Fou", "img/fou_blanc.png")
                ),
                7, 5
        );

        // Pions blancs
        for (int i = 0; i < 8; i++) {
            plateau.placerPiece(
                    new DeplacementPion(
                            new PieceNeutre(true, "Pion", "img/pion_blanc.png")
                    ),
                    6, i
            );
        }

        // Mise à jour de l'interface graphique
        rafraichirGrille();
    }

    private void gererClic(int x, int y) {
        Position posCliquee = new Position(x, y);
        Piece pieceCliquee = plateau.getPiece(x, y);

        if (caseSelectionnee == null) {
            if (pieceCliquee != null && pieceCliquee.estBlanche() == joueurActuel.estBlanc()) {
                caseSelectionnee = posCliquee;
                labels[x][y].setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
            } else {
                System.out.println("Ce n'est pas ton tour !");
            }
        } else {
            Piece pieceADeplacer = plateau.getPiece(caseSelectionnee.x, caseSelectionnee.y);

            if (pieceADeplacer != null && pieceADeplacer.estBlanche() == joueurActuel.estBlanc()) {
                List<Position> deplacements = pieceADeplacer.getDeplacementsPossibles(plateau, caseSelectionnee);

                if (deplacements.contains(posCliquee)) {
                    plateau.placerPiece(pieceADeplacer, x, y);
                    plateau.placerPiece(null, caseSelectionnee.x, caseSelectionnee.y);
                    joueurActuel = (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc;
                    
                } else {
                    System.out.println("Déplacement invalide !");
                }
            }

            labels[caseSelectionnee.x][caseSelectionnee.y].setBorder(null);
            caseSelectionnee = null;
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        rafraichirGrille();
    }

    private void rafraichirGrille() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = plateau.getPiece(i, j);
                labels[i][j].setIcon(p != null ? p.getIcon() : null);
            }
        }
    }

    public static void main(String[] args) {
        new EchecsGUI();
    }
}
