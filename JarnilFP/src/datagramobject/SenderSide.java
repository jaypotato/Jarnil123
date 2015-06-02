package datagramobject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SenderSide {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    private static ReceiverSide rcvs =  new ReceiverSide();

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        rcvs.start();
        while (true) {
        try (DatagramSocket serverSocket = new DatagramSocket()) {
                
                System.out.println("Masukkan pesan: ");
                Scanner input_m = new Scanner(System.in);
                String msg = input_m.nextLine();
                
                System.out.println("Masukkan penerima: ");
                Scanner input_r = new Scanner(System.in);
                String recv = input_r.nextLine();
                
                System.out.println("Masukkan pengirim: ");
                Scanner input_s = new Scanner(System.in);
                String sender = input_s.nextLine();
                
                System.out.println("Masukkan TTL: ");
                Scanner input_ttl = new Scanner(System.in);
                int ttl = input_ttl.nextInt();
                
                System.out.println("Masukkan Hop: ");
                Scanner input_hop = new Scanner(System.in);
                int hop = input_hop.nextInt();
                                
                ByteArrayOutputStream b_out = new ByteArrayOutputStream(256);
                ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(b_out));
                
                o_out.flush();
                o_out.writeObject(new Message(sender,recv,msg,ttl,hop));
                o_out.flush();
                
                byte[] b = b_out.toByteArray();
                
                DatagramPacket msgPacket = new DatagramPacket(b,b.length, addr, PORT);
                serverSocket.send(msgPacket);
     
                //System.out.println("User "+ sender + " send message : " + msg);
                o_out.close();
     
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
    }
}
