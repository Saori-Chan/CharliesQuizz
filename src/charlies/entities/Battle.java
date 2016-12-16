package charlies.entities;

public class Battle {

	private String pic;
	private String commanders;
	private String date;
	private String place;
	private String name;
	private String abst;
	private String link;	
	
	public Battle(String pic, String commanders, String date, String place, String name, String abst, String link) {
		super();
		this.pic = pic;
		this.commanders = commanders;
		this.date = date;
		this.place = place;
		this.name = name;
		this.abst = abst;
		this.link= link;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbst() {
		return abst;
	}

	public void setAbst(String abst) {
		this.abst = abst;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
