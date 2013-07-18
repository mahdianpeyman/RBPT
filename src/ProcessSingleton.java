import java.util.Vector;

public class ProcessSingleton {

	private static ProcessSingleton instance = null;
	private Vector<Process> procs;

	private ProcessSingleton() {
		procs = new Vector<Process>();
	}

	public static ProcessSingleton getInstance() {
		if (instance == null)
			instance = new ProcessSingleton();
		return instance;
	}

	public void addProcess(Process f) {
		procs.add(f);
	}

	public Process getProcess(String name) {
		for (int i = 0; i < procs.size(); i++) 
			if (procs.get(i).getID().equals(name)) 
				return procs.get(i);
		return null;
	}

	public Vector<Process> getProcesses() {
		return procs;
	}

}