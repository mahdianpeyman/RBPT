
public class ProcessTermAction extends ProcessTerm {
	private ActionUse actUse ;
	private ProcessTerm next ;
	ProcessTermAction (ActionUse au, ProcessTerm pt) {
		setActUse(au) ;
		setNext(pt) ;
	}
	public ActionUse getActUse() {
		return actUse;
	}
	public void setActUse(ActionUse actUse) {
		this.actUse = actUse;
	}
	public ProcessTerm getNext() {
		return next;
	}
	public void setNext(ProcessTerm next) {
		this.next = next;
	}
	
	
}
