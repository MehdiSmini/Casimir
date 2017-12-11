import java.net.*;


public class LireReseau {

    TraiterData td = new TraiterData();
    DatagramSocket receiverSocket ;
    DatagramSocket receiverBroadcastSocket;

    public LireReseau() {
        try {
            receiverSocket = new DatagramSocket();
            receiverSocket.connect(receiverSocket.getLocalSocketAddress());
            User.setPort(receiverSocket.getPort());
            receiverBroadcastSocket = new DatagramSocket(5000);
            receiverBroadcastSocket.connect();
        } catch (Exception e){e.printStackTrace();}
    }

    public void get_udp_packet() {
        try {
            /*DatagramSocket receiverSocket = new DatagramSocket();
            User.setPort(receiverSocket.getPort());*/
            while(true) {
                DatagramPacket receivedPacket = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
                receiverSocket.receive(receivedPacket);
                byte[] data = receivedPacket.getData();
                receiverSocket.close();
                td.traiter_data(data,receivedPacket.getAddress());
            }
        } catch ( java.io.IOException e){ e.printStackTrace();}
    }

    public void get_udp_broadcast_packet() {
        try {
            while(true) {
                DatagramPacket receivedPacket = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
                receiverBroadcastSocket.receive(receivedPacket);
                byte[] data = receivedPacket.getData();
                receiverBroadcastSocket.close();
                td.traiter_data(data,receivedPacket.getAddress());
            }
        } catch ( java.io.IOException e){ e.printStackTrace();}
    }
}
