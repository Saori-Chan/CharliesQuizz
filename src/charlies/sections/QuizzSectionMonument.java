package charlies.sections;

import java.util.List;

public class QuizzSectionMonument extends QuizzSection{

	public QuizzSectionMonument(String pic, List<String> answersWho, List<String> answersWhen, String answerWhere) {
		super(pic,answersWho, answersWhen, answerWhere);
	}

	public String questionWhere() {
		return "Who has build it ?";
	}

	public String questionWhen() {
		return "When was it built ?";
	}

	public String questionWho() {
		return "Where is it ?";
	}

}
