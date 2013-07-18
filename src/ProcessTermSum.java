public class ProcessTermSum extends ProcessTerm {
	private Parameter local;
	private ProcessTerm process;

	public ProcessTermSum(String id, Sort sort, ProcessTerm p, Context context) {
		setProcess(p);
		setLocal(new Parameter(id, sort,context));
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

	public String toML() {
		String result = "p_sum ( " ;
		result += local.getType().toML()+"_Var(";
		result += VariableSingleton.getInstance().getParameterVariable(local).toML() ;
		result += ")";
		result += " , " ;
		result += process.toML() ;
		result += ")";
		return result ;
	}

}
