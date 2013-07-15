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

	public String toML() {
		String result = getId()  ;
		int n = 0 ;
		for (Parameter p : getParams() ) {
			n++ ;
			if (n==1) 
				result += " of " ;
			else
				result+=  " * " ;
			result += p.getType().toML() ;
		}
		// TODO Auto-generated method stub
		return result ;
	}

}
