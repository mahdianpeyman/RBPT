import java.util.Vector;

public class MapSingleton {

  private static MapSingleton instance = null;
	private Vector<Map> maps;

	private MapSingleton() {
		maps = new Vector<Map>();
	}

	public static MapSingleton getInstance() {
		if (instance == null)
			instance = new MapSingleton();
		return instance;
	}

	public void addMap(Map m) {
		maps.add(m);
	}

	public Map getMap(String name) {
		for (int i = 0; i < maps.size(); i++)
			if (maps.get(i).getName().equals(name))
				return maps.get(i);
		return null;
	}

	public Vector<Map> getMaps() {
		return maps;
	}

}
