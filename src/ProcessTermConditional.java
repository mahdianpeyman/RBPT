public class ProcessTermConditional extends ProcessTerm {

	private SimpleExpression cond;
	private ProcessTerm left;
	private ProcessTerm right;

	ProcessTermConditional(SimpleExpression e, ProcessTerm l, ProcessTerm r) {
		setCond(e);
		setLeft(l);
		setRight(r);
	}

	public SimpleExpression getCond() {
		return cond;
	}

	public void setCond(SimpleExpression cond) {
		this.cond = cond;
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

	@Override
	public String toML() {
		return "**PROCESSCOND**";
	}
}
