public class Location implements ML {
	private String id;

	public Location(String id2) {
		// TODO Auto-generated constructor stub
		id = id2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toML() {
		return FunctionSingleton.getInstance().getFunctionLocation (getId()).toML() ;
	}
}
