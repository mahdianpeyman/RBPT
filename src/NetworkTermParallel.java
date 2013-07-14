public class NetworkTermParallel extends NetworkTerm {
	private NetworkTerm left;
	private NetworkTerm right;

	public NetworkTermParallel(NetworkTerm left2, NetworkTerm right2) {
		setLeft(left2);
		setRight(right2);
	}

	public NetworkTerm getLeft() {
		return left;
	}

	public void setLeft(NetworkTerm left) {
		this.left = left;
	}

	public NetworkTerm getRight() {
		return right;
	}

	public void setRight(NetworkTerm right) {
		this.right = right;
	}

	@Override
	public String toML() {
		String result = "n_parallel(";
		result += left.toML();
		result += ",";
		result += right.toML();
		return result;
	}

}
