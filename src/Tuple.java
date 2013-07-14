import java.util.Vector;

public class Tuple {
	private Vector<Sort> sortList;

	public Vector<Sort> getSortList() {
		return sortList;
	}

	public void addSort(Sort s) {
		sortList.add(s);
	}

	Tuple() {
		sortList = new Vector<Sort>();
	}

	public Tuple(Tuple t) {
		// TODO Auto-generated constructor stub
		sortList = new Vector<Sort>();
		for (Sort s : t.sortList)
			sortList.add(s);
	}

	public String toML() {
		String result = "";
		int num = 0;
		for (Sort s : sortList) {
			num++;
			if (num == 1)
				result += " of ";
			else
				result += " * ";
			result += s.toML();

		}
		return result;
	}
}