package datagramobject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ThreadMulticast extends Thread {

	ArrayList<Message> listMessage = ThreadReceive.listMessage;
	String addrStr = ThreadReceive.INET_ADDR;
	int PORT = ThreadReceive.PORT;

	public ThreadMulticast() throws UnknownHostException {
	}

	InetAddress addr = InetAddress.getByName(addrStr);

	public void run() {
		while (true) {
			if (listMessage.size() == 0)
				System.out
						.println("array masih kosong...\n thread akan sleep..");
			else {
				for (int i = 0; i < listMessage.size(); i++) {
					// TTL habis = drop
					if (((listMessage.get(i).getmsgTime() - System
							.currentTimeMillis()) / 1000) > listMessage.get(i)
							.getTtl())
						listMessage.remove(i);

					// hop habis = drop
					else if (listMessage.get(i).getHop() <= 0)
						listMessage.remove(i);
					
					//terlalu jauh = drop
					else if(listMessage.get(i).getmaxDistance() <=0)
						listMessage.remove(i);
					
					// coba kirim lagi message yang ada
					else {
						try (DatagramSocket serverSocket = new DatagramSocket()) {
							ByteArrayOutputStream b_out = new ByteArrayOutputStream(
									256);
							ObjectOutputStream o_out = new ObjectOutputStream(
									new BufferedOutputStream(b_out));

							o_out.flush();
							o_out.writeObject(listMessage.get(i));
							o_out.flush();

							byte[] b = b_out.toByteArray();

							DatagramPacket msgPacket = new DatagramPacket(b,
									b.length, addr, PORT);
							serverSocket.send(msgPacket);

							o_out.close();
						} catch (Exception e) {
							System.out.println("exception " + e);
						}
					}
				}
			}
			//lakukan re-Sending selama 5detik sekali
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
