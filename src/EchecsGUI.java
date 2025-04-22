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
    private Jeu jeu;
    private Position caseSelectionnee = null;

    public EchecsGUI() {
        frame = new JFrame("Échecs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(8, 8));
        labels = new JLabel[8][8];
        plateau = new Plateau();
        jeu = new Jeu(plateau);
        plateau.addObserver(this);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                labels[i][j] = new JLabel();
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
                frame.add(labels[i][j]);
            }
        }

        initialiserGrille();
        jeu.commencer();
        frame.setVisible(true);
    }

    private void initialiserGrille() {
        // Pièces noires
        plateau.placerPiece(new DeplacementDiagonale(new DeplacementLigne(new PieceNeutre(false, Type.Reine, "img/reine_noir.png"))), 0, 3);
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi, "img/roi_noir.png")), 0, 4);
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(false, Type.Tour, "img/tour_noir.png")), 0, 0);
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(false, Type.Tour, "img/tour_noir.png")), 0, 7);
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier, "img/cavalier_noir.png")), 0, 1);
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier, "img/cavalier_noir.png")), 0, 6);
        plateau.placerPiece(new DeplacementDiagonale(new PieceNeutre(false, Type.Fou, "img/fou_noir.png")), 0, 2);
        plateau.placerPiece(new DeplacementDiagonale(new PieceNeutre(false, Type.Fou, "img/fou_noir.png")), 0, 5);
        for (int i = 0; i < 8; i++) {
            plateau.placerPiece(new DeplacementPion(new PieceNeutre(false, Type.Pion, "img/pion_noir.png")), 1, i);
        }

        // Pièces blanches
        plateau.placerPiece(new DeplacementLigne(new DeplacementDiagonale(new PieceNeutre(true, Type.Reine, "img/reine_blanc.png"))), 7, 3);
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi, "img/roi_blanc.png")), 7, 4);
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(true, Type.Tour, "img/tour_blanc.png")), 7, 0);
        plateau.placerPiece(new DeplacementLigne(new PieceNeutre(true, Type.Tour, "img/tour_blanc.png")), 7, 7);
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier, "img/cavalier_blanc.png")), 7, 1);
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier, "img/cavalier_blanc.png")), 7, 6);
        plateau.placerPiece(new DeplacementDiagonale(new PieceNeutre(true, Type.Fou, "img/fou_blanc.png")), 7, 2);
        plateau.placerPiece(new DeplacementDiagonale(new PieceNeutre(true, Type.Fou, "img/fou_blanc.png")), 7, 5);
        for (int i = 0; i < 8; i++) {
            plateau.placerPiece(new DeplacementPion(new PieceNeutre(true, Type.Pion, "img/pion_blanc.png")), 6, i);
        }

        rafraichirGrille();
    }

    private void gererClic(int x, int y) {
        if (jeu.estTermine()) {
            JOptionPane.showMessageDialog(frame, "La partie est terminée !");
            return;
        }

        Position posCliquee = new Position(x, y);
        Piece pieceCliquee = plateau.getPiece(x, y);

        if (caseSelectionnee == null) {
            if (jeu.estTourDuJoueur(posCliquee)) {
                caseSelectionnee = posCliquee;
                labels[x][y].setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
            } else {
                System.out.println("Ce n'est pas ton tour !");
            }
        } else {
            if (!posCliquee.equals(caseSelectionnee)) {
                boolean deplacementReussi = jeu.tenterDeplacer(caseSelectionnee, posCliquee);
                if (!deplacementReussi) {
                    System.out.println("Déplacement invalide !");
                } else if (jeu.estTermine()) {
                    JOptionPane.showMessageDialog(frame, "Fin de partie. Le joueur " + (jeu.estBlancEnCours() ? "noir" : "blanc") + " a gagné !");
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
                labels[i][j].setIcon(null);
                if (p != null && p.getTypePiece() != null) {
                    String chemin = switch (p.getTypePiece()) {
                        case Roi -> p.estBlanche() ? "img/roi_blanc.png" : "img/roi_noir.png";
                        case Reine -> p.estBlanche() ? "img/reine_blanc.png" : "img/reine_noir.png";
                        case Fou -> p.estBlanche() ? "img/fou_blanc.png" : "img/fou_noir.png";
                        case Cavalier -> p.estBlanche() ? "img/cavalier_blanc.png" : "img/cavalier_noir.png";
                        case Tour -> p.estBlanche() ? "img/tour_blanc.png" : "img/tour_noir.png";
                        case Pion -> p.estBlanche() ? "img/pion_blanc.png" : "img/pion_noir.png";
                        default -> null;
                    };
                    if (chemin != null) {
                        ImageIcon icon = new ImageIcon(chemin);
                        Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        labels[i][j].setIcon(new ImageIcon(img));
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        new EchecsGUI();
    }
}
