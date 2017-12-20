import org.omg.CORBA.DATA_CONVERSION;

import java.net.InetAddress;
import java.util.Date;

public class TraiterData {

    public enum type_data{PSEUDO,MESSAGE,SESSION,ERROR;}
    private RecevoirMessage rm = new RecevoirMessage();
    private AccepterSession as = new AccepterSession();
    private DemandeSession ds = new DemandeSession();
    private byte[] d ;

    // changer les valeurs après ?
    public type_data get_type(byte[] data){
        if (data[0]=='a')
            return type_data.PSEUDO ;
        if (data[0]=='b')
            return type_data.MESSAGE ;
        if (data[0]=='c')
            return type_data.SESSION ;
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

    public Agent traiter_pseudo(byte[] data, InetAddress addr){
        int i = get_endrank(data);
        String pseudo = new String(remove_padding(java.util.Arrays.copyOfRange(data,0,i-5)));
        Integer port = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,i-5,i)));
        System.out.println(pseudo);
        return new Agent(pseudo,addr,port);
    }

    // date : j/m/A H:M

    public Message traiter_message(byte[] data){
        Integer taille = Integer.parseInt(new String(java.util.Arrays.copyOfRange(data,0,2)))  ;
        String message = new String(remove_padding(java.util.Arrays.copyOfRange(data,2,2+taille)));
        String pseudo = new String(java.util.Arrays.copyOfRange(data,2+taille,18+taille));
        return new Message(message,true, taille, pseudo);
    }

    public Session traiter_session(byte[] data){
        int i = get_endrank(data);
        String pseudo = new String(remove_padding(java.util.Arrays.copyOfRange(data, 0, i-User.getTaille_pseudo())));
        String pseudo_cible = new String(remove_padding(java.util.Arrays.copyOfRange(data,i-User.getTaille_pseudo(),i)));
        return new Session(pseudo,pseudo_cible,false);
    }


    public void traiter_data(byte[] data, InetAddress addr){
        type_data td = get_type(data);
        d = java.util.Arrays.copyOfRange(data,1,data.length);
        if( td == type_data.PSEUDO){
            User.add_agent(traiter_pseudo(data,addr));
        } else if( td == type_data.MESSAGE){
            rm.recevoir_message(traiter_message(d));
        } else if ( td == type_data.SESSION){
            Session session = traiter_session(d);
            if (session.getEtat())
                ds.get_answer(session);
            else
                as.accepter_session(session);
        } else {
            System.out.println("ERRROR");
            // error
        }
    }

}
