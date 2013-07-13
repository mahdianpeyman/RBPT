

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
	        return ( "Error" + str + "not a valid Sort " ) ;
	      else
	           setSecond(tempS) ;
	      return null;
	}
	public Type () {
		first = new Tuple() ;
		
	}
	public Type(Sort sort) {
		first = new Tuple() ;
		second = sort;
		// TODO Auto-generated constructor stub
	}
}
