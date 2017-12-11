public class EnvoyerMessage {

    public void envoyer_message (Message msg, String pseudo_cible, MessageReseau mr){
        mr.send_udp_packet('b' + msg.getTaille().toString() + msg.getData() + msg.getEmetteur(),User.agents_actifs.get(pseudo_cible));
    }

}
