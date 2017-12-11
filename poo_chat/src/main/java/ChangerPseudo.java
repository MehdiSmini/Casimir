
public class ChangerPseudo {


    public ChangerPseudo(ChoisirPseudo cp, User user, MessageReseau mr) {
        cp.choisir_pseudo(user, mr);
        System.out.println("Nouveau pseudo " + user.getPseudo());
    }
}
