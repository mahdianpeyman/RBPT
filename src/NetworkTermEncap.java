public class NetworkTermEncap extends NetworkTerm {
	private Message message;
	private NetworkTerm networkTerm;

	public NetworkTermEncap(Message m, NetworkTerm term) {
		setMessage(m);
		setNetworkTerm(term);
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

	public String toML() {
		String result = "n_encap(";
		result += message.toML()+"_t";
		result += ",";
		result += networkTerm.toML();
		result += ")";
		return result;
	}

}
