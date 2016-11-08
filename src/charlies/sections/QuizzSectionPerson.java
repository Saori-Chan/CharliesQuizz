package charlies.sections;

import java.util.List;

public class QuizzSectionPerson extends QuizzSection{

	public QuizzSectionPerson(String pic, List<String> answersWho, List<String> answersWhen, String answerWhere) {
		super(pic,answersWho, answersWhen, answerWhere);
	}

	public String questionWhere() {
		return "Who is he/she ?";
	}

	public String questionWhen() {
		return "When was he/she born ?";
	}

	public String questionWho() {
		return "Where was he/she born ?";
	}

}
