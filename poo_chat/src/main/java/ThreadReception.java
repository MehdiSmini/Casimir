public class ThreadReception  implements Runnable {
    private LireReseau lr;

    public ThreadReception(LireReseau lr) {
        this.lr = lr ;
    }

    @Override
    public void run(){
        Main.lr.get_udp_broadcast_packet();
    }
}
