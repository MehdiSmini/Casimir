import java.net.*;

public class MessageReseau {

    public void send_udp_packet(String packet, Agent agent){
        try {
            DatagramSocket senderSocket = new DatagramSocket();
            byte[] data = packet.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            datagramPacket.setAddress(agent.getAddr() );
            datagramPacket.setPort(agent.getPort());
            senderSocket.send(datagramPacket);
            System.out.println(packet+" envoye a "+ agent);
            senderSocket.close();
        } catch (java.io.IOException e){e.printStackTrace();}
    }

    public void broadcast_udp_packet(String packet){
        try {
            DatagramSocket senderSocket = new DatagramSocket();
            senderSocket.setBroadcast(true);
            byte[] data = packet.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
            datagramPacket.setPort(5000);
            senderSocket.send(datagramPacket);
            System.out.println(packet + " broadcaste");
            senderSocket.close();
        } catch (java.io.IOException e){e.printStackTrace();}
    }
}
