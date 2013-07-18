import java.util.Vector;

public class ProcessDeclaration implements ML {
	private String id;
	private Vector<Parameter> params;
	private Context context;

	public ProcessDeclaration(String id2, Context father) {
		setId(id2);
		setParams(new Vector<Parameter>());
		setContext(father);
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

	public String toML() {

		String result = "rv ( ";
		result += getId() + "_RecName";
		result += " , " ;
		result += ("  ");

		int nParam = 0;
		for (Parameter param : params) {
			nParam++;
			if (nParam == 1)
				result += (" [ ");
			else
				result += (" , ");
			Variable v = VariableSingleton.getInstance().getParameterVariable(
					param);
			result += v.getSort().toML() + "_Var (";
			result += v.toML();
			result += " ) ";
		}
		if (nParam == 0)
			result += " [ ";
		result += " ] )";
		return result;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void addParam(Parameter p) {
		params.add(p);

	}

}
