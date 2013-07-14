import java.util.Vector;

public class InstanceFunction extends Instance {
	private Function func;

	public InstanceFunction(Function func2, Vector<SimpleExpression> ses) {
		setID(func2.getName());
		setFunc(func2);
		setExprs(ses);
	}

	public Function getFunc() {
		return func;
	}

	public void setFunc(Function func) {
		this.func = func;
	}

	@Override
	public Sort getSort() {
		return func.getType().getSecond();
	}

	@Override
	public String toML() {
		String result = func.getName();
		if (func.getType().getFirst().getSortList().size() > 0) {
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
