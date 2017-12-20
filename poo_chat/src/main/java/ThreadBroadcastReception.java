public class ThreadBroadcastReception  extends Thread{
    private LireReseau lr;

    public ThreadBroadcastReception(LireReseau lr) {
        this.lr = lr ;
    }

    @Override
    public void run(){
        System.out.println("Thread Broadcast");
        lr.get_udp_broadcast_packet();

    }
}
