
import java.util.Vector;


public class MessageSingleton {

	private static MessageSingleton instance = null ;
	private Vector <Message> msgs  ;
	private MessageSingleton() {
		msgs = new Vector<Message> () ;
	}
	public static MessageSingleton getInstance () {
		if (instance == null ) {
			instance = new MessageSingleton() ;
		}
		return instance ;
	}
	public void addMessage ( Message s ) {
		msgs.add(s) ;
	}
	
	public Vector <Message> getMessages ( ) {
		return msgs ;
	}
	public Message getMessage(String id) {
		for ( Message m : msgs)
			if (m.getID().equals(id))
				return m ;	
		return null;
	}
	

}