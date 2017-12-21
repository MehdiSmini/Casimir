public class EnvoyerMessage {

    public void envoyer_message (Message msg, String pseudo_cible){
        String taille = new String() ;
        if(msg.getTaille()<10)
            taille = "00"+msg.getTaille().toString();
        else if(msg.getTaille()<100)
            taille = "0"+msg.getTaille().toString();
        else if(msg.getTaille()<1000)
            taille = msg.getTaille().toString();
        else {
            System.out.println("La taille du message doit être inférieure à 1000 caractères");
            return;
        }
        Main.mr.send_udp_packet('b' + taille + msg.getData() + msg.getEmetteur(),User.agents_actifs.get(pseudo_cible));
    }

}
