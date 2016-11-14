package charlies.entities;

import com.google.appengine.api.datastore.Entity;

public class Score {

	private String id;
	private String category;
	private String pic;
	private String name;
	private int score;
	
	public Score(String category, String name, String pic, int score) {
		//TODO generate Id (UUID ?)
		this.id = name.toUpperCase()+score;
		this.category = category;
		this.pic = pic;
		this.name = name;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void generateId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
