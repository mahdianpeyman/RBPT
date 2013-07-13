
public class NetworkTermEncap extends NetworkTerm {
	private Message message ;
	private NetworkTerm networkTerm;
	public NetworkTermEncap(Message m, NetworkTerm term) {
		// TODO Auto-generated constructor stub
		setMessage(m) ;
		setNetworkTerm(term) ;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public NetworkTerm getNetworkTerm() {
		return networkTerm;
	}
	public void setNetworkTerm(NetworkTerm networkTerm) {
		this.networkTerm = networkTerm;
	}
	

}
