public class AccepterSession {
    private MessageReseau mr = new MessageReseau();

    public void accepter_session(Session session){
        Session session1 = new Session(session.getPseudo_cible(),session.getPseudo(),false);
        if(User.sessions.containsValue(session1)){
            session1.setEtat(true);
            //demande session(session)
            //messages = get_messages_previous_session
            //session.setMessages(messages);
            User.sessions.replace(session1.getPseudo_cible(),session1);
        }
        session1.setEtat(true);
        //demande session(session1)
        //messages = get_messages_previous_session
        User.add_session(session1);
    }
}
