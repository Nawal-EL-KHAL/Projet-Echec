import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class MyFrame extends JFrame implements Observer {
    private Model model;
    JLabel[][] tabJLabel = new JLabel[8][8];
    private final int sizeX = 10; // taille de la grille affichée
    private final int sizeY = 50;
    private static final int pxCase = 50; // nombre de pixel par case
    private JLabel selectedPiece = null; // Stocke la pièce sélectionnée
    private int selectedRow = -1, selectedCol = -1; // Coordonnées de la pièce sélectionnée

    public MyFrame(Model model) {
        this.model = model;
        setTitle("Jeu d'Échecs");
        setResizable(false);
        setSize(sizeX * pxCase, sizeX * pxCase);
        build();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        installation();
    }

    public void build(){
        JPanel jp = new JPanel(new GridLayout(8,8));
        setContentPane(jp);

        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                JLabel jl = new JLabel();
                jl.setOpaque(true);
                if ((i+j)%2 == 0){
                    jl.setBackground(Color.WHITE);
                } else {
                    jl.setBackground(Color.BLACK);
                }
                // Centre l'icône dans le JLabel
                jl.setHorizontalAlignment(SwingConstants.CENTER);
                jl.setVerticalAlignment(SwingConstants.CENTER);
                jp.add(jl);
                tabJLabel[i][j] = jl;
                final int ii=i;
                final int jj=j;
                jl.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleClick(ii, jj);
                    }
                });

//                jl.addMouseListener(
//                        new MouseAdapter() {
//                            @Override
//                            public void mouseClicked(MouseEvent e) {
//                                model.set(ii,jj);
//                               System.out.println(ii + " " + jj); // Pour débogage
//                            }
//                        }
//                );
            }
        }
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        ImageIcon icon = new ImageIcon(urlIcone);

        // Redimensionner l'icône
        Image img = icon.getImage().getScaledInstance(pxCase, pxCase, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(img);

        return resizedIcon;
    }



    @Override
    public void update(Observable o, Object arg) {
        ImageIcon icon = chargerIcone("icons/black-bishop.png");
        tabJLabel[model.i][model.j].setIcon(icon);
    }


    public void installation() {
        ImageIcon fouNoir = chargerIcone("icons/black-bishop.png");
        ImageIcon roiNoir = chargerIcone("icons/black-king.png");
        ImageIcon cavalierNoir = chargerIcone("icons/black-knight.png");
        ImageIcon pionNoir = chargerIcone("icons/black-pawn.png");
        ImageIcon reineNoir = chargerIcone("icons/black-queen.png");
        ImageIcon tourNoir = chargerIcone("icons/black-rook.png");
        ImageIcon fouBlanc = chargerIcone("icons/white-bishop.png");
        ImageIcon roiBlanc = chargerIcone("icons/white-king.png");
        ImageIcon cavalierBlanc = chargerIcone("icons/white-knight.png");
        ImageIcon pionBlanc = chargerIcone("icons/white-pawn.png");
        ImageIcon reineBlanc = chargerIcone("icons/white-queen.png");
        ImageIcon tourBlanc = chargerIcone("icons/white-rook.png");
        tabJLabel[0][0].setIcon(tourNoir);
        tabJLabel[0][1].setIcon(cavalierNoir);
        tabJLabel[0][2].setIcon(fouNoir);
        tabJLabel[0][3].setIcon(reineNoir);
        tabJLabel[0][4].setIcon(roiNoir);
        tabJLabel[0][5].setIcon(fouNoir);
        tabJLabel[0][6].setIcon(cavalierNoir);
        tabJLabel[0][7].setIcon(tourNoir);
        tabJLabel[7][0].setIcon(tourBlanc);
        tabJLabel[7][1].setIcon(cavalierBlanc);
        tabJLabel[7][2].setIcon(fouBlanc);
        tabJLabel[7][3].setIcon(reineBlanc);
        tabJLabel[7][4].setIcon(roiBlanc);
        tabJLabel[7][5].setIcon(fouBlanc);
        tabJLabel[7][6].setIcon(cavalierBlanc);
        tabJLabel[7][7].setIcon(tourBlanc);
        for (int i=0; i<8; i++){tabJLabel[1][i].setIcon(pionNoir);}
        for (int i=0; i<8; i++){tabJLabel[6][i].setIcon(pionBlanc);}
    }

    private void handleClick(int row, int col) {
        JLabel clickedLabel = tabJLabel[row][col];

        if (selectedPiece == null) {
            // Sélectionne la pièce si elle est présente
            if (clickedLabel.getIcon() != null) {
                selectedPiece = clickedLabel;
                selectedRow = row;
                selectedCol = col;
                clickedLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Visuel pour la sélection
            }
        } else {
            // Déplace la pièce vers la nouvelle case si elle est vide ou occupée par un adversaire
            if (clickedLabel != selectedPiece) {
                clickedLabel.setIcon(selectedPiece.getIcon());
                selectedPiece.setIcon(null);
                selectedPiece.setBorder(null); // Supprime le cadre rouge
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }
}
