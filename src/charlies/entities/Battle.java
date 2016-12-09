package charlies.entities;

public class Battle {

	private String pic;
	private String commanders;
	private String dates;
	private String place;
	
	public Battle(String pic, String commanders, String dates, String place) {
		super();
		this.pic = pic;
		this.commanders = commanders;
		this.dates = dates;
		this.place = place;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getCommanders() {
		return commanders;
	}

	public void setCommanders(String commanders) {
		this.commanders = commanders;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
