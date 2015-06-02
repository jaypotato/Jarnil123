package datagramobject;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws UnknownHostException {
		
		ThreadReceive Listen = new ThreadReceive();
		Listen.start();
		
		ThreadSend Send = new ThreadSend();
		Send.start();
				
		ThreadMulticast reSend = new ThreadMulticast();
		reSend.start();
	}

}
