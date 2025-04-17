

public class Main {
    public static void main(String[] args) {

        Model model = new Model();
        MyFrame myFrame = new MyFrame(model);

        model.addObserver(myFrame); // Quand model change, MyFrame sera notifié via update() et pourra mettre à jour l'affichage
    }
}