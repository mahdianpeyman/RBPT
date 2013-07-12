import java.util.Vector;


public class InstanceVariable extends Instance {
	private Variable var ;

	public InstanceVariable(Variable var2, Vector<SimpleExpression> ses) {
		setVar(var2) ;
		setExprs(ses) ;
	}

	public Variable getVar() {
		return var;
	}

	public void setVar(Variable var) {
		this.var = var;
	}
	

}
