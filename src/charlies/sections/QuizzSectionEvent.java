package charlies.sections;

import java.util.List;

public class QuizzSectionEvent extends QuizzSection {

	public QuizzSectionEvent(String pic, List<String> answersWho, List<String> answersWhen, List<String> answerWhere, String hint, String abst, String link) {
		super(pic,answersWho, answersWhen, answerWhere, hint, abst, link);
		this.who.setQuestion("Who was involved?");
		this.when.setQuestion("When was it?");
		this.where.setQuestion("Where has it tooked place?");
	}
}
