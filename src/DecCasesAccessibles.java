import java.util.ArrayList;

public abstract class DecCasesAccessibles {

    protected DecCasesAccessibles base;

    public DecCasesAccessibles(DecCasesAccessibles base) {
        this.base = base;
    }

    // méthode à implémenter dans les sous-classes
    public abstract ArrayList<Case> getMesCA();

    public ArrayList<Case> getCA() {
        ArrayList<Case> retour = new ArrayList<>(getMesCA());

        if (base != null) {
            retour.addAll(base.getCA());
        }

        return retour;
    }
}
