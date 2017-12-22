import java.util.*;
import java.io.*;

public class ChoisirPseudo {

    private String new_pseudo = "";
    private  Scanner Sc = new Scanner(System.in);
    private Boolean pseudo_state = false;

    public Boolean getPseudo_state() {
        return pseudo_state;
    }

    public void setPseudo_state(Boolean pseudo_state) {
        this.pseudo_state = pseudo_state;
    }

    public void choisir_pseudo(String pseud){
        String last_pseudo = User.getPseudo();
        pseudo_state = false ;

        System.out.println("Rentrez un pseudo (3-20 caractères):");

        new_pseudo = pseud;
        if(User.agents_actifs.containsKey(new_pseudo)){
            System.out.println("Pseudo déja existant");
        } else {
            if (new_pseudo.length() < 21 && new_pseudo.length() > 2) {
                pseudo_state = true;
                System.out.println("Bonjour " + new_pseudo);
                String taille ;
                String port;
                if(last_pseudo==null)
                    taille = "04" ;
                else if (last_pseudo.length() < 10)
                    taille = "0"+last_pseudo.length();
                else
                    taille = ""+last_pseudo.length();
                if(User.getPort()<10000)
                    port="0"+User.getPort();
                else
                    port=""+User.getPort();
                System.out.println("Choisir pseudo taille :"+taille);
                Main.mr.broadcast_udp_packet("a"+taille+last_pseudo+new_pseudo+port);
                User.setPseudo(new_pseudo);
                User.setLast_pseudo(last_pseudo);
                System.out.println("Dans choisir pseudo "+User.getPseudo());
                stocker_pseudo("user_pseudo.csv");
            } else {
                System.out.println("Taille non conforme");
            }
        }

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
