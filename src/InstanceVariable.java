import java.util.Vector;


public class InstanceVariable extends Instance {
	private Variable var ;

	public InstanceVariable(Variable var2) {
		setID (var2.getName()) ;
		setVar(var2) ;
		setExprs(new Vector<SimpleExpression>()) ;
	}

	public Variable getVar() {
		return var;
	}

	public void setVar(Variable var) {
		this.var = var;
	}

	@Override
	public String toString() {
		return var.getName();
	}

	@Override
	public Sort getSort() {
		return var.getSort();
	}
	
	@Override
	public String toML() {
		return toString();
	}

	

}
