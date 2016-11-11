package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionMonument;

public class MonumentsGenerator extends SectionGenerator {
	
	public MonumentsGenerator() {
		super();
		//request += "";//DBPEDIA REQUEST
	}

	public List<QuizzSection> generate(int nb) {
		List<QuizzSection> list = new ArrayList<QuizzSection>();
		//DBPEDIA CALL + WRONG ANSWERS GENERATION
		
		//QuizzSection current = new QuizzSectionMonument(pic, answersWho, answersWhen, answerWhere);
		return list;
	}

}
