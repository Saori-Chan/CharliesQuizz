package charlies.sections;

import java.util.List;

public class QuizzSectionEvent extends QuizzSection{

	public QuizzSectionEvent(String pic, List<String> answersWho, List<String> answersWhen, List<String> answerWhere) {
		super(pic,answersWho, answersWhen, answerWhere);
		this.who.setQuestion("Who was involved?");
		this.when.setQuestion("When was it?");
		this.where.setQuestion("Where has it tooked place?");
	}
}
