public class NetworkTermAbs extends NetworkTerm {
	private Message message;
	private NetworkTerm networkTerm;

	public NetworkTermAbs(Message m2, NetworkTerm term) {
		setMessage(m2);
		setNetworkTerm(term);
	}

	public NetworkTerm getNetworkTerm() {
		return networkTerm;
	}

	public void setNetworkTerm(NetworkTerm networkTerm) {
		this.networkTerm = networkTerm;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String toML() {
		String result = "n_abs (";
		result += message.toML()+"_t";
		result += ",";
		result += result = networkTerm.toML();
		result += ")";
		return result;
	}

}
