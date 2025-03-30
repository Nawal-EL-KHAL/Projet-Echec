import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class MyFrame extends JFrame implements Observer {
    private Model model;
    JLabel[][] tabJLabel = new JLabel[8][8];

    public MyFrame(Model model) {
        this.model = model;
        setSize(480,480);
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

    @Override
    public void update(Observable o, Object arg) {
        ImageIcon icon = new ImageIcon("icons/black-bishop.png");
        tabJLabel[model.i][model.j].setIcon(icon);
    }
}
