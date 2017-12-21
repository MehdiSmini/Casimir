import java.util.ArrayList;
import java.util.Date;

public class RecevoirMessage {

    public void recevoir_message(Message msg){
        Session session = User.sessions.get(msg.getEmetteur());
        if (session != null && session.getEtat()){
            Date date = new Date();
            msg.setDate(date);
            session.add_message(msg);
            User.sessions.replace(msg.getEmetteur(),session);
            System.out.println(msg.getEmetteur()+" : "+msg.getData());// afficher msg
        }

    }



}
