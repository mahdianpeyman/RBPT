import java.util.Vector;


public class Manager {
	public int a ;
	public static void addFunctions (Vector<Function> v ,Type t){
		for (int i = 0 ; i < v.size () ; i++ ) {
			v.elementAt(i).type = t;
		}
	};

}
