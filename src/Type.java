public class Type {
	private Tuple first;
	private Sort second;

	public Tuple getFirst() {
		return first;
	}

	public void setFirst(Tuple t) {
		first = t;
	}

	public Sort getSecond() {
		return second;
	}

	public void setSecond(Sort s) {
		second = s;
	}

	public Type() {
		first = new Tuple();

	}

	public Type(Sort sort) {
		first = new Tuple();
		second = sort;
	}

	public Type(Tuple tuple) {
		setFirst(tuple);
	}
}
