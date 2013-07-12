
public class Action {
	private String id ;
	private Tuple params ;
	public Action(String id2, Tuple t) {
		// TODO Auto-generated constructor stub
		id = id2 ;
		params = t ;
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

}
