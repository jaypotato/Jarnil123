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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverSide extends Thread {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    byte[] buf = new byte[256];
    static MulticastSocket clientSocket;
    
        public void run() {
                
                InetAddress address;
                try {
                    address = InetAddress.getByName(INET_ADDR);
                    clientSocket = new MulticastSocket(PORT);
                    clientSocket.joinGroup(address);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ReceiverSide.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ReceiverSide.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                while (true){
                        DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                        try {
                            clientSocket.receive(msgPacket);
                        } catch (IOException ex) {
                            Logger.getLogger(ReceiverSide.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                        ObjectInputStream ois;
                            try {
                                ois = new ObjectInputStream(new BufferedInputStream(bais));
                                Object readObj = ois.readObject();

                                Message message = (Message) readObj;
                                System.out.println("Message is: " + message.getMessage() + ", ini pesan dari " + message.getSender());
                                ois.close();
                            } catch (IOException ex) {
                                Logger.getLogger(ReceiverSide.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ReceiverSide.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
        }
}

