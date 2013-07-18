public class Parameter implements ML {
	private String id;
	private Sort type;
	private Context context;

	public Parameter(String id2, Sort sort, Context context) {
		id = id2;
		type = sort;
		setContext(context);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Sort getType() {
		return type;
	}

	public void setType(Sort type) {
		this.type = type;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String toML() {
		return getId() + "_" + getType().toML() + "_" + getContext().toML();
	}

}
