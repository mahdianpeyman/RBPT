import java.util.Vector;

public class Manager {
	public int a;

	public static void addFunctions(Vector<String> v, Type t) {
		for (int i = 0; i < v.size(); i++)
			addFunction(v.elementAt(i), t);
	};

	public static String addFunction(String id, Type t) {
		FunctionSingleton.getInstance().addFunction(id, t);
		return id;
	}

	public static String addSort(String id) {
		Sort s = new Sort(id);
		SortSingleton.getInstance().addSort(s);
		return id;

	}

	public static String addMap(String id, Type t) {
		Map m = new Map(id);
		m.setType(t);
		MapSingleton.getInstance().addMap(m);
		return id;
	}

	public static void addVariables(Vector<String> v, String s) {
		Sort tSort = SortSingleton.getInstance().getSort(s);
		if (tSort == null)
			errln(" Error : " + s + " is not a valid Sort ");
		for (String vName : v)
			Manager.addVariable(vName, tSort);
	}

	public static Variable addVariable(String vName, Sort vSort) {
		return VariableSingleton.getInstance().addVariable(vName, vSort,
				ContextSingleton.getInstance().getContext());
	}

	public static void addEquation(SimpleExpression left, SimpleExpression right) {
		Equation e = new Equation(left, right);
		testln("Test : " + left.getID() + " : " + left);
		EquationSingleton.getInstance().addEquation(e);
	}

	public static void addMessages(Vector<String> ids, Tuple t) {
		for (String id : ids) {
			Message m = new Message(id, t);
			MessageSingleton.getInstance().addMessage(m);
			Type msgFunctionType = new Type(SortSingleton.getInstance()
					.getSort("Msg"));
			msgFunctionType.setFirst(t);
			FunctionSingleton.getInstance().addFunction(id, msgFunctionType);
		}
	}

	public static void addActions(Vector<String> ids, Tuple t) {
		for (String id : ids) {
			Action a = new Action(id, t);
			ActionSingleton.getInstance().addAction(a);
			Sort action = SortSingleton.getInstance().getSort("Action");
			Type type = new Type(action);
			type.setFirst(t);
			FunctionSingleton.getInstance().addFunction(id, type);
		}
	}

	public static void addLocations(Vector<String> ids) {
		for (String id : ids) {
			LocationSingleton.getInstance().addLocation(id);
			Sort loc = SortSingleton.getInstance().getSort("Loc");
			FunctionSingleton.getInstance().addFunction(id, new Type(loc));
		}
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

	public static Instance makeInstance(String id, Vector<SimpleExpression> args) {

		Process proc = ProcessSingleton.getInstance().getProcess(id);
		if (proc != null) {
			Vector<Parameter> params = proc.getDeclaration().getParams();
			Vector<Sort> sortList = new Vector<Sort>();
			for (Parameter p : params)
				sortList.add(p.getType());
			for (int i = 0; i < min(sortList.size(), args.size()); i++) {
				Sort s = sortList.get(i);
				SimpleExpression e = args.get(i);
				if (s.equals(e.getSort()) == false)
					errln("Error : In ProcessInstanc <"
							+ proc.getDeclaration().getId() + ">Argument #"
							+ (i + 1) + "(" + e.getID()
							+ ") doesn't match with Sort : " + s.getName());
			}
			if (sortList.size() != args.size()) {
				errln("Error : Singniture of Map '"
						+ proc.getDeclaration().getId()
						+ "' doesn't match with arguments");
				errln("Error : " + sortList.size() + " ! = " + args.size());
			}
			Instance inst = new InstanceProcess(proc, args);
			return inst;
		}
		Variable var = VariableSingleton.getInstance().getVariable(id);
		if (var != null) {
			if (args.size() > 0)
				errln("Error : variable <" + var.getName()
						+ "> should not have any argument");
			return new InstanceVariable(var);
		}
		Location loc = LocationSingleton.getInstance().getLocaiton(id);
		if (loc != null) {
			if (args.size() > 0)
				errln("Error : Location <" + loc.getId()
						+ "> should not have any argument");
			return new InstanceLocation(loc);
		}
		Action act = ActionSingleton.getInstance().getAction(id);
		if (act != null) {
			Vector<Sort> sortList = act.getParams().getSortList();
			for (int i = 0; i < min(sortList.size(), args.size()); i++) {
				Sort s = sortList.get(i);
				SimpleExpression e = args.get(i);
				if (s.equals(e.getSort()) == false)
					errln("Error : for Action : <" + act.getId()
							+ "> Argument #" + (i + 1) + "(" + e.getID()
							+ ") doesn't match with Sort : " + s.getName());
			}
			if (sortList.size() != args.size())
				errln("Error : Singniture of Function '" + act.getId()
						+ "' doesn't match with arguments");
			Instance inst = new InstanceAction(act, args);
			return inst;
		}

		Message msg = MessageSingleton.getInstance().getMessage(id);
		if (msg != null) {
			Vector<Sort> sortList = msg.getParams().getSortList();
			for (int i = 0; i < min(sortList.size(), args.size()); i++) {
				Sort s = sortList.get(i);
				SimpleExpression e = args.get(i);
				if (s.equals(e.getSort()) == false)
					errln("Error : Argument #" + (i + 1) + "(" + e.getID()
							+ ") doesn't match with Sort : " + s.getName());
			}
			if (sortList.size() != args.size())
				errln("Error : Singniture of Function '" + msg.getID()
						+ "' doesn't match with arguments");
			Instance inst = new InstanceMessage(msg, args);
			return inst;
		}
		Function func = FunctionSingleton.getInstance().getFunction(id);
		if (func != null) {
			Vector<Sort> sortList = func.getType().getFirst().getSortList();
			for (int i = 0; i < min(sortList.size(), args.size()); i++) {
				Sort s = sortList.get(i);
				SimpleExpression e = args.get(i);
				if (s.equals(e.getSort()) == false)
					errln("Error : Argument #" + (i + 1) + "(" + e.getID()
							+ ") doesn't match with Sort : " + s.getName());
			}
			if (sortList.size() != args.size())
				errln("Error : Singniture of Function '" + func.getName()
						+ "' doesn't match with arguments");
			Instance inst = new InstanceFunction(func, args);
			return inst;
		}
		Map map = MapSingleton.getInstance().getMap(id);
		if (map != null) {
			Vector<Sort> sortList = map.getType().getFirst().getSortList();
			for (int i = 0; i < min(sortList.size(), args.size()); i++) {
				Sort s = sortList.get(i);
				SimpleExpression e = args.get(i);
				if (s.equals(e.getSort()) == false)
					errln("Error : Argument #" + (i + 1) + "(" + e.getID()
							+ ") doesn't match with Sort : " + s.getName());
			}
			if (sortList.size() != args.size()) {
				errln("Error : Singniture of Map '" + map.getName()
						+ "' doesn't match with arguments");
				errln("Error : " + sortList.size() + " ! = " + args.size());
			}
			Instance inst = new InstanceMap(map, args);
			return inst;
		}

		errln("Erorr" + id + " is not a valied instance ! ");
		return null;
	}

	public static Instance makeInstanceSND(Instance inst) {
		if (SortSingleton.getInstance().getSort("Msg").equals(inst.getSort()) == false) {
			errln("Error : <" + inst.toML() + "> is not a Message");
			return null;
		}
		return new InstanceAction(ActionSingleton.getInstance()
				.getAction("snd"), inst);
	}

	public static Instance makeInstanceRCV(Instance inst) {
		if (SortSingleton.getInstance().getSort("Msg").equals(inst.getSort()) == false) {
			errln("Error : <" + inst.toML() + "> is not a Message");
			return null;
		}
		return new InstanceAction(ActionSingleton.getInstance()
				.getAction("rcv"), inst);
	}

	public static ProcessTerm instanceToProcessTerm(Instance ins) {
		return new ProcessTermInstance(ins);
	}

	public static void setProcessTerm(Process p, ProcessTerm term) {
		p.setTerm(term);
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
			errln("Error : in sum context the second ID is not a Sort");
		return new ProcessTermSum(id, sort, t);
	}

	public static void addProcessTermSumVariable(String id, String sortStr) {
		Sort sort = SortSingleton.getInstance().getSort(sortStr);
		if (sort == null)
			errln("Error : in sum context the second ID is not a Sort");
		addVariable(id, sort);

	}

	public static NetworkTerm retNetworkTermParallel(NetworkTerm left,
			NetworkTerm right) {
		NetworkTerm nt = new NetworkTermParallel(left, right);
		return nt;
	}

	public static NetworkTerm retNetworkTermDeploy(String locS, ProcessTerm term) {
		Location loc = LocationSingleton.getInstance().getLocaiton(locS);
		if (loc == null)
			errln("Error : " + locS
					+ " in NetworkTermDeploy is not a Location ");
		NetworkTerm nt = new NetworkTermDeploy(loc, term);
		return nt;
	}

	public static NetworkTerm retNetworkTermHide(String locS, NetworkTerm term) {
		Location loc = LocationSingleton.getInstance().getLocaiton(locS);
		if (loc == null)
			errln("Error : " + locS + " in NetworkTermHide is not a location");
		return new NetworkTermHide(loc, term);
	}

	public static NetworkTerm retNetworkTermEncap(String id, NetworkTerm term) {
		Message m = MessageSingleton.getInstance().getMessage(id);
		if (m == null)
			errln("Error : " + id
					+ " in NetworkTermEncap is not a message constructor");
		return new NetworkTermEncap(m, term);
	}

	public static NetworkTerm retNetworkTermAbs(String id, NetworkTerm term) {
		Message m = MessageSingleton.getInstance().getMessage(id);
		if (m == null)
			errln("Error : " + id
					+ " in NetworkTermAbs is not a message constructor");
		return new NetworkTermAbs(m, term);
	}

	public static NetworkTerm retNetworkTermSingle(NetworkTerm term) {
		return term;
	}

	public static void setInitial(NetworkTerm term) {
		InitialSingleton.getInstance().setNetworkTerm(term);
	}

	public static void createInitial() {
		MLGenerator.createInitial();
	}

	public static Type retType() {
		return new Type();
	}

	public static void setTypeFirst(Type type, Tuple tuple) {
		type.setFirst(tuple);
	}

	public static Tuple retTupleSortList(Vector<String> strs) {
		Tuple t = new Tuple();
		for (String str : strs) {
			Sort s = SortSingleton.getInstance().getSort(str);
			if (s == null)
				errln("Error :" + str + "in Tuple is not a Sort/Loc");
			t.addSort(s);
		}
		return t;
	}

	public static void finalTest() {
		Vector<Equation> eqs = EquationSingleton.getInstance().getEquations();
		for (Equation e : eqs) {
			test(e.getLeft().getID() + " : ");
			testln(e.getLeft() + " = " + e.getRight());

		}
	}

	public static void setTypeSecond(Type type, String str) {
		Sort sort = SortSingleton.getInstance().getSort(str);
		if (sort == null)
			errln("Error" + str + "not a valid Sort ");
		else
			type.setSecond(sort);
	}

	public static Context enterContext() {
		Context c = ContextSingleton.getInstance().enterContext();
		ctraceEnter(ContextSingleton.getInstance().getContext().getLevel());
		return c;
	}

	private static void ctraceEnter(int id) {
		for (int i = 0; i < id + 1; i++)
			test(" ");
		testln(" -> " + id);
	}

	public static Context exitContext() {
		VariableSingleton.getInstance().removeContext(
				ContextSingleton.getInstance().getContext());
		ctraceExit(ContextSingleton.getInstance().getContext().getLevel());
		return ContextSingleton.getInstance().exitContext();
	}

	public static void createML() {
		MLGenerator.createML();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static void ctraceExit(int id) {
		for (int i = 0; i < id + 1; i++)
			test(" ");
		testln(" <- " + id);
	}

	private static void errln(String str) {
		err(str);
		errln();
	}

	private static void errln() {
		err("\n");

	}

	static void testln(String string) {
		test(string);
		test("\n");
	}

	private static void testln() {
		test("\n");
	}

	private static int min(int size, int size2) {
		return (size > size2) ? size2 : size;
	}

	private static void err(String str) {
		System.out.print(str);
	}

	private static void test(String string) {
		// System.out.print(string);
	}

}
