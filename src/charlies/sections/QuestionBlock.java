package charlies.sections;

import java.util.List;

public class QuestionBlock {

	String question;
	List<String> answers;
	
	public QuestionBlock(List<String> answers) {
		this.answers = answers;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

}
