import java.util.ArrayList;

public abstract class DecCasesAccessibles {

    DecCasesAccessibles base = new DecCasesAccessibles() {};

    public abstract ArrayList<Case> getMesCases(){
        return base.getMesCases();
    }
}
