import java.util.Vector;


public class InstanceProcess extends Instance {
	
	private Process proc ;

	public InstanceProcess(Process proc2, Vector<SimpleExpression> ses) {
		// TODO Auto-generated constructor stub
		setProc(proc2) ;
		setExprs(ses) ;
	}

	public Process getProc() {
		return proc;
	}

	public void setProc(Process proc) {
		this.proc = proc;
	}
	

}
