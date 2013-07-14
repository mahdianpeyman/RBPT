
import java.util.Vector;


public class SortSingleton {

	private static SortSingleton instance = null ;
	private Vector <Sort> sorts  ;
	private SortSingleton() {
		sorts = new Vector<Sort> () ;
		sorts. add ( new Sort ( "Loc")) ;
		sorts. add ( new Sort ( "Msg")) ;
		sorts. add( new Sort ("Action")) ;
	}
	public static SortSingleton getInstance () {
		if (instance == null ) {
			instance = new SortSingleton() ;
		
		}
		return instance ;
	}
	public void addSort ( Sort s ) {
		sorts.add(s) ;
	}
	public Sort getSort ( String name ) {
		for ( int i = 0 ; i < sorts.size() ; i++ ) {
			if ( sorts.get(i).getName().equals(name)){
				return sorts.get(i) ;
			}
		}
		return null ;
	}
	public Vector <Sort> getSorts ( ) {
		return sorts ;
	}


}