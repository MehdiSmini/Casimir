public class ThreadBroadcastReception  implements Runnable{
    private LireReseau lr;

    public ThreadBroadcastReception(LireReseau lr) {
        this.lr = lr ;
    }

    @Override
    public void run(){
        Main.lr.get_udp_broadcast_packet();
    }
}
