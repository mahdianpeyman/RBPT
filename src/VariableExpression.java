
public class VariableExpression extends SimpleExpression{
	
	private Variable v ;

	public Variable getV() {
		return v;
	}

	public void setV(Variable v) {
		this.v = v;
	}
	
	
	public VariableExpression(Variable v2) {
		v = v2 ;
		type = 1 ;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		String result = v.getName() ;
		return result ;
	}
}
