import java.util.Vector;

public class Manager {
	public int a;

	public static void addFunctions(Vector<String> v, Type t) {
		for (int i = 0; i < v.size(); i++) {
			addFunction(v.elementAt(i), t);
		}
	};

	public static String addFunction(String id, Type t) {
		Function f = new Function(id);
		f.setType(t);
		FunctionSingleton.getInstance().addFunction(f);
		return id;
	}

	public static String addSort(String id) {
		Sort s = new Sort(id);
		SortSingleton.getInstance().addSort(s);
		return id;

	}

	public static void createDatatypesSortsFucntions() {
		Vector<Sort> sorts = SortSingleton.getInstance().getSorts();
		Vector<Function> funcs = FunctionSingleton.getInstance().getFunctions();
		for (Sort s : sorts) {
			System.out.print("datatype " + s.getName());
			int numF = 0;
			for (Function f : funcs) {
				Type t = f.getType();
				if (t.getSecond().equals(s)) {
					numF++;
					if (numF == 1)
						System.out.print(" = ");
					else
						System.out.print(" | ");
					System.out.print(f.getName());
					int numS = 0;
					for (Sort typesort : t.getFirst().l) {
						numS++;
						if (numS == 1)
							System.out.print(" of ");
						else
							System.out.print(" * ");
						System.out.print(typesort.getName());
					}

				}

			}
			System.out.println(";");
		}
	}
	
	public static String addMap(String id, Type t) {
		Map m = new Map(id);
		m.setType(t);
		MapSingleton.getInstance().addMap(m);
		return id;
	}

	
	public static String addVariables(Vector<String> v,String s){

	    Sort tSort = SortSingleton.getInstance().getSort (s) ;
	    if (tSort == null ) 
	        return ( "not a valid Sort " ) ;
	     for (String vName : v)
	    	 Manager.addVariable ( vName , tSort);
		return null;
	}

	private static void addVariable(String vName, Sort vSort) {
		Variable v = new Variable ( vName, vSort );
		VariableSingleton.getInstance().addVariable(v);
		
		
	}
}
