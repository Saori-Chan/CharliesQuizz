package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.exceptions.NoResultException;
import charlies.exceptions.UnknownCategoryException;
import charlies.sections.QuizzSection;

public class GeneratorManager {
	
	private SectionGenerator generator;
	private List<String> categories;
	
	public GeneratorManager() {
		this.categories = new ArrayList<String>();
		this.categories.add("scientists");
		this.categories.add("battles");
		this.categories.add("athletes");
	}

	public List<QuizzSection> generate(int nb, String category, int nbAnswers) throws UnknownCategoryException, NoResultException{
		switch (category){
			case "scientists" :
				generator = new ScientistsGenerator(nbAnswers);
				break;
			case "battles" :
				generator = new BattlesGenerator(nbAnswers);
				break;
			case "athletes" :
				generator = new AthletesGenerator(nbAnswers);
				break;
			default :
		  		throw new UnknownCategoryException();
		}
		return generator.generate(nb);
	}
	
	public List<String> getCategories(){
		return this.categories;
	}
	
}
