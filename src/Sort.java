
//import java.util.Vector;


public class Sort {

	private String name ;
	//public Vector <Function> funcs ; 


	Sort ( String str ) {
		setName(str) ;
		//funcs = new Vector <Function> () ;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	@Override 
	public boolean equals (Object s) {
		if ( s == null ) 
			return false ;
		return name .equals (((Sort)s).getName());
	}



}