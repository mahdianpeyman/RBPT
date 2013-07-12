import java.util.Vector;

public class InstanceMap extends Instance {

	private Map map;

	public InstanceMap(Map map2, Vector<SimpleExpression> ses) {
		setExprs(ses);
		setMap(map2);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}
