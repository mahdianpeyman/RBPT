
public class ActionUseInstance extends ActionUse{
	private Instance inst ;

	public ActionUseInstance(Instance i) {
		setInst(i);
	}
	public Instance getInst() {
		return inst;
	}

	public void setInst(Instance inst) {
		this.inst = inst;
	}
}
