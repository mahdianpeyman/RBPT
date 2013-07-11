public class FunctionCallExpression extends SimpleExpression {
	private Function func;

	public Function getFunc() {
		return func;
	}

	public void setFunc(Function func) {
		this.func = func;
	}

	

	public FunctionCallExpression(Function f) {
		type = 2 ;
		func = f;
	}

	
	@Override
	public String toString() {
		String result = func.getName();
		if (func.getType().getFirst().l.size() > 0) {
			int num = 0 ; 
			for (SimpleExpression e : exprs){
				num++ ;
				if ( num == 1 ) 
					result += " ( " ;
				else
					result += " , " ;
				
				result += e;
			}
			result += " )";
			
		}
		return result;
	}
}
