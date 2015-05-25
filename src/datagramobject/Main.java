package datagramobject;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		
		String nama;
		Scanner namaSc = new Scanner(System.in);
		nama = namaSc.toString();
		Message Nama = new Message(nama);
	}

}
