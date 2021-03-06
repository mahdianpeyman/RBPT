public class NetworkTermDeploy extends NetworkTerm {
	private Location loc;
	private ProcessTerm process;

	public NetworkTermDeploy(Location loc2, ProcessTerm process2) {
		setLoc(loc2);
		setProcess(process2);
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public ProcessTerm getProcess() {
		return process;
	}

	public void setProcess(ProcessTerm process) {
		this.process = process;
	}

	public String toML() {
		String result = "n_deploy(";
		result += loc.toML();
		result += ",";
		result += process.toML();
		result += ")";
		return result;
	}

}
