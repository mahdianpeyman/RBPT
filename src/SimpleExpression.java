import java.util.Vector;


public abstract class SimpleExpression {
	
	private String ID ;
	protected Vector <SimpleExpression> exprs ;
	public SimpleExpression(){
		setExprs(new Vector <SimpleExpression> ()) ;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Vector <SimpleExpression> getExprs() {
		return exprs;
	}

	public void setExprs(Vector <SimpleExpression> exprs) {
		this.exprs = exprs;
	}
	public void addExpr ( SimpleExpression se) {
		exprs . add (se) ;
	}
	public boolean isRelated(Map m) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public abstract String toString () ;

	public abstract Sort getSort() ;
	
	
	
}
