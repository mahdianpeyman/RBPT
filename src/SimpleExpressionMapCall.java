public class SimpleExpressionMapCall extends SimpleExpression {
	private Map map;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}


	public SimpleExpressionMapCall(Map m) {
		map = m;
	}

	@Override
	public boolean isRelated(Map m) {
		return (map.equals(m));
	}

	@Override
	public String toString() {
		String result = map.getName() ;
		int num = 0 ;
		for (SimpleExpression e : exprs){
			num ++ ;
			if ( num == 1 ) 
				result += " ( ";
			else 
				result += " , ";
			result +=  e;
		}
		result += " )";
		return result;	}

	@Override
	public Sort getSort() {
		return map.getType().getSecond();
	}

}
