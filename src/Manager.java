import java.awt.print.Printable;
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
			out("datatype " + s.getName());
			int numF = 0;
			for (Function f : funcs) {
				Type t = f.getType();
				if (t.getSecond().equals(s)) {
					numF++;
					if (numF == 1)
						out(" = ");
					else
						out(" | ");
					out(f.getName());
					int numS = 0;
					for (Sort typesort : t.getFirst().l) {
						numS++;
						if (numS == 1)
							out(" of ");
						else
							out(" * ");
						out(typesort.getName());
					}

				}

			}
			outln(";");
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

	public static void addVariable(String vName, Sort vSort) {
		Variable v = new Variable ( vName, vSort );
		VariableSingleton.getInstance().addVariable(v);
		
		
	}
	
	public static SimpleExpression setSimpleExpression (String id ){
		Variable v = VariableSingleton.getInstance().getVariable(id);
		Function f = FunctionSingleton.getInstance().getFunction(id);
		Map m = MapSingleton.getInstance().getMap(id) ;
		if ( v != null )
			return new VariableExpression(v);
		if ( f != null )
			return new FunctionCallExpression(f);
		if ( m != null )
			return new MapCallExpression(m);
		return null;
	}
	public static String simpleExpressionError(String id){
		return  id +" in simpleExpression is not a variable/function/map ! " ;
	}
	
	public static void addEquation (SimpleExpression left, SimpleExpression right) {
		Equation e = new Equation(left,right) ;
		EquationSingleton.getInstance().addEquation(e) ;	
	}
	public static void createMLFuncEQN() {
		Vector<Map> maps = MapSingleton.getInstance().getMaps() ;
		for (Map m : maps) {
			Vector<Equation>eqns = EquationSingleton.getInstance().getRelatedEquation(m) ; 
			outln () ;
			outln("# equations of " + m.getName()) ;
			int numE = 0 ;
			for (Equation e : eqns ) {
				numE ++ ;
				if ( numE == 1 ) 
					out ("fun ");
				else
					out ( " | ");
				printMLEqn ( e ) ;
				
				outln();
			}
			outln(" ; ");
		}
	}

	private static void printMLEqn(Equation e) {
		printMLSimpleExpression (e.getLeft()) ;
		out ( " = ") ;
		printMLSimpleExpression (e.getRight()) ;
		
	}

	private static void printMLSimpleExpression(SimpleExpression se) {
		out(se.toString());
		
	}

	private static void out(String string) {
		System.out.print(string) ;		
	}
	private static void outln(String string) {
		System.out.println(string) ;		
	}
	private static void outln() {
		System.out.println() ;		
	}	
	
	public static void createVars() {
		/*Vector<Variable> vars = VariableSingleton.getInstance().getVariables();
		for (Variable v : vars)
			outln(" # " + v.getName()+ " ");
			*/
	}
	public static void addMessages (Vector<String> ids,Tuple t) {
		for (String id:ids) {
			Message m = new Message (id,t) ;
			MessageSingleton.getInstance().addMessage(m);
		}
	}
	
	public static void createMsgSort_Msg() {
		Vector <Message> msgs = MessageSingleton.getInstance().getMessages() ;
		out ("datatype Msg ");	
		int numM = 0 ;
		for (Message m : msgs ) {
			numM++ ;
			if ( numM == 1 ) 
				out ( " = " );
			else
				out ( " | " );
			out (m.getID()) ;
			outln (m.getParams().toString());
		}
	
	}
	
	
}
