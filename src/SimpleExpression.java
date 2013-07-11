import java.util.Vector;


public class SimpleExpression {
	
	private String ID ;
	private Vector <SimpleExpression> exprs ;

	public SimpleExpression(){
		setExprs(new Vector <SimpleExpression> ()) ;
	}
	public SimpleExpression(SimpleExpression s){
		
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
	
	
}
