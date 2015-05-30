package datagramobject;
import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String message;
	String receiver;
	String sender;
	int ttl;
	int hop;
	
	int ctr;
	String id_msg;
	
	//public Message(String nama){
		//this.sender = nama;
	//}
	
	public Message(String message, String receiver, String sender
			,int ttl, int hop){
		setMessage(message);
		setReceiver(receiver);
		setSender(sender);
		setTtl(ttl);
		//setCtr(ctr);
		setHop(hop);
		setId_msg();
	}

	public void setId_msg(){
		this.id_msg = this.sender + this.ctr;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public int getHop() {
		return hop;
	}

	public void setHop(int hop) {
		this.hop = hop;
	}

	public int getJarak() {
		return jarak;
	}

	public void setJarak(int jarak) {
		this.jarak = jarak;
	}

	public int getCtr() {
		return ctr;
	}

	public void setCtr(int ctr) {
		this.ctr = ctr;
	}


}
