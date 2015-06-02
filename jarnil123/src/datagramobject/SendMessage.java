package datagramobject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.UnexpectedException;
import java.util.Random;
import java.util.Scanner;

public class SendMessage {

	String addrStr = ThreadReceive.INET_ADDR;
	int PORT = ThreadReceive.PORT;
	private static String pengirim;
	private static String penerima;

	public SendMessage() throws UnknownHostException {
	}

	public SendMessage(String pengirim, String penerima)
            throws UnknownHostException {
                this.penerima = penerima;
		this.pengirim = pengirim;
	}

	InetAddress addr = InetAddress.getByName(addrStr);

	 void sendingMessage() throws UnknownHostException {

		try (DatagramSocket serverSocket = new DatagramSocket()) {

			Random rand = new Random();
			int id = rand.nextInt(1111111111);

			ByteArrayOutputStream b_out = new ByteArrayOutputStream(256);
			ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(b_out));

			o_out.flush();
			o_out.writeObject(new Message(id, pengirim, penerima,
					"ACK : message sent to " + penerima, 100000, 100000));
			o_out.flush();

			byte[] b = b_out.toByteArray();

			DatagramPacket msgPacket = new DatagramPacket(b, b.length, addr,
					PORT);
			serverSocket.send(msgPacket);

			// System.out.println("User "+ sender + " send message : " +
			// msg);
			o_out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}