import java.util.Vector;

public class ProcessDeclaration {
	private String id;
	private Vector<Parameter> params;

	ProcessDeclaration(String str) {
		id = str;
		params = new Vector<Parameter>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vector<Parameter> getParams() {
		return params;
	}

	public void setParams(Vector<Parameter> params) {
		this.params = params;
	}

	public void addParam(String id, Sort sort) {
		Parameter p = new Parameter(id, sort);
		params.add(p);

	}

	public Object getName() {
		return id;
	}

}
