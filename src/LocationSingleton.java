
import java.util.Vector;


public class LocationSingleton {

	private static LocationSingleton instance = null ;
	private Vector <Location> locs  ;
	private LocationSingleton() {
		locs = new Vector<Location> () ;
	}
	public static LocationSingleton getInstance () {
		if (instance == null ) {
			instance = new LocationSingleton() ;
		}
		return instance ;
	}
	public void addLocation ( String id ) {
		Location l = new Location(id);
		locs.add(l) ;
	}
	
	public Vector <Location> getLocations ( ) {
		return locs ;
	}
	

}