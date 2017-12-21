import org.omg.CORBA.DATA_CONVERSION;

import java.net.InetAddress;
import java.util.Date;

public class TraiterData {

    public enum type_data{MESSAGE,SESSION,CONNEXION,DECONNEXION,INIT,ERROR;}
    private RecevoirMessage rm = new RecevoirMessage();
    private AccepterSession as = new AccepterSession();
    private DemandeSession ds = new DemandeSession();
    private byte[] d ;

    // changer les valeurs après ?
    public type_data get_type(byte[] data){
        if (data[0]=='a')
            return type_data.CONNEXION ;
        if (data[0]=='b')
            return type_data.MESSAGE ;
        if (data[0]=='c')
            return type_data.SESSION ;
        if (data[0]=='d')
            return type_data.DECONNEXION;
        if (data[0]=='e')
            return type_data.INIT;
        return type_data.ERROR;
    }

    /*byte[] dataU ;
    dataU = java.util.Arrays.copyOfRange(data, 1, data.length);*/

    public Integer get_endrank(byte[] data){
        int i = 0 ;
        for (byte j:data){
            if(j==0)
                return i;
            i++;
        }
        return i;
    }

    public byte[] remove_padding(byte[] data){
        return java.util.Arrays.copyOfRange(data,0,get_endrank(data));
    }

    public String traiter_pseudo(byte[] data){
        return new String(data);
    }
    public Agent traiter_agent(byte[] data, InetAddress addr){
        int i = get_endrank(data);
        String pseudo = traiter_pseudo(remove_padding(java.util.Arrays.copyOfRange(data,0,i-5)));
        Integer port = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,i-5,i)));
        System.out.println(pseudo);
        return new Agent(pseudo,addr,port);
    }

    // date : j/m/A H:M

    public Message traiter_message(byte[] data){
        Integer taille = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,0,3)))  ;
        String message = new String(remove_padding(java.util.Arrays.copyOfRange(data,2,2+taille)));
        String pseudo = new String(java.util.Arrays.copyOfRange(data,2+taille,18+taille));
        return new Message(message,true, taille, pseudo);
    }

    public Session traiter_session(byte[] data){
        int i = get_endrank(data);
        Boolean etat = new Boolean(Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,0,1))) == 1);
        String pseudo = new String(remove_padding(java.util.Arrays.copyOfRange(data, 1, i-User.getTaille_pseudo())));
        String pseudo_cible = new String(remove_padding(java.util.Arrays.copyOfRange(data,i-User.getTaille_pseudo(),i)));
        return new Session(pseudo,pseudo_cible,etat);
    }

    public Integer traiter_init(byte[] data){
        int i = get_endrank(data);
        Integer port = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,0,i)));
        return port;
    }


    public void traiter_data(byte[] data, InetAddress addr){
        System.out.println("traiter data adress : "+addr.toString());
        type_data td = get_type(data);
        d = java.util.Arrays.copyOfRange(data,1,data.length);
        if( td == type_data.CONNEXION){
            Integer taille = Integer.parseInt(new String(java.util.Arrays.copyOfRange(d,0,1)));
            String oldPseudo = traiter_pseudo(java.util.Arrays.copyOfRange(d,1,taille+1));
            byte[] d1 = java.util.Arrays.copyOfRange(d,taille+1,d.length);
            Agent agent= traiter_agent(d1,addr);
            System.out.println("agents actifs traiter data : "+User.agents_actifs.toString());
            if(User.agents_actifs.containsKey(oldPseudo)){
                User.agents_actifs.remove(oldPseudo);
            if (!User.agents_actifs.containsKey(agent.getPseudo()))
                User.add_agent(agent);
            if(User.sessions.containsKey(oldPseudo)){
                Session newSession = User.sessions.remove(oldPseudo);
                newSession.setPseudo_cible(agent.getPseudo());
                User.add_session(newSession);
            }

            System.out.println("test traiter data"+agent);
            //Main.mr.send_udp_packet("a"+User.getPseudo()+User.getPort(),agent);
            }
        } else if( td == type_data.MESSAGE){
            rm.recevoir_message(traiter_message(d));
        } else if ( td == type_data.SESSION){

            System.out.println("Type Session reçu");
            Session session = traiter_session(d);
            System.out.println(session);
            if (session.getEtat())
                ds.get_answer(session);
            else
                as.accepter_session(session);
        } else {
            System.out.println("ERRROR");
            // error
        }
    }

    public void traiter_data_broadcast(byte[] data, InetAddress addr) {
        type_data td = get_type(data);
        System.out.println("traiter data broadcast adress : "+addr.toString());
        d = java.util.Arrays.copyOfRange(data, 1, data.length);
        if (td == type_data.INIT)
            Main.mr.send_udp_packet("a"+ User.getPseudo()+User.getPort(),new Agent("",addr,traiter_init(d)));
        else if (td == type_data.CONNEXION) {
            Agent agent = traiter_agent(d, addr);
            System.out.println("agents actifs broadcast traiter data : " + User.agents_actifs.toString());
            if (!User.agents_actifs.containsKey(agent.getPseudo())) {
                User.add_agent(agent);
                System.out.println("test traiter broadcast data" + agent);
                //Main.mr.send_udp_packet("a" + User.getPseudo() + User.getPort(), agent);
            }

        } else if (td == type_data.DECONNEXION) {
            Agent agent = traiter_agent(d,addr);
            if (User.agents_actifs.containsKey(agent.getPseudo())){
                User.agents_actifs.remove(agent.getPseudo());
                System.out.println("agents actifs après suppression traiter data : " + User.agents_actifs.toString());
            }
        } else
            System.out.println("Error");
    }

}
