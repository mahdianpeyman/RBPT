public class ProcessTermAction extends ProcessTerm {
	private Instance actionInstance;
	private ProcessTerm next;

	ProcessTermAction(Instance au, ProcessTerm pt) {
		setNext(pt);
		setActionInstance(au);
	}

	public ProcessTerm getNext() {
		return next;
	}

	public void setNext(ProcessTerm next) {
		this.next = next;
	}

	@Override
	public String toML() {
		String result = "p_prefix(";
		result += actionInstance.toML();
		result += ",";
		result += next.toML();
		result += ")";
		return result;
	}

	public Instance getActionInstance() {
		return actionInstance;
	}

	public void setActionInstance(Instance actionInstance) {
		this.actionInstance = actionInstance;
	}

}
