package charlies.entities;

public class Battle {

	private String pic;
	private String commanders;
	private String date;
	private String place;
	
	public Battle(String pic, String commanders, String date, String place) {
		super();
		this.pic = pic;
		this.commanders = commanders;
		this.date = date;
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

	public String getDate() {
		return date;
	}

	public void setDates(String date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
