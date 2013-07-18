import java.util.Vector;

public class VariableSingleton {

	private static VariableSingleton instance = null;
	private Vector<Variable> vars;

	private VariableSingleton() {
		vars = new Vector<Variable>();
	}

	public static VariableSingleton getInstance() {
		if (instance == null)
			instance = new VariableSingleton();
		return instance;
	}

	public Variable getVariable(String name) {

		Context c = ContextSingleton.getInstance().getContext();
		while (c != null) {
			for (int i = 0; i < vars.size(); i++)
				if (vars.get(i).matchCall(name, c))
					return vars.get(i);
			c = c.getFather();
		}
		return null;
	}

	public Vector<Variable> getVariables() {
		return vars;
	}

	public void removeContext(Context expired) {
		for (Variable var : vars)
			if (var.getContext().equals(expired))
				var.setValid(false);
	}

	public Variable addVariable(String name, Sort sort, Context context) {
		Variable v = new Variable(name, sort, context);
		vars.add(v);
		return v;
	}

	public Variable getParameterVariable(Parameter local) {
		for (Variable v : vars)
			if (v.getContext().equals(local.getContext())
					&& v.getName().equals(local.getId())
					&& v.getSort().equals(local.getType()))
				return v;
		return null;
	}

}