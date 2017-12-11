
import java.util.*;

public class DemandeSession {

    public void demande_session(String pseudo_cible, MessageReseau mr){
        Session session ;
        mr.send_udp_packet("c" + User.getPseudo() + pseudo_cible, User.agents_actifs.get(pseudo_cible));
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while (elapsedTime < 500){
            session = User.sessions.get(pseudo_cible);
            if (session != null) {
                if (session.getEtat()) {
                    // messages = get_message_previous_session(session)
                    // session.setMessages(messages)
                    System.out.println("Session établie avec " + pseudo_cible);
                    return;
                }
            }
            elapsedTime = new Date().getTime() - startTime;

        }
    }

    public void get_answer(Session session){
        Session session1 = new Session(session.getPseudo_cible(),session.getPseudo(),false);
        Session session2 = new Session(session.getPseudo_cible(),session.getPseudo(),true);
        if (User.sessions.containsValue(session1)){
            User.sessions.replace(session.getPseudo_cible(),session2);
        } else {
            User.add_session(session2);
        }

    }
}
