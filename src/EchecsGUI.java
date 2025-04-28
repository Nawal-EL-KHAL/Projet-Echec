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


    public EchecsGUI() {
        frame = new JFrame("Échecs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        plateau = new Plateau();
        frame.setLayout(new GridLayout(plateau.getAxeX(), plateau.getAxeY()));
        labels = new JLabel[plateau.getAxeX()][plateau.getAxeY()];
        jeu = new Jeu(plateau);
        plateau.addObserver(this);

        for (int i = 0; i < plateau.getAxeX(); i++) {
            for (int j = 0; j < plateau.getAxeY(); j++) {
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
                        jeu.gererClic(x, y);
                    }
                });
                frame.add(labels[i][j]);
            }
        }


        frame.setVisible(true);

        plateau.initialiserGrille();
        jeu.commencer();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            switch ((String) arg) {
                case "rafraichissement":
                    rafraichirGrille();
                    break;
                case "couleur":
                    colorierSelection();
                    break;
                case "vider":
                    viderSelection();
                    break;
                case "promotion":
                    if (jeu.estPromotionEnAttente()) {
                        String[] options = {"Reine", "Tour", "Fou", "Cavalier"};
                        int choix = JOptionPane.showOptionDialog(
                                null,
                                "Choisissez la pièce de promotion :",
                                "Promotion",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                        if (choix >= 0) {
                            jeu.traiterPromotion(options[choix]);
                        } else {
                            jeu.traiterPromotion("Reine"); // choix par défaut
                        }
                    }
                default:
                    System.out.println("Type de mise à jour inconnu : " + arg);
            }
        }
    }

    public void colorierSelection() {
        if (jeu.getCaseSelectionnee() != null) {
            Position pos = jeu.getCaseSelectionnee();
            labels[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));  // ou une autre couleur
        }
    }

    public void viderSelection() {
        if (jeu.getCaseSelectionnee() != null) {
            Position pos = jeu.getCaseSelectionnee();
            labels[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }
    }




    private void rafraichirGrille() {
        for (int i = 0; i < plateau.getAxeX(); i++) {
            for (int j = 0; j < plateau.getAxeY(); j++) {
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
