public class Action implements ML {
	private String id;
	private Tuple params;

	public Action(String id2, Tuple t) {
		id = id2;
		params = t;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Tuple getParams() {
		return params;
	}

	public void setParams(Tuple params) {
		this.params = params;
	}

	public String toML() {
		// TODO Auto-generated method stub
		return FunctionSingleton.getInstance().getFunctionAction(getId()).toML();
	}

}
