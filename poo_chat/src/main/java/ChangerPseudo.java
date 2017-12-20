
public class ChangerPseudo {


    public ChangerPseudo(ChoisirPseudo cp, User user) {
        cp.choisir_pseudo(user);
        System.out.println("Nouveau pseudo " + user.getPseudo());
    }
}
