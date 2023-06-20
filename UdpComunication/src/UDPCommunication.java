import java.net.*;

public class UDPCommunication {
    private DatagramSocket serverSocket;
    private DatagramSocket clientSocket;

    public void startServer(int port) {
        try {
            serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String(receivePacket.getData());
                System.out.println("Received from client: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String serverAddress, int serverPort) {
        try {
            clientSocket = new DatagramSocket();
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);

            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverInetAddress, serverPort);
            clientSocket.send(sendPacket);

            System.out.println("Sent to server: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                clientSocket.close();
            }
        }
    }

    public void broadcastMessage(String message, int serverPort) {
        try {
            clientSocket = new DatagramSocket();
            clientSocket.setBroadcast(true);

            byte[] sendData = message.getBytes();
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcastAddress, serverPort);
            clientSocket.send(sendPacket);

            System.out.println("Broadcasted message: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                clientSocket.close();
            }
        }
    }

    public static void main(String[] args) {
        UDPCommunication udpCommunication = new UDPCommunication();

        // Start the server in a separate thread
        new Thread(() -> {
            udpCommunication.startServer(9876);
        }).start();

        // Delay to ensure the server is started before sending the message
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Send a unicast message to the server
        udpCommunication.sendMessage("Hello, server!", "<Pi_IP_Address>", 9876);

        // Send a broadcast message to all servers on the network
        udpCommunication.broadcastMessage("Hello, servers!", 9876);
    }
}
