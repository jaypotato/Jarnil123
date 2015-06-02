package datagramobject;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		
		String nama;
		Scanner namaPengirim = new Scanner(System.in);
		nama = namaPengirim.toString();
		
		
		ReceiverSide RS = new ReceiverSide();
		RS.start();
		
		SenderSide SS = new SenderSide(nama);
		SS.start();
				
	}

}
