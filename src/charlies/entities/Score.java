package charlies.entities;


public class Score {

	private String category;
	private String pic;
	private String name;
	private long score;
	
	public Score(String category, String name, String pic, long score) {
		
		this.category = category;
		this.pic = pic;
		this.name = name;
		this.score = score;
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

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
