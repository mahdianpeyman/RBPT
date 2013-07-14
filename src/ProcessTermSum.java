
public class ProcessTermSum extends ProcessTerm {
	private Parameter local ;
	private ProcessTerm process ;
	
	

	public ProcessTermSum(String id, Sort sort, ProcessTerm p) {
		// TODO Auto-generated constructor stub
		setProcess(p);
		setLocal(new Parameter(id, sort));
	}
	public Parameter getLocal() {
		return local;
	}
	public void setLocal(Parameter local) {
		this.local = local;
	}
	public ProcessTerm getProcess() {
		return process;
	}
	public void setProcess(ProcessTerm process) {
		this.process = process;
	}
	@Override
	public String toML() {
		return "**PROCESSSUM**";
	}
	

}
