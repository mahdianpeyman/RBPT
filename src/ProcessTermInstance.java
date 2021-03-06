public class ProcessTermInstance extends ProcessTerm {

	private Instance instance;

	public ProcessTermInstance(Instance ins) {
		setInstance(ins);
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public String toML() {
		return instance.toML();
	}

}
