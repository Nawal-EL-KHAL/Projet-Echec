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
