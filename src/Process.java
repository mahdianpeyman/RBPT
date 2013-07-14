public class Process {
	private ProcessDeclaration declaration;
	private ProcessTerm term;

	public Process(ProcessDeclaration declaration2, ProcessTerm term2) {
		setDeclaration(declaration2);
		setTerm(term2);
	}

	public Process(ProcessDeclaration declaration2) {
		setDeclaration(declaration2);
	}

	public ProcessDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(ProcessDeclaration declaration) {
		this.declaration = declaration;
	}

	public ProcessTerm getTerm() {
		return term;
	}

	public void setTerm(ProcessTerm term) {
		this.term = term;
	}

	public Object getName() {
		return declaration.getName();
	}

}
