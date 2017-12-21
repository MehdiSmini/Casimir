import java.util.*;
import java.io.*;

public class ChoisirPseudo {

    private String new_pseudo = "";
    private  Scanner Sc = new Scanner(System.in);
    private Boolean pseudo_state = false;

    public void choisir_pseudo(User user){
        String last_pseudo = user.getPseudo();
        pseudo_state = false ;
        while(!pseudo_state) {
            System.out.println("Rentrez un pseudo (3-9 caractères):");
            new_pseudo = Sc.next();
            if(user.agents_actifs.containsKey(new_pseudo)){
                System.out.println("Pseudo déja existant");
            } else {
                if (new_pseudo.length() < 10 && new_pseudo.length() > 3) {
                    pseudo_state = true;
                    System.out.println("Bonjour " + new_pseudo);
                    Integer taille ;
                    if(last_pseudo==null)
                        taille = 4 ;
                    else if (last_pseudo.length() > 10)
                        taille = 2;
                    else
                        taille = 1;
                    Main.mr.broadcast_udp_packet("a"+taille+last_pseudo+new_pseudo+User.getPort());
                    user.setPseudo(new_pseudo);
                    user.setLast_pseudo(last_pseudo);
                    System.out.println("Dans choisir pseudo "+User.getPseudo());
                    stocker_pseudo("user_pseudo.csv");
                } else {
                    System.out.println("Taille non conforme");
                }
            }

        }

    }

    public void changerPseudo(User user) {
        choisir_pseudo(user);
        System.out.println("Nouveau pseudo " + user.getPseudo());
    }

     public void stocker_pseudo(String chemin){
        File csvFile = new File(chemin);
        try{
            PrintStream l_out = new PrintStream(new FileOutputStream(chemin));
            l_out.print(new_pseudo);
            l_out.flush();
            l_out.close();
            l_out=null;
        } catch (java.io.FileNotFoundException e){}
    }

}
