
import java.util.Vector;


public class ActionSingleton {

	private static ActionSingleton instance = null ;
	private Vector <Action> acts  ;
	private ActionSingleton() {
		acts = new Vector<Action> () ;
		Sort msg = SortSingleton.getInstance().getSort ("Msg") ;
		Tuple t = new Tuple () ;
		t.addSort(msg) ;
		Action snd = new Action("snd",t) ;
		Action rcv = new Action("rcv",t) ;
		acts.add(snd) ;
		acts.add(rcv) ;
		Sort action = SortSingleton.getInstance().getSort("Action");
		Type type = new Type(action);
		type.setFirst(t);
		FunctionSingleton.getInstance().addFunction("snd", type);
		FunctionSingleton.getInstance().addFunction("rcv", type);
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