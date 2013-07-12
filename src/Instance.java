import java.util.Vector;


public abstract class Instance {
	private Vector<SimpleExpression> exprs;

	public Vector<SimpleExpression> getExprs() {
		return exprs;
	}
	public void setExprs(Vector<SimpleExpression> ses) {
		exprs = new Vector<SimpleExpression> () ;
		for (SimpleExpression s : ses ) 
			exprs.add(s);
	}
}
