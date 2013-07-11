
import java.util.Vector;


public class VariableSingleton {

	private static VariableSingleton instance = null ;
	private Vector <Variable> vars  ;
	private VariableSingleton() {
		vars = new Vector<Variable> () ;
	}
	public static VariableSingleton getInstance () {
		if (instance == null ) {
			instance = new VariableSingleton() ;
		
		}
		return instance ;
	}
	public void addVariable ( Variable s ) {
		vars.add(s) ;
	}
	public Variable getVariable ( String name ) {
		for ( int i = 0 ; i < vars.size() ; i++ ) {
			if ( vars.get(i).getName().equals(name)){
				return vars.get(i) ;
			}
		}
		return null ;
	}
	public Vector <Variable> getVariables ( ) {
		return vars ;
	}


}