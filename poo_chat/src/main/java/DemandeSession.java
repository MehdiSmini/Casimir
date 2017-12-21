
import java.util.*;

public class DemandeSession {

    public void demande_session(String pseudo_cible){
        Session session ;
        System.out.println("Demande de session avec "+pseudo_cible);
        System.out.println("Liste agents actifs "+User.agents_actifs.toString());
        Main.mr.send_udp_packet("c0" + User.getPseudo() + pseudo_cible, User.agents_actifs.get(pseudo_cible));
        User.add_session(new Session(User.getPseudo(),pseudo_cible,false));
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while (elapsedTime < 500){
            session = User.sessions.get(pseudo_cible);
            if (session.getEtat()) {
                // messages = get_message_previous_session(session)
                // session.setMessages(messages)
                System.out.println("Session établie avec " + pseudo_cible);
                return;
            }
            elapsedTime = new Date().getTime() - startTime;

        }
        User.sessions.remove(pseudo_cible);
    }

    public void get_answer(Session session){
        System.out.println("Sessions enregistrées " +User.sessions.toString());
        if (User.sessions.containsKey(session.getPseudo())) {
            Session session1 = new Session(session.getPseudo_cible(), session.getPseudo(), true);
            User.sessions.replace(session1.getPseudo_cible(), session1);
        }

    }
}
