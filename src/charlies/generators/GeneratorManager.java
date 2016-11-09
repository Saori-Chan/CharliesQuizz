package charlies.generators;

import java.util.List;

import charlies.exceptions.UnknownCategoryException;
import charlies.sections.QuizzSection;

public class GeneratorManager {
	
	private SectionGenerator generator;
	
	public List<QuizzSection> generate(int nb, String category) throws UnknownCategoryException{
		switch (category){
			case "scientist" :
				generator = new ScientistsGenerator();
				break;
			case "monuments" :
				generator = new MonumentsGenerator();
				break;
			default :
		  		throw new UnknownCategoryException();
		}
		return generator.generate(nb);
	}
	
}
