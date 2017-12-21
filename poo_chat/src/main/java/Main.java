
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
    private static boolean finished = false ;

    private static Scanner Sc = new Scanner(System.in);

    public static void main(String[] args){

      try {
          lr.ThreadReceptionBroadcast();
          lr.ThreadReception();
          mr.broadcast_udp_packet("e"+user.getPort());
          cp.choisir_pseudo();

          TimeUnit.SECONDS.sleep(5);

          String cmd ;
          while(!finished){
              System.out.println(User.agents_actifs.toString());
            cmd = Sc.next();
            if(cmd.equals("msg")){
                System.out.println("Destinataire :");
                String cible = Sc.next();
                if (!user.sessions.containsKey(cible))
                    System.out.println("Pas de session avec "+cible);
                else{
                    System.out.println("Message :");
                    String msg = Sc.next();
                    em.envoyer_message(new Message(msg,false,msg.length(),user.getPseudo()),cible);
                }
            } else if (cmd.equals("session")){
              System.out.println("Cible :");
              String cible = Sc.next();
              if (!user.agents_actifs.containsKey(cible))
                  System.out.println(cible+" pas present sur le reseau");
              else
                  ds.demande_session(cible);
            } else if (cmd.equals("pseudo")){
                cp.changerPseudo();
            } else if (cmd.equals("close")){
                System.out.println("Cible :");
                String cible = Sc.next() ;
                if (user.sessions.containsKey(cible))
                    user.sessions.remove(cible);
                else
                    System.out.println("Pas de session avec "+ cible);
            } else if (cmd.equals("quit")){
                mr.broadcast_udp_packet("d"+user.getPseudo()+User.getPort());
                lr.setRunning(false);
                finished = true;
            }
          }
      }catch (Exception e){}
    }
}
