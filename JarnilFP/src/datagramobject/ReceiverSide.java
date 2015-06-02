package datagramobject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ReceiverSide {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException {
        
        InetAddress address = InetAddress.getByName(INET_ADDR);
       
        byte[] buf = new byte[256];
        
        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
            clientSocket.joinGroup(address);
     
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);

                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
                
                try{
                    Object readObj = ois.readObject();
                
                    Message message = (Message) readObj;
                    System.out.println("Message is: " + message.getMessage() + ", ini pesan dari " + message.getSender());
                    ois.close();
                }
                catch(Exception e){
                    System.out.println("No object could be read from the "
                            + "received UDP datagram.");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

}

