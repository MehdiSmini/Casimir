import java.net.InetAddress;

public class Agent {

    private String pseudo ;

    private InetAddress addr;

    private Integer port;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Agent(String pseudo, InetAddress addr, Integer port) {

        this.pseudo = pseudo;
        this.addr = addr;
        this.port = port;
    }
}
