import java.util.Vector;

public class InstanceLocation extends Instance {
	private Location location;

	public InstanceLocation(Location loc) {
		setID(loc.getId());
		setLocation(loc);
		setExprs(new Vector<SimpleExpression>());
		// TODO Auto-generated constructor stub
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Sort getSort() {
		// TODO Auto-generated method stub
		return SortSingleton.getInstance().getSort("Loc");
	}

	public String toML() {
		return location.toML();
	}

}
