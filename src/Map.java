public class Map implements ML {
  private String name;
	private Type type;

	public Map(String str) {
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

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		return name.equals(((Map) o).getName());
	}

	public String toML() {
		return getName()+"_map";
	}
}
