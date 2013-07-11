public class FunctionCallExpression extends SimpleExpression {
	private Function func;

	public Function getFunc() {
		return func;
	}

	public void setFunc(Function func) {
		this.func = func;
	}

	public FunctionCallExpression() {

	}

	public FunctionCallExpression(Function f) {

		func = f;
	}

}
