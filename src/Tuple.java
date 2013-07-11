
import java.util.Vector;




public class Tuple {
	public Vector <Sort> l ; 
	public int size () {
		return l.size();
	}
	public void addSort(Sort s) {
		l.add(s);
	}
	Tuple () {
		l = new Vector <Sort> () ; 
	}
	
}