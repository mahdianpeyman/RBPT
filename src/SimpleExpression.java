import java.util.Vector;

public abstract class SimpleExpression  implements ML {

	private String ID;
	protected Vector<SimpleExpression> exprs;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Vector<SimpleExpression> getExprs() {
		return exprs;
	}

	public void setExprs(Vector<SimpleExpression> exprs) {
		this.exprs = new Vector<SimpleExpression>(exprs);
	}

	protected void setExprs(final Instance inst) {
		this.exprs = new Vector<SimpleExpression>();
		this.exprs.add(inst);
	}

	public void addExpr(SimpleExpression se) {
		exprs.add(se);
	}

	public boolean isRelated(Map m) {
		Map map = MapSingleton.getInstance().getMap(ID);
		return m.equals(map);
	}

	public abstract Sort getSort();

}
