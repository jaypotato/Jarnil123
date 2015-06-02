package datagramobject;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		
		String nama;
		Scanner namaSc = new Scanner(System.in);
		nama = namaSc.toString();
		new Message(nama);
		
		//listen packet datang
		while(true){
		
			//IO interrupt
			//kirim paket
			//....
			
			
			//kalau ada paket datang di simpan ke array list
		}
		
	}

}
