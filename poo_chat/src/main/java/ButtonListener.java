import java.util.ArrayList;

public class ButtonListener {
    private final Fenetre fen;

    public ButtonListener (Fenetre fen) {
        this.fen = fen;
    }

    public void buttonClicked(Agent agent) {
        /*ArrayList<Message> m = new ArrayList<Message>();
        m = session.getMessages();
        fen.Afficher(m);*/
    }

    public void button2Clicked(String pseudo_cible) {
        System.out.println(pseudo_cible);
        DemandeSession d = new DemandeSession();
        d.demande_session(pseudo_cible);
    }

}