package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.exceptions.UnknownCategoryException;
import charlies.sections.QuizzSection;

public class GeneratorManager {
	
	private SectionGenerator generator;
	private List<String> categories;
	
	public GeneratorManager() {
		this.categories = new ArrayList<String>();
		this.categories.add("scientists");
		this.categories.add("monuments");
	}

	public List<QuizzSection> generate(int nb, String category) throws UnknownCategoryException{
		switch (category){
			case "scientists" :
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
	
	public List<String> getCategories(){
		return this.categories;
	}
	
}
