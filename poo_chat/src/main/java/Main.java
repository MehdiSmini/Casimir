
import java.io.*;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Main {

    public static MessageReseau mr = new MessageReseau();
    public static LireReseau lr = new LireReseau();
    public static ChoisirPseudo cp = new ChoisirPseudo();
    private static User user = new User();
    private static EnvoyerMessage em = new EnvoyerMessage();
    private static DemandeSession ds = new DemandeSession();

    private static Scanner Sc = new Scanner(System.in);

    public static void main(String[] args){
        /*ArrayList<String> test_agents = new ArrayList<String>();
        test_agents.add("Jean-Pierre");
        test_agents.add("Jean-Michel");
        test_agents.add("Jean-Paul");
        test_agents.add("Marie-Jeanne");
        test_agents.add("Jeanne-d'arc");
        cp.setAgents_actifs(test_agents);
        cp.choisir_pseudo();
        ChangerPseudo ca = new ChangerPseudo(cp);
        */
      try {
          lr.ThreadReceptionBroadcast();
          lr.ThreadReception();
          mr.broadcast_udp_packet("e");
          cp.choisir_pseudo(user);

          TimeUnit.SECONDS.sleep(5);
          //ds.demande_session("Jean-Charles");
          TimeUnit.SECONDS.sleep(5);
          //em.envoyer_message(new Message("Salut Jean-Charles",false,18,user.getPseudo()),"Jean-Charles");
          String cmd ;
          while(true){
              System.out.println(User.agents_actifs.toString());
            cmd = Sc.next();
            if(cmd.equals("msg")){
                System.out.println("Destinataire :");
                String cible = Sc.next();
                System.out.println("Message :");
                String msg = Sc.next();
                em.envoyer_message(new Message(msg,false,msg.length(),user.getPseudo()),cible);
            } else if (cmd.equals("session")){
              System.out.println("Cible :");
              String cible = Sc.next();
              ds.demande_session(cible);
            } else if (cmd.equals("pseudo")){
                cp.changerPseudo(user);
            }

            else if (cmd.equals("quit")){
                return;
            }
          }
      }catch (Exception e){}
    }
}
