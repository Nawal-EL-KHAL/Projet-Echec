import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;

public class VueDames extends Vue {

    private static PlateauDames plateau = new PlateauDames();
    private JeuDames jeuDames;

    public VueDames() {
        super(plateau, new JeuDames((PlateauDames) plateau));  // Appel du constructeur parent d'abord

        this.jeuDames = (JeuDames) super.getJeu();  // récupère correctement l'objet JeuEchecs

        getFrame().setTitle("Dames");
        plateau.addObserver(this);

        this.plateau.initialiserGrille();


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


                case "accessibilite":
                    afficherCasesAccessibles();
                    break;
                case "viderCA":
                    viderCasesAccessibles();
                    break;

                default:
                    System.out.println("Type de mise à jour inconnu : " + arg);
            }
        }
    }

    public void colorierSelection() {
        if (jeuDames != null && jeuDames.getCaseSelectionnee() != null) {
            Position pos = jeuDames.getCaseSelectionnee();
            labels[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        }
    }

    public void viderSelection() {
        if (jeuDames.getCaseSelectionnee() != null) {
            Position pos = jeuDames.getCaseSelectionnee();
            labels[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }
    }

    public void afficherCasesAccessibles() {
        if (jeuDames.getCaseSelectionnee() != null) {
            Position caseSelectionnee = jeuDames.getCaseSelectionnee();
            Piece piece = plateau.getPiece(caseSelectionnee.x, caseSelectionnee.y);

            if (piece != null) {
                List<Position> deplacementsPossibles = piece.getDeplacementsPossibles(plateau, caseSelectionnee);

                for (Position p : deplacementsPossibles) {
                    if (p.x >= 0 && p.x < plateau.getAxeX() && p.y >= 0 && p.y < plateau.getAxeY()) {
                        labels[p.x][p.y].repaint();  // Demande de redessiner la case
                    }
                }
            }
        }
    }

    public void viderCasesAccessibles() {
        for (int i = 0; i < plateau.getAxeX(); i++) {
            for (int j = 0; j < plateau.getAxeY(); j++) {
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                labels[i][j].repaint();
            }
        }
    }

    @Override
    // Méthode pour redessiner les cases avec des cercles ou des filtres
    protected void paintCase(Graphics g, int x, int y) {
        if (jeu == null || jeuDames.getCaseSelectionnee() == null) {
            return;
        }

        Position caseSelectionnee = jeuDames.getCaseSelectionnee();

        if (x == caseSelectionnee.x && y == caseSelectionnee.y) {
            return;
        }

        if (plateau.estOccupe(new Position(x, y))) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(0, 0, 255, 80));
            g2.fillRect(0, 0, labels[x][y].getWidth(), labels[x][y].getHeight());
            g2.dispose();
        } else {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.BLUE);
            int diameter = Math.min(labels[x][y].getWidth(), labels[x][y].getHeight()) / 4;
            int cx = (labels[x][y].getWidth() - diameter) / 2;
            int cy = (labels[x][y].getHeight() - diameter) / 2;
            g2.fillOval(cx, cy, diameter, diameter);
            g2.dispose();
        }
    }



    @Override
    public void rafraichirGrille() {
        for (int i = 0; i < plateau.getAxeX(); i++) {
            for (int j = 0; j < plateau.getAxeY(); j++) {
                Piece p = plateau.getPiece(i, j);
                labels[i][j].setIcon(null);
                if (p != null && p.getTypePiece() != null) {
                    String chemin = switch (p.getTypePiece()) {
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
        new VueDames();
    }
}