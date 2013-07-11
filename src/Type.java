

public class Type {
	private Tuple first ;
	private Sort second ;
	public Tuple getFirst () {
		return first ;
	}
	public void setFirst (Tuple t) {
		first = t ;
	}
	
	public Sort getSecond () {
		return second ;
	}
	public void setSecond (Sort s) {
		second = s ;
	}
	
	public String setSecond (String str ) {
		Sort tempS = SortSingleton.getInstance().getSort (str) ;
	      if (tempS == null ) 
	        return ( "not a valid Sort " ) ;
	      else
	           setSecond(tempS) ;
	      return null;
	}
	public Type () {
		first = new Tuple() ;
		
	}
}
