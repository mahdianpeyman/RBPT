
public class Parameter {
	private String id ;
	private Sort type ;
	public Parameter(String id2, Sort sort) {
		id = id2;
		type = sort ;
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Sort getType() {
		return type;
	}
	public void setType(Sort type) {
		this.type = type;
	}

}
