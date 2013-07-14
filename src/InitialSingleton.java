


public class InitialSingleton {

	private static InitialSingleton instance = null ;
	private NetworkTerm networkTerm;
	private InitialSingleton() {
	}
	public static InitialSingleton getInstance () {
		if (instance == null ) {
			instance = new InitialSingleton() ;
		}
		return instance ;
	}
	public NetworkTerm getNetworkTerm() {
		return networkTerm;
	}
	public void setNetworkTerm(NetworkTerm networkTerm) {
		this.networkTerm = networkTerm;
	}
	


}