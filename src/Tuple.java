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

	public Tuple(Tuple t) {
		// TODO Auto-generated constructor stub
		l = new Vector<Sort>() ;
		for (Sort s : t.l)
			l.add(s) ;
	}
	@Override
	public String toString () {
		String result = "" ;
		int num = 0 ;
		for (Sort s : l ) {
			num ++ ;
			if ( num == 1 ) 
				result += " of ";
			else
				result += " * " ;
			result += s.toString() ;
			
		}
		return result ;
		
	}

}