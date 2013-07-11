import java.util.Vector;

public class Tuple {
	public Vector<Sort> l;

	public int size() {
		return l.size();
	}

	public void addSort(Sort s) {
		l.add(s);
	}

	public String addSort(String str) {
		Sort tempS = SortSingleton.getInstance().getSort(str);
		if (tempS == null)
			return ("not a valid Sort ");
		addSort(tempS);
		return null  ;

	}

	Tuple() {
		l = new Vector<Sort>();
	}

}