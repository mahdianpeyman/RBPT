import java.util.Vector;


public class InstanceMessage extends Instance {
	private Message msg ;

	public InstanceMessage(Message msg2, Vector<SimpleExpression> ses) {
		setMsg(msg2) ;
		setExprs(ses) ;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}
	

}
