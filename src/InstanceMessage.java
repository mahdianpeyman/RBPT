import java.util.Vector;

public class InstanceMessage extends Instance {
	private Message msg;

	public InstanceMessage(Message msg2, Vector<SimpleExpression> ses) {
		setID(msg2.getID());
		setMsg(msg2);
		setExprs(ses);
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	@Override
	public Sort getSort() {
		return SortSingleton.getInstance().getSort("Msg");
	}

	@Override
	public String toML() {
		String result = msg.getID();
		int num = 0;
		for (SimpleExpression e : exprs) {
			num++;
			if (num == 1)
				result += " ( ";
			else
				result += " , ";
			result += e.toML();
		}
		result += " )";
		return result;
	}

}
