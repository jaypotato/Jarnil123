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

                /*String msg = new String(buf, 0, buf.length);
                System.out.println("received msg: " + msg + "\n\n");
                */
                
                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
                
                try{
                    Object readObj = ois.readObject();
                //    if (readObj instanceof String) {
                    Message message = new Message(readObj);
                    System.out.println("Message is: " + message);
                    ois.close();
                //}
                    //else 
                //{
                //    System.out.println("The received object is not of type"
                //            + " String!");
                //}
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

