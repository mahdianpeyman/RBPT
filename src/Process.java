public class Process  implements ML{
	private ProcessDeclaration declaration;
	private ProcessTerm term;
	private String ID ;

	public Process(ProcessDeclaration declaration2, ProcessTerm term2) {
		setDeclaration(declaration2);
		setTerm(term2);
		setID(declaration2.getId()) ;
	}

	public Process(ProcessDeclaration declaration2) {
		setDeclaration(declaration2);
		setID(declaration2.getId()) ;
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


	public String toML() {
		return getID()+"_RecName" ;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	

}
