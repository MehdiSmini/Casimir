public class AccepterSession {


    public void accepter_session(Session session){
        if(!User.sessions.containsKey(session.getPseudo())){
            System.out.println("Demande de sessions de la part de " + session.getPseudo());
            Session session1 = new Session(session.getPseudo_cible(),session.getPseudo(),true);
            Main.mr.send_udp_packet("c1" + User.getPseudo() + session1.getPseudo_cible(), User.agents_actifs.get(session1.getPseudo_cible()));
            //messages = get_messages_previous_session
            //session.setMessages(messages);
            User.add_session(session1);
        }
    }
}