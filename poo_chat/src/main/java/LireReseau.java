import java.net.*;


public class LireReseau {

    private TraiterData td = new TraiterData();
    private DatagramSocket receiverSocket ;
    private DatagramSocket receiverBroadcastSocket;
    private static Boolean running = true ;

    public static void setRunning(Boolean running) {
        LireReseau.running = running;
    }

    public LireReseau() {
        try {
            receiverSocket = new DatagramSocket();
            User.setPort(receiverSocket.getLocalPort());
            receiverBroadcastSocket = new DatagramSocket(5000);
            receiverBroadcastSocket.setBroadcast(true);
        } catch (Exception e){e.printStackTrace();}
    }

    public void get_udp_packet() {
        try {
            /*DatagramSocket receiverSocket = new DatagramSocket();
            User.setPort(receiverSocket.getPort());*/
            while(running) {
                System.out.println("Port réception : " + User.getPort());
                DatagramPacket receivedPacket = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
                receiverSocket.receive(receivedPacket);
                byte[] data = receivedPacket.getData();
                System.out.println("Lire Reseau packet recu : "+new String(data));
                td.traiter_data(data,receivedPacket.getAddress());
            }
        } catch ( java.io.IOException e){ e.printStackTrace();}
    }

    public void get_udp_broadcast_packet() {
        try {
            while(running) {
                DatagramPacket receivedPacket = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
                receiverBroadcastSocket.receive(receivedPacket);
                byte[] data = receivedPacket.getData();
                System.out.println("Lire Reseau Broadcast packet recu : "+new String(data));
                td.traiter_data_broadcast(data,receivedPacket.getAddress());
            }
        } catch ( java.io.IOException e){ e.printStackTrace();}
    }

    public void ThreadReceptionBroadcast(){

        new Thread(){
            @Override
            public void run(){
                    System.out.println("Thread Broadcast");
                    get_udp_broadcast_packet();
            }
        }.start();
    }

    public void ThreadReception(){
        new Thread(){
            @Override
            public void run(){
                    System.out.println("Thread Reception");
                    get_udp_packet();
            }
        }.start();
    }
}
