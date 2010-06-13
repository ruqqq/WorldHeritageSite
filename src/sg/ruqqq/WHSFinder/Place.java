package sg.ruqqq.WHSFinder;

// Place.java which is intended to store data of places
// Implements Comparable so we can apply sorting algorithm when Place is in a List
public class Place implements Comparable<Place>{
	String name;
	String description;
	String imageUrl;
	String url;
	String address; // unused
	
	double latitude;
	double longitude;
	
	public Place(String name, String description, String imageUrl, String url, String address, double latitude, double longitude) {
		super();
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.url = url;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return name;
	}

	// This is the magic method which allows sorting of the items when Place is positioned in a List
	public int compareTo(Place obj) {
		int lastCmp = getName().compareTo(obj.getName());
        return (lastCmp != 0 ? lastCmp :
                getName().compareTo(obj.getName()));
	}

	// the rest are getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
