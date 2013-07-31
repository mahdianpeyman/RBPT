import java.util.Vector;

public class FunctionSingleton {

  private static FunctionSingleton instance = null;
	private Vector<Function> funcs;

	private FunctionSingleton() {
		funcs = new Vector<Function>();
	}

	public static FunctionSingleton getInstance() {
		if (instance == null)
			instance = new FunctionSingleton();
		return instance;
	}

	public Function getFunction(String name) {
		for (int i = 0; i < funcs.size(); i++)
			if (funcs.get(i).getName().equals(name))
				return funcs.get(i);
		return null;
	}

	public Vector<Function> getFunctions() {
		return funcs;
	}

	public void addFunction(String id, Type t) {
		Function f = new Function(id);
		f.setType(t);
		funcs.add(f);
		// TODO Auto-generated method stub
	}

	public Vector<Function> getSortFunction(Sort s) {
		Vector<Function> result = new Vector<Function> () ;
		for ( Function f : funcs) 
			if (f.getType().getSecond().equals(s) )
				result .add(f) ;
		return result ;
	}

	public Function getFunctionMessage(String iD) {
		for (Function f : funcs ) 
			if (f.getName().equals(iD) && f.getType().getSecond().equals(SortSingleton.getInstance().getSort("Msg")))
				return f ;
		return null;
	}

	public Function getFunctionAction(String id) {
		for (Function f : funcs ) 
			if (f.getName().equals(id) && f.getType().getSecond().equals(SortSingleton.getInstance().getSort("Action")))
				return f ;
		return null;
	}

	public Function getFunctionLocation(String id) {
		for (Function f : funcs ) 
			if (f.getName().equals(id) && f.getType().getSecond().equals(SortSingleton.getInstance().getSort("Loc")))
				return f ;
		return null ;
	}

}
