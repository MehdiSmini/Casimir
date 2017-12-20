public class ThreadReception  extends Thread {
    private LireReseau lr;

    public ThreadReception(LireReseau lr) {
        this.lr = lr ;
    }

    @Override
    public void run(){
        System.out.println("Thread Reception");
        lr.get_udp_packet();

    }
}
