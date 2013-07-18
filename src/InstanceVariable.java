import java.util.Vector;

public class InstanceVariable extends Instance {
	private Variable var;

	public InstanceVariable(Variable var2) {
		setID(var2.getName());
		setVar(var2);
		setExprs(new Vector<SimpleExpression>());
	}

	public Variable getVar() {
		return var;
	}

	public void setVar(Variable var) {
		this.var = var;
	}

	public Sort getSort() {
		return var.getSort();
	}

	public String toML() {
		return var.toML();
	}

}
