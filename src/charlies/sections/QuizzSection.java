package charlies.sections;

import java.util.List;

public abstract class QuizzSection {

	private String pic;
	protected QuestionBlock who;
	protected QuestionBlock when;
	protected QuestionBlock where;
		
	public QuizzSection(String pic, List<String> whoAnswers, List<String> whenAnswers, List<String> whereAnswers) {
		this.pic = pic;
		this.who = new QuestionBlock(whoAnswers);
		this.when = new QuestionBlock(whenAnswers);
		this.where = new QuestionBlock(whereAnswers);
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public QuestionBlock getWho() {
		return who;
	}

	public void setWho(QuestionBlock who) {
		this.who = who;
	}

	public QuestionBlock getWhen() {
		return when;
	}

	public void setWhen(QuestionBlock when) {
		this.when = when;
	}

	public QuestionBlock getWhere() {
		return where;
	}

	public void setWhere(QuestionBlock where) {
		this.where = where;
	}

	
}
