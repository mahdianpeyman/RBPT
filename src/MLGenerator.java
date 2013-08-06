import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class MLGenerator {
	public static void createMLFuncEQN() {
		Vector<Map> maps = MapSingleton.getInstance().getMaps();
		for (Map m : maps) {
			Vector<Equation> eqns = EquationSingleton.getInstance()
					.getRelatedEquation(m);
			outln();
			outln("(* Equations of " + m.toML() + "*)");
			int numE = 0;
			for (Equation e : eqns) {
				numE++;
				if (numE == 1)
					out("fun ");
				else
					out(" | ");
				out(e.toML());
				outln();
			}
			outln(" ; ");
		}
	}

	public static void createMsgTypeSort_Msg() {
		Vector<Message> msgs = MessageSingleton.getInstance().getMessages();

		outln();
		outln("(* Msg created from msgs rule *)");
		out("datatype Msg ");
		int numM = 0;
		for (Message m : msgs) {
			numM++;
			if (numM == 1)
				out(" = ");
			else
				out(" | ");
			out(m.toML());
			outln(m.getParams().toML());
		}
		if (numM == 0)
			outln(" =  MsgDummy");
		outln(" ; ");

		outln();
		outln("(* Msg_Type created from msgs rule *)");
		out("datatype Msg_Type ");
		numM = 0;
		for (Message m : msgs) {
			numM++;
			if (numM == 1)
				out(" = ");
			else
				out(" | ");
			outln(m.toML() + "_t");
		}
		if (numM == 0)
			outln(" =  MsgDummy_t");
		outln(" ; ");

		outln();
		outln("(* Msg_Type_equal function *)");
		numM = 0;
		for (Message m : msgs) {
			numM++;
			if (numM == 1)
				out(" fun ");
			else
				out(" | ");
			outln(SortSingleton.getInstance().getSort("Msg").toML()
					+ "_Type_equal (" + m.toML() + "_t , " + m.toML()
					+ "_t ) = true");
		}
		if (numM == 0)
			out(" fun ");
		else if (numM > 1)
			out(" | ");
		if (numM != 1)
			outln(SortSingleton.getInstance().getSort("Msg").toML()
					+ ("_Type_equal (x,y) = false"));
		outln(" ; ");

	}

	public static void createActionSortAction() {
		Vector<Action> acts = ActionSingleton.getInstance().getActions();
		outln();
		outln("(* Action created from acts rule*)");
		out("datatype Action ");
		int num = 0;
		for (Action a : acts) {
			num++;
			if (num == 1)
				out(" = ");
			else
				out(" | ");
			out(a.toML());
			outln(a.getParams().toML());
		}
		outln(" | na_Action of NC*NetworkAction");
		outln(" ; ");
	}

	public static void createLocSortLocs() {
		Vector<Location> locs = LocationSingleton.getInstance().getLocations();
		outln();
		outln("(* Loc from locs *)");
		out("datatype Loc ");
		int num = 0;
		for (Location l : locs) {
			num++;
			if (num == 1)
				out(" = ");
			else
				out(" | ");
			out(l.toML());
		}
		outln(" ; ");
	}

	public static void createDatatypesSortsFunctions() {
		Vector<Sort> sorts = SortSingleton.getInstance().getSorts();
		outln();
		outln("(* All datatypes are created from sorts and funcs*)");
		for (Sort s : sorts) {
			Vector<Function> sortFunctions = FunctionSingleton.getInstance()
					.getSortFunction(s);
			if (s.getName().equals("Action"))
				continue;

			// if (s.getName().equals("Loc"))
			// continue;
			if (s.getName().equals("Msg"))
				continue;

			out("datatype " + s.toML());
			int n = 0;
			for (Function f : sortFunctions) {
				Type t = f.getType();
				n++;
				if (n == 1)
					out(" = ");
				else
					out(" | ");
				out(f.toML());
				int numS = 0;
				for (Sort typesort : t.getFirst().getSortList()) {
					numS++;
					if (numS == 1)
						out(" of ");
					else
						out(" * ");
					out(typesort.toML());
				}
				outln();
			}
			Vector<Variable> vars = VariableSingleton.getInstance()
					.getVariables();
			for (Variable v : vars) {
				if (v.getContext().getId() == 0)
					continue;
				if (v.getSort().equals(s) == false)
					continue;
				out("|");
				outln(v.toML());
			}
			outln(";");
		}
	}

	public static void createEqualFunctionForSort() {
		Vector<Sort> sorts = SortSingleton.getInstance().getSorts();
		Sort action = SortSingleton.getInstance().getSort("Action");
		for (Sort s : sorts) {
			if (s.equals(action))
				continue;
			Vector<Function> sortFunctions = FunctionSingleton.getInstance()
					.getSortFunction(s);
			int num = 0;
			outln();
			outln(" (* equal function for Sort  <" + s.toML() + "> :*)");

			for (Function f : sortFunctions) {
				num++;
				if (num == 1)
					out("fun ");
				else
					out(" | ");
				out(s.toML() + "_equal( ");
				out(f.toML());
				Vector<Sort> params = f.getType().getFirst().getSortList();
				if (params.size() > 0) {
					int np = 0;
					for (Sort p : params) {
						np++;
						if (np == 1)
							out("( ");
						else
							out(" , ");
						out("x" + np);
					}
					out(") ");
				}
				out(" , ");
				out(f.toML());
				if (params.size() > 0) {
					int np = 0;
					for (Sort p : params) {
						np++;
						if (np == 1)
							out("( ");
						else
							out(" , ");
						out("y" + np);
					}
					out(") ");
				}
				out(") = true ");
				int np = 0;
				for (Sort p : params) {
					np++;
					out(" andalso " + p.toML() + "_equal( x" + np + " , y" + np
							+ ") ");
				}
				outln();
			}
			if (num == 0)
				out("fun ");
			else if (num > 1)
				out("| ");
			if (num != 1)
				outln(s.toML() + "_equal (x,y) = false ");
			outln(";");
		}

	}

	private static void outln(String string) {
		out(string);
		out("\n");
	}

	private static void outln() {
		out("\n");
	}

	private static void out(String string) {
		System.out.print(string);
	}

	public static void createML() {
		embedStaticCode("StaticCodes/starting.sml");
		// createLocSortLocs();
		createDatatypesSortsFunctions();
		createMsgTypeSort_Msg();
		createPairDatatype();
		embedStaticCode("StaticCodes/NC_Datatype.sml");
		createNetworkActionDatatype();
		createActionSortAction();
		createVariableDatatype();
		createMLProcessCallDatatype();
		createProcessDatatype();
		embedStaticCode("StaticCodes/RSE_Datatype.sml");
		createSameSortFunction();
		createIsTypeFunction();
		createMLFuncEQN();
		createEqualFunctionForSort();
		embedStaticCode("StaticCodes/p_equal.sml");
		embedStaticCode("StaticCodes/nc_include.sml");
		embedStaticCode("StaticCodes/nc_equal.sml");
		createEqualAction();
		embedStaticCode("StaticCodes/nc_filter.sml");
		embedStaticCode("StaticCodes/m_filter.sml");
		embedStaticCode("StaticCodes/rew.sml");
		embedStaticCode("StaticCodes/reduce.sml");
		embedStaticCode("StaticCodes/rn_cnt_fresh_var_get_fvar.sml");
		embedStaticCode("StaticCodes/exception.sml");
		embedStaticCode("StaticCodes/val_new_add.sml");
		embedStaticCode("StaticCodes/s1.sml");
		embedStaticCode("StaticCodes/hadNewPE.sml");
		embedStaticCode("StaticCodes/mergeRSE.sml");
		embedStaticCode("StaticCodes/addNewPE.sml");
		embedStaticCode("StaticCodes/linearize.sml");
		callLinearizeFunction();

	}

	private static void createEqualAction() {

		Sort s = SortSingleton.getInstance().getSort("Action");
		Vector<Function> sortFunctions = FunctionSingleton.getInstance()
				.getSortFunction(s);
		int num = 0;
		outln();
		outln(" (* equal function for Sort  <" + s.toML() + "> :*)");
		out("fun ");
		for (Function f : sortFunctions) {
			out(s.toML() + "_equal( ");
			out(f.toML());
			Vector<Sort> params = f.getType().getFirst().getSortList();
			if (params.size() > 0) {
				int np = 0;
				for (Sort p : params) {
					np++;
					if (np == 1)
						out("( ");
					else
						out(" , ");
					out("x" + np);
				}
				out(") ");
			}
			out(" , ");
			out(f.toML());
			if (params.size() > 0) {
				int np = 0;
				for (Sort p : params) {
					np++;
					if (np == 1)
						out("( ");
					else
						out(" , ");
					out("y" + np);
				}
				out(") ");
			}
			out(") = true ");
			int np = 0;
			for (Sort p : params) {
				np++;
				out(" andalso " + p.toML() + "_equal( x" + np + " , y" + np
						+ ") ");
			}
			outln();
			out("| ");
		}
		outln(" "
				+ s.toML()
				+ "_equal(na_Action(nc1,nsnd_NA(m1,l1)),na_Action(nc2,nsnd_NA(m2,l2))) =");
		outln("     nc_equal(nc1,nc2) andalso Msg_equal(m1,m2) andalso Loc_equal(l1,l2)");
		outln("| "
				+ s.toML()
				+ "_equal(na_Action(nc1,nrcv_NA(m1)),na_Action(nc2,nrcv_NA(m2))) =");
		outln("     nc_equal(nc1,nc2) andalso Msg_equal(m1,m2)");
		outln("| "
				+ s.toML()
				+ "_equal(na_Action(nc1,tau_NA),na_Action(nc2,tau_NA)) = nc_equal(nc1,nc2)");
		outln("| " + s.toML() + "_equal (x,y) = false ");
		outln(";");
	}

	private static void createIsTypeFunction() {
		Vector<Message> msgs = MessageSingleton.getInstance().getMessages();
		outln();
		outln("(* isType Function *)");
		int numM = 0;
		for (Message m : msgs) {
			numM++;
			if (numM == 1)
				out("fun ");
			else
				out(" | ");
			out(" isType ( " + m.toML() + "_t , " + m.toML());
			int np = 0;
			for (; np < m.getParams().getSortList().size(); np++) {
				if (np == 0)
					out("(");
				else
					out(",");
				out("x" + np);
			}
			if (np != 0)
				out(")");
			outln(") = true");
		}
		if (numM == 0)
			out("fun ");
		else if (numM > 1)
			out(" | ");
		if (numM != 1)
			outln(" isType(x,y) = false");
		outln(" ; ");

	}

	private static void createSameSortFunction() {
		Vector<Sort> sorts = SortSingleton.getInstance().getSorts();
		outln();
		outln("(*SameSort function*)");
		int n = 0;
		for (Sort s : sorts) {
			n++;
			if (n == 1)
				out("fun ");
			else
				out(" | ");
			outln("SameSort ( " + s.toML() + "_Var (x) , " + s.toML()
					+ "_Var(y) ) = true ");

		}
		outln(" | SameSort (x,y) = false	");
		outln(" ;");

	}

	private static void createNetworkActionDatatype() {
		outln();
		outln("(* NetworkAction Datatype *)");
		outln("datatype NetworkAction = nsnd_NA of "
				+ SortSingleton.getInstance().getSort("Msg").toML() + " * "
				+ SortSingleton.getInstance().getSort("Loc").toML());
		outln(" | nrcv_NA of "
				+ SortSingleton.getInstance().getSort("Msg").toML());
		outln(" | tau_NA");
		outln(" ; ");

	}

	private static void createPairDatatype() {
		outln();
		outln("(*Pair Datatype*)");
		outln("datatype Pair = conn of "
				+ SortSingleton.getInstance().getSort("Loc").toML() + " * "
				+ SortSingleton.getInstance().getSort("Loc").toML());
		outln(" | disconn of "
				+ SortSingleton.getInstance().getSort("Loc").toML() + " * "
				+ SortSingleton.getInstance().getSort("Loc").toML());
		outln(";");
	}

	private static void embedStaticCode(String fileName) {
		BufferedReader br = null;
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));

			while ((sCurrentLine = br.readLine()) != null) {
				outln(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	private static void createVariableDatatype() {
		outln();
		outln("(* VarName Datatype*)");
		outln("datatype VarName ");
		Vector<Sort> sorts = SortSingleton.getInstance().getSorts();
		int n = 0;
		for (Sort s : sorts) {
			n++;
			if (n == 1)
				out(" = ");
			else
				out(" | ");
			outln(s.toML() + "_Var of " + s.toML());
		}
		outln(";");
	}

	private static void callLinearizeFunction() {
		Vector<Process> ps = ProcessSingleton.getInstance().getProcesses();
		outln();
		outln("(*call Linearize*)");
		outln();
		out("linearize (");
		int n = 0;
		for (Process p : ps) {
			n++;
			if (n == 1)
				out(" [ ");
			else
				out(" , ");
			out("def ( ");
			out(p.getDeclaration().toML());
			out(" , ");
			out(p.getTerm().toML());
			outln(" ) ");
		}
		if (n == 0)
			out(" [ ");
		outln(" ] ) ");
		outln(" ; ");

	}

	private static void createMLProcessCallDatatype() {
		Vector<Process> pts = ProcessSingleton.getInstance().getProcesses();
		outln();
		outln("(* Datatype RecName*) ");
		outln("datatype RecName ");
		int n = 0;
		for (Process p : pts) {
			n++;
			if (n == 1)
				out(" = ");
			else
				out(" | ");
			outln(p.toML());
		}
		outln(" | rn_RecName of int");
		if (n == 0)
			outln(" = RecVarDummy ");
		outln(";");
		outln();
		outln("(* Datatype RecVar*) ");
		outln(" datatype RecVar = rv of RecName*VarName list; ");

	}

	private static void createProcessDatatype() {
		outln();
		outln("(* DataType Porcess*)");
		outln("datatype Process = p_nil");
		outln("| p_choice of Process * Process");
		outln("| p_prefix of "
				+ SortSingleton.getInstance().getSort("Action").toML()
				+ " * Process");
		outln("| p_cond of "
				+ SortSingleton.getInstance().getSort("Bool").toML()
				+ " * Process*Process");
		outln("| p_recvar of RecVar");
		outln("| p_sum of VarName * Process");
		outln("| n_encap of "
				+ SortSingleton.getInstance().getSort("Msg").toML() + "_Type"
				+ " * Process");
		outln("| n_abs of " + SortSingleton.getInstance().getSort("Msg").toML()
				+ "_Type" + " * Process");
		outln("| n_hide of "
				+ SortSingleton.getInstance().getSort("Loc").toML()
				+ " * Process");
		outln("| n_deploy of "
				+ SortSingleton.getInstance().getSort("Loc").toML()
				+ " * Process");
		outln("| n_parallel of " + "Process * Process");
		outln("| n_sync of " + "Process * Process");
		outln("| n_lmerge of " + "Process * Process");
		outln(";");

	}
}
