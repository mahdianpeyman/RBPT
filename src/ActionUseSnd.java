
public class ActionUseSnd extends ActionUse {
	private Instance data ;
	ActionUseSnd ( Instance i ) {
		setData(i) ;
	}
	public Instance getData() {
		return data;
	}
	public void setData(Instance data) {
		this.data = data;
	}

}
