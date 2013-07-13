
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
	
	public Variable getVariable ( String name ) {
		
		Context c = ContextSingleton.getInstance().getContext() ;
		while ( c != null ) {
			for ( int i = 0 ; i < vars.size() ; i++ ) 
				if ( vars.get(i).matchCall(name,c))
					return vars.get(i) ;
			c = c.getFather() ;
		}
		return null ;
	}
	public Vector <Variable> getVariables ( ) {
		return vars ;
	}
	public void removeContext(Context expired) {
		Vector <Variable> exp = new Vector<Variable> () ;
		for (Variable var : vars ) 
			if (var.getContext().equals(expired))
				exp.add(var ) ;
		for (Variable v : exp ) 
			vars.remove(v) ;
	}
	public Variable addVariable(String name, Sort sort, Context context) {
		Variable v = new Variable(name, sort, context ) ;
		vars.add(v) ;
		return v ;
	}


}