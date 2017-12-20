
import java.io.*;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Main {
    public static ChoisirPseudo cp = new ChoisirPseudo();
    private static MessageReseau mr = new MessageReseau();
    public static LireReseau lr = new LireReseau();
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
          cp.choisir_pseudo(user,mr);
          /*ThreadReception tr = new ThreadReception(lr);
          ThreadBroadcastReception tbr = new ThreadBroadcastReception(lr);
          tr.start();
          tbr.start();*/

          lr.ThreadReceptionBroadcast();
          TimeUnit.SECONDS.sleep(5);
          mr.broadcast_udp_packet("Error");
          System.out.println("Error envoyé");
          ds.demande_session("Jean-Charles",mr);
          em.envoyer_message(new Message("Salut Jean-Charles",false,18,user.getPseudo()),"Jean-Charles",mr);
          String cmd ;
          while(true){
            cmd = Sc.next();
            if(cmd.equals("msg")){
                String cible = Sc.next();
                System.out.println("Destinataire :");
                String msg = Sc.next();
                System.out.println("Message :");
                em.envoyer_message(new Message(msg,false,msg.length(),user.getPseudo()),cible,mr);
            } else if (cmd.equals("quit")){
                break;
            }
          }
      }catch (Exception e){}
    }
}
