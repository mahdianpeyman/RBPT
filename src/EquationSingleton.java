
import java.util.Vector;


public class EquationSingleton {

	private static EquationSingleton instance = null ;
	private Vector <Equation> eqns  ;
	private EquationSingleton() {
		eqns = new Vector<Equation> () ;
	}
	public static EquationSingleton getInstance () {
		if (instance == null ) {
			instance = new EquationSingleton() ;
		
		}
		return instance ;
	}
	public void addEquation ( Equation s ) {
		eqns.add(s) ;
	}
	
	public Vector <Equation> getEquations ( ) {
		return eqns ;
	}


}