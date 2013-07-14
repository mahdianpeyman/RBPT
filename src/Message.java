public class Message {
	private String ID;
	private Tuple params;

	public Message(String id2, Tuple t) {
		// TODO Auto-generated constructor stub
		ID = id2;
		params = new Tuple(t);
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Tuple getParams() {
		return params;
	}

	public void setParams(Tuple params) {
		this.params = params;
	}

	public String toML() {
		String result = getID();
		return result;
	}
}
