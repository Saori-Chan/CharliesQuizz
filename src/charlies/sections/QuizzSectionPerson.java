package charlies.sections;

import java.util.List;

public class QuizzSectionPerson extends QuizzSection{

	public QuizzSectionPerson(String pic, List<String> answersWho, List<String> answersWhen, List<String> answerWhere, String hint, String abst, String link) {
		super(pic,answersWho, answersWhen, answerWhere, hint, abst, link);
		this.who.setQuestion("Who is he/she?");
		this.when.setQuestion("When was he/she born?");
		this.where.setQuestion("Where was he/she born?");
	}

}
