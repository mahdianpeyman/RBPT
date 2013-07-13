
public class SimpleExpressionVariable extends SimpleExpression{
	
	private Variable v ;

	public Variable getV() {
		return v;
	}

	public void setV(Variable v) {
		this.v = v;
	}
	
	
	public SimpleExpressionVariable(Variable v2) {
		v = v2 ;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		String result = v.getName() ;
		return result ;
	}

	@Override
	public Sort getSort() {
		return v.getSort();
	}
}
