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

	public String toML() {
		String result ="p_recvar ( rv ( ";
		result += proc.toML();
		result += " , " ;
		Vector<Parameter> params = getProc().getDeclaration().getParams();
		int i ;
		for (i = 0 ; i < params.size() ; i++ ) {
			if ( i == 0 ) 
				result += " [ ";
			else
				result += " , " ;
			result+= params.get(i) .getType().toML() + "_Var (" + exprs.get(i) .toML()+ ")";
		}
		if ( i == 0 )
			result += " [ ";
		result += " ] )  )";
		return result;
	}

	@Override
	public Sort getSort() {
		return null;
	}

}
