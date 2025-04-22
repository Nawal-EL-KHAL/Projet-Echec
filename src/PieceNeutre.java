import javax.swing.*;
import java.awt.Image;
import java.util.*;

public class PieceNeutre implements Piece {
    private boolean estBlanc;
    private String nom;
    private String cheminImage;

    public PieceNeutre(boolean estBlanc, String nom, String cheminImage) {
        this.estBlanc = estBlanc;
        this.nom = nom;
        this.cheminImage = cheminImage;
    }

    @Override
    public List<Position> getDeplacementsPossibles(Plateau plateau, Position pos) {
        return Collections.emptyList(); // le déplacement vient du décorateur
    }

    @Override
    public boolean estBlanche() {
        return estBlanc;
    }

    @Override
    public Icon getIcon() {
        ImageIcon icon = new ImageIcon(cheminImage);
        Image image = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    @Override
    public String getNom() {
        return nom;
    }
}
