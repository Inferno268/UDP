import java.net.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a DatagramSocket to listen for incoming UDP packets
            DatagramSocket serverSocket = new DatagramSocket(9876);

            byte[] receiveData = new byte[1024];

            while (true) {
                // Create a DatagramPacket to receive the incoming UDP packet
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive the UDP packet from the client
                serverSocket.receive(receivePacket);

                // Extract the message from the received packet
                String message = new String(receivePacket.getData());

                System.out.println("Received from client: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}