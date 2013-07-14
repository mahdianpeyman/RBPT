//import java.util.Vector;

public class Sort {

	private String name;

	Sort(String str) {
		setName(str);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object s) {
		if (s == null)
			return false;
		return name.equals(((Sort) s).getName());
	}

	public String toML() {
		return name;
	}

}