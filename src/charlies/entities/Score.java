package charlies.entities;

public class Score {

	private String id;
	private String category;
	private String name;
	private int score;
	
	public Score(String category, String name, int score) {
		//TODO generate Id (UUID ?)
		this.id = name.toUpperCase()+score;
		this.category = category;
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

}
