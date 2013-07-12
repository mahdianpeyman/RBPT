
import java.util.Vector;


public class ActionSingleton {

	private static ActionSingleton instance = null ;
	private Vector <Action> acts  ;
	private ActionSingleton() {
		acts = new Vector<Action> () ;
	}
	public static ActionSingleton getInstance () {
		if (instance == null ) {
			instance = new ActionSingleton() ;
		
		}
		return instance ;
	}
	public void addAction ( Action s ) {
		acts.add(s) ;
	}
	
	public Vector <Action> getActions ( ) {
		return acts ;
	}
	public Action getAction(String id) {
		for ( Action a : acts )
			if ( a.getId().equals(id))
				return a ;
		return null;
	}
	

}