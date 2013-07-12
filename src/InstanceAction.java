import java.util.Vector;


public class InstanceAction extends Instance {
	
	private Action act ;

	public InstanceAction(Action act2, Vector<SimpleExpression> ses) {
		// TODO Auto-generated constructor stub
		setAct(act2);
		setExprs(ses);
	}

	public Action getAct() {
		return act;
	}

	public void setAct(Action act) {
		this.act = act;
	}
	

}
