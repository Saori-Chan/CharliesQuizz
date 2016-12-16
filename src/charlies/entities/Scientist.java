package charlies.entities;

public class Scientist {

	private String pic;
	private String name;
	private String birth;
	private String place;
	private String abst;
	private String link;
	
	public Scientist(String pic, String name, String birth, String place, String abst, String link) {
		this.pic = pic;
		this.name = name;
		this.birth = birth;
		this.place = place;
		this.abst = abst;
		this.link= link;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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
	
}
