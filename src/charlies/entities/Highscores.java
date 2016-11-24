package charlies.entities;

import java.util.List;

public class Highscores {

	private List<Score> hs;
	private int place;
	private Score scorePlayer;
	
	public Highscores(List<Score> hs, int place, Score s) {
		this.hs = hs;
		this.place = place;
		this.scorePlayer = s;
	}

	public List<Score> getHs() {
		return hs;
	}

	public void setHs(List<Score> hs) {
		this.hs = hs;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public Score getS() {
		return scorePlayer;
	}

	public void setS(Score s) {
		this.scorePlayer = s;
	}
	
}
