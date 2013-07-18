import java.util.Vector;

public class InstanceAction extends Instance {

	private Action act;

	public InstanceAction(Action act2, Instance inst) {
		// TODO Auto-generated constructor stub
		setID(act2.getId());
		setAct(act2);
		setExprs(inst);
	}

	public InstanceAction(Action act2, Vector<SimpleExpression> args) {
		setAct(act2);
		setExprs(args);
	}

	public Action getAct() {
		return act;
	}

	public void setAct(Action act) {
		this.act = act;
	}

	@Override
	public Sort getSort() {
		return SortSingleton.getInstance().getSort("Action");
	}

	public String toML() {
		String result = act.toML();
		if (act.getParams().getSortList().size() > 0) {
			int num = 0;
			for (SimpleExpression e : exprs) {
				num++;
				if (num == 1)
					result += " ( ";
				else
					result += " , ";

				result += e.toML();
			}
			result += " )";

		}
		return result;
	}

}
