import java.util.Vector;

public class ContextSingleton {

	private static ContextSingleton instance = null;
	private Vector<Context> stack;

	private ContextSingleton() {
		stack = new Vector<Context>();
	}

	public static ContextSingleton getInstance() {
		if (instance == null) 
			instance = new ContextSingleton();
		return instance;
	}

	public Context getContext() {
		return stack.get(0);
	}

	public Context enterContext() {
		if (stack.size() > 0)
			stack.add(0, new Context(stack.get(0)));
		else
			stack.add(0, new Context());
		return stack.get(0);
	}

	public Context exitContext() {
		stack.remove(0);
		if (stack.size() > 0)
			return stack.get(0);
		return null;
	}

}