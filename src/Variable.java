public class Variable implements ML {
	private String name;
	private Sort sort;
	private Context context;
	private boolean isValid;

	public Variable(String varStr, Sort sort2, Context context2) {
		setName(varStr);
		setSort(sort2);
		setContext(context2);
		setValid(true);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		Variable v = (Variable) o;
		return v.getName().equals(name) && v.getSort().equals(sort)
				&& isValid == v.isValid() && v.getContext().equals(context);
	}

	public boolean matchCall(String name2, Context c) {
		return name.equals(name2) && context.equals(c) && isValid();
	}

	public String toML() {
		return getName() + "_" + getContext().toML();
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
