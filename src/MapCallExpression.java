public class MapCallExpression extends SimpleExpression {
	private Map map;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public MapCallExpression() {

	}

	public MapCallExpression(Map m) {
		map = m;
	}

}
