package datagramobject;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

	public static void main(String[] args) {
		
            try {
                String nama;
                Scanner namaPengirim = new Scanner(System.in);
                nama = namaPengirim.toString();
                
                
                ReceiverSide RS = new ReceiverSide();
                RS.start();
                
                SenderSide SS = new SenderSide(nama);
                SS.start();
            } catch (UnknownHostException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
				
	}

}
