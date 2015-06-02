package datagramobject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ReceiverSender {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    static String message;
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        
        InetAddress address = InetAddress.getByName(INET_ADDR);

        byte[] buf = new byte[256];

        try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {
            clientSocket.joinGroup(address);

            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);

                String msg = new String(buf, 0, buf.length);
                System.out.println("received msg: " + msg);
                message = msg;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (DatagramSocket serverSocket = new DatagramSocket()) {
           
            String msg = message;
            DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                    msg.getBytes().length, addr, PORT);
            serverSocket.send(msgPacket);

            System.out.println("sent packet with msg: " + msg);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
