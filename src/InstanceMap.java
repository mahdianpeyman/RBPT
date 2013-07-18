import java.util.Vector;

public class InstanceMap extends Instance {

	private Map map;

	public InstanceMap(Map map2, Vector<SimpleExpression> ses) {
		setID(map2.getName());
		setExprs(ses);
		setMap(map2);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public Sort getSort() {
		return map.getType().getSecond();
	}

	public String toML() {
		String result = map.toML();
		int num = 0;
		for (SimpleExpression e : exprs) {
			num++;
			if (num == 1)
				result += " ( ";
			else
				result += " , ";
			result += e.toML();
		}
		result += " )";
		return result;
	}

}
