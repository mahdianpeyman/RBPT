
public class VariableExpression extends SimpleExpression{
	
	private Variable v ;

	public Variable getV() {
		return v;
	}

	public void setV(Variable v) {
		this.v = v;
	}
	
	public VariableExpression () {
		
	}

	public VariableExpression(Variable v2) {
		v = v2 ;
		// TODO Auto-generated constructor stub
	}
}
