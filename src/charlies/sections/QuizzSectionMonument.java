package charlies.sections;

import java.util.List;

public class QuizzSectionMonument extends QuizzSection{

	public QuizzSectionMonument(String pic, List<String> answersWho, List<String> answersWhen, List<String> answerWhere) {
		super(pic,answersWho, answersWhen, answerWhere);
		this.who.setQuestion("Who has build it?");
		this.when.setQuestion("When was it build?");
		this.where.setQuestion("Where is it?");
	}
}
