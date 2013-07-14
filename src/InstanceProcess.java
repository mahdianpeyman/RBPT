import java.util.Vector;

public class InstanceProcess extends Instance {

	private Process proc;

	public InstanceProcess(Process proc2, Vector<SimpleExpression> ses) {
		// TODO Auto-generated constructor stub
		setID(proc2.getDeclaration().getId());
		setProc(proc2);
		setExprs(ses);
	}

	public Process getProc() {
		return proc;
	}

	public void setProc(Process proc) {
		this.proc = proc;
	}

	@Override
	public String toString() {
		String result = proc.getDeclaration().getId();
		int num = 0;
		for (SimpleExpression e : exprs) {
			num++;
			if (num == 1)
				result += " ( ";
			else
				result += " , ";
			result += e;
		}
		result += " )";
		return result;
	}

	@Override
	public String toML() {
		return toString();
	}

	@Override
	public Sort getSort() {
		return null;
	}

}
