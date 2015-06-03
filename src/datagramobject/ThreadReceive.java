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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadReceive extends Thread {

	final static String INET_ADDR = "224.0.0.3";
	final static int PORT = 8888;
	byte[] buf = new byte[256];
	static MulticastSocket clientSocket;
	static ArrayList<Message> listMessage = new ArrayList<Message>();
	int jarakTempuh = ThreadSend.getLokasi();

	public void run() {
		System.out.println("membuat thread receive");
		InetAddress address;
		try {
			address = InetAddress.getByName(INET_ADDR);
			clientSocket = new MulticastSocket(PORT);
			clientSocket.joinGroup(address);
		} catch (UnknownHostException ex) {
			Logger.getLogger(ThreadReceive.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ThreadReceive.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		while (true) {
			DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
			try {
				clientSocket.receive(msgPacket);
			} catch (IOException ex) {
				Logger.getLogger(ThreadReceive.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new BufferedInputStream(bais));
				Object readObj = ois.readObject();

				Message message = (Message) readObj;
				int alert = 0;
				for (int i = 0; i < listMessage.size(); i++) {
					if (listMessage.get(i).getId() == message.getId()
							&& listMessage.get(i).getSender()
									.equals(message.getSender())) {
						alert = 1;
						break;
					}
				}
				// pesan belum ada di array
				if (alert != 1) {
					// pesan dari diri sendiri
					/*
					 * if (message.getSender().equals(ThreadSend.getPengirim()))
					 * { System.out
					 * .println("--Dropping Message--\n--Message diri sendiri--\n"
					 * + "Id : " + message.getId() + "\nMessage : " +
					 * message.getMessage() + "\npengirim : " +
					 * message.getSender() + "\npenerima :" +
					 * message.getReceiver() + "\nhopnya : " + message.getHop()
					 * + "\nTTL : " + message.getTtl() + "\n"); }
					 */

					// pesannya untuk dia
					if (message.getReceiver().equals(ThreadSend.getPengirim())) {
						System.out.println("--Received Message--\n" + "Id : "
								+ message.getId() + "\nMessage : "
								+ message.getMessage() + "\npengirim : "
								+ message.getSender() + "\npenerima :"
								+ message.getReceiver() + "\nhopnya : "
								+ message.getHop() + "\nTTL : "
								+ message.getTtl() + "\n");
						// code untuk handling ACK
						// kirim message ke sender asal
						SendMessage ackSend = new SendMessage(
								message.getReceiver(), message.getSender());
						ackSend.sendingMessage();
					}

					// ttl abis
					else if (((message.getmsgTime() - System
							.currentTimeMillis()) / 1000) > message.getTtl()) {
						System.out
								.println("--Dropping Message--\n--TTL abis--\n"
										+ "Id : " + message.getId()
										+ "\nMessage : " + message.getMessage()
										+ "\npengirim : " + message.getSender()
										+ "\npenerima :"
										+ message.getReceiver() + "\nhopnya : "
										+ message.getHop() + "\nTTL : "
										+ message.getTtl() + "\n");
					}
					// hop habis
					else if (message.getHop() <= 0) {
						System.out
								.println("--Dropping Message--\n--hop abis--\n"
										+ "Id : " + message.getId()
										+ "\nMessage : " + message.getMessage()
										+ "\npengirim : " + message.getSender()
										+ "\npenerima :"
										+ message.getReceiver() + "\nhopnya : "
										+ message.getHop() + "\nTTL : "
										+ message.getTtl() + "\n");
					}
					// jarak terlalu jauh = drop
					else if (message.getmaxDistance() <= 0) {
						System.out
								.println("--Dropping Message--\n--jarak terlalu jauh--\n"
										+ "Id : " + message.getId()
										+ "\nMessage : " + message.getMessage()
										+ "\npengirim : " + message.getSender()
										+ "\npenerima :"
										+ message.getReceiver() + "\nhopnya : "
										+ message.getHop() + "\nTTL : "
										+ message.getTtl() + "\n");
					}

					// pesannya bukan untuk dia
					// simpan ke array message
					else {
						System.out.println("--Saving Message--\n" + "Id : "
								+ message.getId() + "\nMessage : "
								+ message.getMessage() + "\npengirim : "
								+ message.getSender() + "\npenerima :"
								+ message.getReceiver() + "\nhopnya : "
								+ message.getHop() + "\nTTL : "
								+ message.getTtl() + "\n");
						// kurangi hop - 1
						message.setHop(message.getHop() - 1);
						// kurangi distance sesuai jarak yang ditempuh
						message.setmaxDistance(message.getmaxDistance()
								- jarakTempuh);
						listMessage.add(message);
					}
				}
				// pesan sudah ada di array
				else {
					System.out
							.println("--Dropping Message--\n--Message Exists--\n"
									+ "Id : "
									+ message.getId()
									+ "\nMessage : "
									+ message.getMessage()
									+ "\npengirim : "
									+ message.getSender()
									+ "\npenerima :"
									+ message.getReceiver()
									+ "\nhopnya : "
									+ message.getHop()
									+ "\nTTL : " + message.getTtl());
				}
				ois.close();
			} catch (IOException ex) {
				Logger.getLogger(ThreadReceive.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ThreadReceive.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}
}
