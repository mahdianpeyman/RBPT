import java.util.Vector;


public class InstanceFunction extends Instance {
	private Function func ;
	
	public InstanceFunction(Function func2, Vector<SimpleExpression> ses) {
		setFunc(func2) ;
		setExprs(ses);
	}
	
	public Function getFunc() {
		return func;
	}
	public void setFunc(Function func) {
		this.func = func;
	}
	
}
