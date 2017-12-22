import java.util.Date;

public class Message {
    private String data ;
    private Date date ;
    private Boolean etat ;
    private Integer taille ;
    private String emetteur;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Integer getTaille() {
        return taille;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public void setEmetteur(String emetteur) {
        this.emetteur = emetteur;
    }

    public String getEmetteur(){
        return emetteur;

    }

    public Message(String data,Boolean etat, Integer taille,String pseudo) {
        this.data = data;
        this.date = new Date();
        this.emetteur = pseudo;
        this.etat = etat;
        this.taille=taille;
    }

    @Override
    public String toString() {
        return
                 date.toInstant() + " "+
                 emetteur + " :  " +
                 data  ;
    }
}
