package charlies.sections;

import java.util.List;

public abstract class QuizzSection {

	private String pic;
	private String questionWho;
	private String questionWhen;
	private String questionWhere;
	private List<String> answersWho;
	private List<String> answersWhen;
	private String answerWhere;
	
	public QuizzSection(String pic, List<String> answersWho, List<String> answersWhen, String answerWhere) {
		this.pic = pic;
		this.questionWho = questionWho();
		this.questionWhen = questionWhen();
		this.questionWhere = questionWhere();
		this.answersWho = answersWho;
		this.answersWhen = answersWhen;
		this.answerWhere = answerWhere;
	}

	public abstract String questionWho();
	public abstract String questionWhen();
	public abstract String questionWhere();
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getQuestionWho() {
		return questionWho;
	}

	public void setQuestionWho(String questionWho) {
		this.questionWho = questionWho;
	}

	public String getQuestionWhen() {
		return questionWhen;
	}

	public void setQuestionWhen(String questionWhen) {
		this.questionWhen = questionWhen;
	}

	public String getQuestionWhere() {
		return questionWhere;
	}

	public void setQuestionWhere(String questionWhere) {
		this.questionWhere = questionWhere;
	}

	public List<String> getAnswersWho() {
		return answersWho;
	}

	public void setAnswersWho(List<String> answersWho) {
		this.answersWho = answersWho;
	}

	public List<String> getAnswersWhen() {
		return answersWhen;
	}

	public void setAnswersWhen(List<String> answersWhen) {
		this.answersWhen = answersWhen;
	}

	public String getAnswerWhere() {
		return answerWhere;
	}

	public void setAnswerWhere(String answerWhere) {
		this.answerWhere = answerWhere;
	}

}
