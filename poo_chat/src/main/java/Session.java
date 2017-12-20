import java.util.ArrayList;

public class Session {

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setPseudo(String pseudo) {

        this.pseudo = pseudo;
    }

    public void setPseudo_cible(String pseudo_cible) {
        this.pseudo_cible = pseudo_cible;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Session{" +
                "pseudo='" + pseudo + '\'' +
                ", pseudo_cible='" + pseudo_cible + '\'' +
                ", etat=" + etat +
                ", messages=" + messages +
                '}';
    }

    private String pseudo;
    private String pseudo_cible;
    private Boolean etat;
    private ArrayList <Message> messages = new ArrayList<>();

    public String getPseudo() {
        return pseudo;
    }

    public String getPseudo_cible() {
        return pseudo_cible;
    }

    public Session(String pseudo, String pseudo_cible, Boolean etat) {
        this.pseudo = pseudo;
        this.pseudo_cible = pseudo_cible;
        this.etat = etat;
    }

    public void add_message(Message msg){
        messages.add(msg);
    }


}
