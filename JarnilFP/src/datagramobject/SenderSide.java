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
import java.util.Random;

public class SenderSide extends Thread{
    
	private Thread sendThread;
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    //private static ReceiverSide rcvs =  new ReceiverSide();
    String pesan, penerima, pengirim; int TTL, hop;
    
    SenderSide(String pengirim){
    	this.pengirim = pengirim;
    }
    
    
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        //rcvs.start();
        while (true) {
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            
        	Random rand = new Random(); 
        	int id = rand.nextInt(1111111111);
        	
                System.out.println("Masukkan pesan: ");
                Scanner input_m = new Scanner(System.in);
                //this.pesan = pesan;
                String msg = input_m.nextLine();
                
                System.out.println("Masukkan penerima: ");
                Scanner input_r = new Scanner(System.in);
                String recv = input_r.nextLine();
                //this.penerima = penerima;
                
                //System.out.println("Masukkan pengirim: ");
                //Scanner input_s = new Scanner(System.in);
                //String sender = input_s.nextLine();
                //this.pengirim = pengirim;
                
                System.out.println("Masukkan TTL: ");
                Scanner input_ttl = new Scanner(System.in);
                int ttl = input_ttl.nextInt();
                //this.TTL = TTL;
                
                System.out.println("Masukkan Hop: ");
                Scanner input_hop = new Scanner(System.in);
                int hop = input_hop.nextInt();
                //this.hop = hop;
                                
                ByteArrayOutputStream b_out = new ByteArrayOutputStream(256);
                ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(b_out));
                
                o_out.flush();
                o_out.writeObject(new Message(id,this.pengirim,recv,msg,ttl,hop));
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
