
public class ProcessTermChoice extends ProcessTerm {
	private ProcessTerm left ;
	private ProcessTerm right ;
	
	ProcessTermChoice (ProcessTerm l, ProcessTerm r) {
		left = l ;
		right = r ;
	}
	public ProcessTerm getLeft() {
		return left;
	}
	public void setLeft(ProcessTerm left) {
		this.left = left;
	}
	public ProcessTerm getRight() {
		return right;
	}
	public void setRight(ProcessTerm right) {
		this.right = right;
	}
	

}