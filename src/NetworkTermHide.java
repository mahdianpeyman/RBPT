
public class NetworkTermHide extends NetworkTerm {
	private Location location;
	private NetworkTerm networkTerm ;
	public NetworkTermHide(Location loc, NetworkTerm term) {
		setLocation(loc) ;
		setNetworkTerm(term ) ;
		// TODO Auto-generated constructor stub
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public NetworkTerm getNetworkTerm() {
		return networkTerm;
	}
	public void setNetworkTerm(NetworkTerm networkTerm) {
		this.networkTerm = networkTerm;
	}
	

}
