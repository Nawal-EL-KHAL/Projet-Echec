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

    public MyFrame(Model model) {
        this.model = model;
        setTitle("Jeu d'Échecs");
        setResizable(false);
        setSize(sizeX * pxCase, sizeX * pxCase);
        build();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ferme le programme lorsque la fenêtre est fermée
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

//                System.out.println(i + " " + j); // Pour débogage

                final int ii=i;
                final int jj=j;
                jl.addMouseListener(
                        new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                model.set(ii,jj);
//                               System.out.println(ii + " " + jj); // Pour débogage
                            }
                        }
                );
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
}
