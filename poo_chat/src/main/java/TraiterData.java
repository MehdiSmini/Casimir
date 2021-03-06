import org.omg.CORBA.DATA_CONVERSION;

import java.net.InetAddress;
import java.util.Date;

public class TraiterData {

    public enum type_data{MESSAGE,SESSION,CONNEXION,DECONNEXION,INIT,ERROR;}
    private RecevoirMessage rm = new RecevoirMessage();
    private AccepterSession as = new AccepterSession();
    private DemandeSession ds = new DemandeSession();
    private byte[] d ;


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
        System.out.println("endrank traiter agent :"+i);
        String pseudo = traiter_pseudo(remove_padding(java.util.Arrays.copyOfRange(data,0,i-5)));
        Integer port = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,i-5,i)));
        System.out.println(pseudo);
        return new Agent(pseudo,addr,port);
    }


    public Message traiter_message(byte[] data){
        Integer taille = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,0,3)))  ;
        String message = new String(remove_padding(java.util.Arrays.copyOfRange(data,3,3+taille)));
        String pseudo = new String(remove_padding(java.util.Arrays.copyOfRange(data,3+taille,24+taille)));
        return new Message(message,true, taille, pseudo);
    }

    public Session traiter_session(byte[] data){
        int i = get_endrank(data);
        System.out.println("endrank traiter session :"+i);
        Boolean etat = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data, 0, 1))) == 1;
        String pseudo = new String(remove_padding(java.util.Arrays.copyOfRange(data, 1, i-User.getTaille_pseudo())));
        String pseudo_cible = new String(remove_padding(java.util.Arrays.copyOfRange(data,i-User.getTaille_pseudo(),i)));
        return new Session(pseudo,pseudo_cible,etat);
    }

    public Integer traiter_init(byte[] data){
        int i = get_endrank(data);
        System.out.println("endrank init agent :"+i);
        Integer port = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,0,i)));
        return port;
    }


    public void traiter_data(byte[] data, InetAddress addr){
        System.out.println("traiter data adress : "+addr.toString());
        type_data td = get_type(data);
        d = java.util.Arrays.copyOfRange(data,1,data.length);
        if( td == type_data.CONNEXION){
            gestion_agents(d,addr);
            System.out.println("agents actifs traiter data : " + User.agents_actifs.toString());
            
        } else if( td == type_data.MESSAGE){
            System.out.println("Type Message reçu");
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
            System.out.println("ERROR");
        }
    }

    public void traiter_data_broadcast(byte[] data, InetAddress addr) {
        type_data td = get_type(data);
        System.out.println("traiter data broadcast adress : "+addr.toString());
        d = java.util.Arrays.copyOfRange(data, 1, data.length);
        if (td == type_data.INIT) {
            System.out.println((User.getPseudo() == null));
            System.out.println(User.getPseudo());
            if (!(User.getPseudo() == null))
                Main.mr.send_udp_packet("a" +User.getTaille_lastpseudo()+ User.getLast_pseudo()+User.getPseudo() + User.getPort(), new Agent("", addr, traiter_init(d)));
        } else if (td == type_data.CONNEXION) {
            System.out.println("Evaluation de d dans broadcast :"+new String(d));
            gestion_agents(d,addr);
            System.out.println("agents actifs broadcast traiter data : " + User.agents_actifs.toString());
        } else if (td == type_data.DECONNEXION) {
            Agent agent = traiter_agent(d,addr);
            if (User.agents_actifs.containsKey(agent.getPseudo())){
                User.agents_actifs.remove(agent.getPseudo());
                System.out.println("agents actifs après suppression traiter data : " + User.agents_actifs.toString());
            }
        } else
            System.out.println("Error");

    }


    private void gestion_agents(byte [] d, InetAddress addr) {
        Integer taille = Integer.parseInt(new String(java.util.Arrays.copyOfRange(d, 0, 2)));
        System.out.println("Taille gestion agents :"+taille);
        String oldPseudo = traiter_pseudo(java.util.Arrays.copyOfRange(d, 2, taille + 2));
        System.out.println("old :" + oldPseudo);
        byte[] d1 = java.util.Arrays.copyOfRange(d, taille + 2, d.length);
        Agent agent = traiter_agent(d1, addr);
        System.out.println("agent :" + agent);
        if (User.agents_actifs.containsKey(oldPseudo)) {
            System.out.println("Ancien pseudo deja existant");
            User.agents_actifs.remove(oldPseudo);
            Main.fenetre.removeFromAgentBox(oldPseudo);
        }
        if (!User.agents_actifs.containsKey(agent.getPseudo()))
            Main.fenetre.addToAgentsBox(agent.getPseudo());
            User.add_agent(agent);
        if (User.sessions.containsKey(oldPseudo)) {
            Session newSession = User.sessions.remove(oldPseudo);
            Main.fenetre.removeFromSessionBox(oldPseudo);
            Main.fenetre.addToSessionBox(agent.getPseudo());
            newSession.setPseudo_cible(agent.getPseudo());
            User.add_session(newSession);
        }

    }
}
