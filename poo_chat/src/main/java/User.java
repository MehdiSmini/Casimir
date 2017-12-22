import java.util.ArrayList;
import java.util.HashMap;
import java.net.*;

public class User {

    private static String pseudo;

    private static String last_pseudo;

    public static String getLast_pseudo() {
        return last_pseudo;
    }

    public static void setLast_pseudo(String last_pseudo) {
        User.last_pseudo = last_pseudo;
    }

    public static Integer getTaille_pseudo() {
        return pseudo.length();
    }

    public static String getTaille_lastpseudo() {
        if (!(last_pseudo==null)) {
            if (last_pseudo.length()<10)
                return "0"+last_pseudo.length();
            else
                return "" + last_pseudo.length();
        }
        else
            return "04";
    }

    public static void add_session(Session session) {
        sessions.put(session.getPseudo_cible(),session);
    }

    public static HashMap<String,Session> sessions = new HashMap<>();

    public static HashMap<String,Agent> agents_actifs = new HashMap<>();

    public static void add_agent(Agent agent) {
        agents_actifs.put(agent.getPseudo(),agent);
    }


    public static void setPseudo(String Pseudo) {
        pseudo = Pseudo;
    }

    public static String getPseudo() {
        return pseudo;
    }

    private static int port ;

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        User.port = port;
    }
}
