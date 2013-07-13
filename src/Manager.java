import java.util.Vector;

public class Manager {
	public int a;

	public static void addFunctions(Vector<String> v, Type t) {
		for (int i = 0; i < v.size(); i++) {
			addFunction(v.elementAt(i), t);
		}
	};

	public static String addFunction(String id, Type t) {
	
		FunctionSingleton.getInstance().addFunction(id,t);
		return id;
	}

	public static String addSort(String id) {
		Sort s = new Sort(id);
		SortSingleton.getInstance().addSort(s);
		return id;

	}

	public static void createDatatypesSortsFunctions() {
		Vector<Sort> sorts = SortSingleton.getInstance().getSorts();
		Vector<Function> funcs = FunctionSingleton.getInstance().getFunctions();
		outln();
		outln("# All datatypes are created from sorts and funcs");
		for (Sort s : sorts) {
			if (s.getName().equals("Loc"))
				continue;
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

	public static String addVariables(Vector<String> v, String s) {

		Sort tSort = SortSingleton.getInstance().getSort(s);
		if (tSort == null)
			return (" Error : " + s + " is not a valid Sort ");
		for (String vName : v)
			Manager.addVariable(vName, tSort);
		return null;
	}

	public static Variable addVariable(String vName, Sort vSort) {
		return VariableSingleton.getInstance().addVariable(vName, vSort,
				ContextSingleton.getInstance().getContext());
	}

	public static SimpleExpression setSimpleExpression(String id) {
		Variable v = VariableSingleton.getInstance().getVariable(id);
		Function f = FunctionSingleton.getInstance().getFunction(id);
		Map m = MapSingleton.getInstance().getMap(id);
		if (v != null)
			return new VariableExpression(v);
		if (f != null)
			return new FunctionCallExpression(f);
		if (m != null)
			return new MapCallExpression(m);
		return null;
	}

	public static String simpleExpressionError(String id) {
		return id + " in simpleExpression is not a variable/function/map ! ";
	}

	public static void addEquation(SimpleExpression left, SimpleExpression right) {
		Equation e = new Equation(left, right);
		EquationSingleton.getInstance().addEquation(e);
	}

	public static void createMLFuncEQN() {
		Vector<Map> maps = MapSingleton.getInstance().getMaps();
		for (Map m : maps) {
			Vector<Equation> eqns = EquationSingleton.getInstance()
					.getRelatedEquation(m);
			outln();
			outln("# equations of " + m.getName());
			int numE = 0;
			for (Equation e : eqns) {
				numE++;
				if (numE == 1)
					out("fun ");
				else
					out(" | ");
				printMLEqn(e);

				outln();
			}
			outln(" ; ");
		}
	}

	private static void printMLEqn(Equation e) {
		printMLSimpleExpression(e.getLeft());
		out(" = ");
		printMLSimpleExpression(e.getRight());

	}

	private static void printMLSimpleExpression(SimpleExpression se) {
		out(se.toString());

	}

	
	public static void addMessages(Vector<String> ids, Tuple t) {
		for (String id : ids) {
			Message m = new Message(id, t);
			MessageSingleton.getInstance().addMessage(m);
		}
	}

	public static void createMsgSort_Msg() {
		Vector<Message> msgs = MessageSingleton.getInstance().getMessages();
		outln();
		outln("# Msg created from msgs rule");
		out("datatype Msg ");
		int numM = 0;
		for (Message m : msgs) {
			numM++;
			if (numM == 1)
				out(" = ");
			else
				out(" | ");
			out(m.getID());
			outln(m.getParams().toString());
		}
		outln(" ; ");

	}

	public static void addActions(Vector<String> ids, Tuple t) {
		for (String id : ids) {
			Action a = new Action(id, t);
			ActionSingleton.getInstance().addAction(a);
		}
	}

	public static void createActionSortAction() {
		Vector<Action> acts = ActionSingleton.getInstance().getActions();
		outln();
		outln("# Action created from acts rule");
		out("datatype Action ");
		int num = 0;
		for (Action a : acts) {
			num++;
			if (num == 1)
				out(" = ");
			else
				out(" | ");
			out(a.getId());
			outln(a.getParams().toString());
		}
		outln(" ; ");
	}

	public static void addLocations(Vector<String> ids) {
		for (String id : ids) {
			LocationSingleton.getInstance().addLocation(id);
			Sort loc = SortSingleton.getInstance().getSort("Loc") ;
			FunctionSingleton.getInstance().addFunction(id, new Type(loc) );
		}
	}

	public static void createLocSortLocs() {
		Vector<Location> locs = LocationSingleton.getInstance().getLocations();
		outln();
		outln("# Loc from locs");
		out("datatype Loc ");
		int num = 0;
		for (Location l : locs) {
			num++;
			if (num == 1)
				out(" = ");
			else
				out(" | ");
			out(l.getId());
		}
		outln(" ; ");
	}

	public static String addParameterProcessDeclaration(ProcessDeclaration pd,
			String id, String sortStr) {
		Sort sort = SortSingleton.getInstance().getSort(sortStr);
		if (sort == null)
			return "Error : " + sortStr + " is not a sort";
		addVariable(id, sort);
		pd.addParam(id, sort);
		return null;
	}

	public static Instance makeInstance(String id, Vector<SimpleExpression> ses) {
		Function func = FunctionSingleton.getInstance().getFunction(id);
		if (func != null)
			return new InstanceFunction(func, ses);
		Map map = MapSingleton.getInstance().getMap(id);
		if (map != null)
			return new InstanceMap(map, ses);
		Action act = ActionSingleton.getInstance().getAction(id);
		if (act != null)
			return new InstanceAction(act, ses);
		Process proc = ProcessSingleton.getInstance().getProcess(id);
		if (proc != null) {
			return new InstanceProcess(proc, ses);
		}
		Message msg = MessageSingleton.getInstance().getMessage(id);
		if (msg != null)
			return new InstanceMessage(msg, ses);
		Variable var = VariableSingleton.getInstance().getVariable(id);
		if (var != null)
			return new InstanceVariable(var, ses);
		outln(id + " is not a valied instance ! ");
		return null;
	}

	public static ProcessTerm instanceToProcessTerm(Instance ins) {
		return new ProcessTermInstance(ins);
	}

	public static void setProcessTerm(Process p, ProcessTerm term) {
		p.setTerm(term);
		/*
		 * Vector <Parameter> params = p.getDeclaration().getParams(); for
		 * (Parameter param : params ) {
		 * 
		 * }
		 */
	}

	public static Process addProcessDeclaration(ProcessDeclaration declaration) {
		Process p = new Process(declaration);
		ProcessSingleton.getInstance().addProcess(p);
		return p;
	}

	public static ProcessTerm retProcessTermSum(String id, String sortStr,
			ProcessTerm t) {
		Sort sort = SortSingleton.getInstance().getSort(sortStr);
		if (sort == null)
			outln("Error : in sum context the second ID is not a Sort");
		return new ProcessTermSum(id, sort, t);
	}

	public static void addProcessTermSumVariable(String id, String sortStr) {
		Sort sort = SortSingleton.getInstance().getSort(sortStr);
		if (sort == null)
			outln("Error : in sum context the second ID is not a Sort");
		addVariable(id, sort);

	}
	
	
	public static NetworkTerm retNetworkTermParallel( NetworkTerm left, NetworkTerm right) {
		return new NetworkTermParallel(left,right) ;
	}
	
	public static NetworkTerm retNetworkTermDeploy(String locS,ProcessTerm term){
		Location loc = LocationSingleton.getInstance().getLocaiton (locS) ;
		if ( loc == null ) 
			out ( "Error : " +locS+ " in NetworkTermDeploy is not a Location ");
		return new NetworkTermDeploy(loc,term) ;
	}
	
	public static NetworkTerm retNetworkTermHide(String locS,NetworkTerm term) {
		Location loc = LocationSingleton.getInstance().getLocaiton(locS);
		if ( loc == null ) 
			out ("Error : " +locS+ " in NetworkTermHide is not a location") ;
		return new NetworkTermHide(loc,term) ;
	}
	
	public static NetworkTerm retNetworkTermEncap ( String id , NetworkTerm term ) {
		Message m = MessageSingleton.getInstance().getMessage(id) ;
		if ( m == null ) 
			out ("Error : " +id+ " in NetworkTermEncap is not a message constructor") ;
		return new NetworkTermEncap (m,term);
	}
	
	public static NetworkTerm retNetworkTermAbs( String id , NetworkTerm term){
		Message m = MessageSingleton.getInstance().getMessage(id) ;
		if ( m == null ) 
			out ("Error : " +id+ " in NetworkTermAbs is not a message constructor") ;
		return new NetworkTermAbs(m,term) ;
	}
	
	public static void setInitial(NetworkTerm term) {
		InitialSingleton.getInstance().setNetworkTerm(term);
	}
	
	

	public static Context enterContext() {
		Context c = ContextSingleton.getInstance().enterContext();
		ctraceEnter(ContextSingleton.getInstance().getContext().getLevel());
		return c;
	}

	private static void ctraceEnter(int id) {
		for (int i = 0; i < id + 1; i++)
			err(" ");
		errln(" -> " +id);
	}

	public static Context exitContext() {
		VariableSingleton.getInstance().removeContext(
				ContextSingleton.getInstance().getContext());
		ctraceExit(ContextSingleton.getInstance().getContext().getLevel());
		return ContextSingleton.getInstance().exitContext();
	}

	private static void ctraceExit(int id) {
		for (int i = 0; i < id + 1; i++)
			err(" ");
		errln(" <- " +id);
	}
	private static void err (String str ) {
		//System.out.print( str ) ;
	}
	private static void errln ( String str ) {
		err (str) ;errln () ;
	}

	private static void errln() {
		err ("\n") ;
		
	}
	
	private static void out(String string) {
		 System.out.print(string);
	}

	private static void outln(String string) {
		out(string);
		out("\n");
	}

	private static void outln() {
		out("\n");
	}

}
