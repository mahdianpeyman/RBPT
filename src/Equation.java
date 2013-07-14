public class Equation {
	private SimpleExpression left;
	private SimpleExpression right;

	public Equation() {
	}

	public Equation(SimpleExpression left2, SimpleExpression right2) {
		left = left2;
		right = right2;
	}

	public SimpleExpression getLeft() {
		return left;
	}

	public void setLeft(SimpleExpression left) {
		this.left = left;
	}

	public SimpleExpression getRight() {
		return right;
	}

	public void setRight(SimpleExpression right) {
		this.right = right;
	}

	public boolean isRelated(Map m) {
		return left.isRelated(m);
	}
}
