import java.util.Vector;


public class SortSingleton {
	
	private static SortSingleton instance = null ;
	private static Vector <Sort> sorts  ;
	private SortSingleton() {
		
	}
	public static SortSingleton getInstance () {
		if (instance == null ) {
			instance = new SortSingleton() ;
		sorts = new Vector<Sort> () ;
		sorts. add ( new Sort ( "Loc")) ;
		}
		return instance ;
	}
	public void addSort ( Sort s ) {
		sorts.add(s) ;
	}
	public Sort getSort ( String name ) {
		for ( int i = 0 ; i < sorts.size() ; i++ ) {
			if ( sorts.get(i).name.equals(name)){
				return sorts.get(i) ;
			}
		}
		return null ;
	}
	

}
