public class Function implements ML{
	private String name;
	private Type type;

	public Function(String str) {
		setName(str);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String toML() {
		return getName()+"_"+getType().getSecond().toML();
	}

}
