public class Context {
	static int counter = 0;
	private int level = 0 ;
	private int id;
	private Context father;

	Context() {
		id = counter++;
		father = null;
		level = 0 ;
	}

	Context(Context f) {
		id = counter++;
		father = f;
		level=father.getLevel() + 1;
	}

	public Context getFather() {
		return father;
	}

	public void setFather(Context father) {
		this.father = father;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals ( Object o ) {
		if ( o == null ) 
			return false ;
		return  ((Context) o).getId() == id;		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
