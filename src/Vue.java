import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public abstract class Vue implements Observer {
    protected JFrame frame;
    protected JLabel[][] labels;
    protected Jeu jeu;
    protected DemoPlateau test;


    public Vue(Plateau plateau, Jeu jeu) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(plateau.getAxeX(), plateau.getAxeY()));

        labels = new JLabel[plateau.getAxeX()][plateau.getAxeY()];
        this.jeu = jeu;


        // Création de l'interface graphique
        for (int i = 0; i < plateau.getAxeX(); i++) {
            for (int j = 0; j < plateau.getAxeY(); j++) {
                int finalI = i;
                int finalJ = j;
                labels[i][j] = new JLabel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        paintCase(g, finalI, finalJ);  // Appelle la méthode pour dessiner sur la case
                    }
                };
                labels[i][j].setOpaque(true);

                if (plateau.isDamier()){
                    labels[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                }else {
                    labels[i][j].setBackground(Color.DARK_GRAY);
                }

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


    }

    @Override
    public abstract void update(Observable o, Object arg);


    public abstract void rafraichirGrille();

    // Méthode pour redessiner les cases avec des cercles ou des filtres
    protected abstract void paintCase(Graphics g, int x, int y);

    public JFrame getFrame() {
        return frame;
    }


    public JLabel[][] getLabels() {
        return labels;
    }

    public Jeu getJeu() {
        return jeu;
    }
}