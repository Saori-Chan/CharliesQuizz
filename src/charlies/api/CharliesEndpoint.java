package charlies.api;

import java.util.ArrayList;
import java.util.List;

import charlies.entities.Score;
import charlies.exceptions.UnknownCategoryException;
import charlies.generators.GeneratorManager;
import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.DefaultValue;
import com.google.api.server.spi.config.Named;

@Api(name = "charlies", version="v1")
public class CharliesEndpoint {

	private List<Score> highscores;
	private GeneratorManager generator = new GeneratorManager();
	
	@ApiMethod(path="/highscores")
	public List<Score> listHighscores(@Named("category") @DefaultValue("") String category) {
		this.highscores = load();
		if (category.equals("")){
			return highscores;
		}
		List<Score> hs = new ArrayList<Score>();
		for (Score s : highscores){
			if (category.equals(s.getCategory())){
				hs.add(s);
			}
		}
		return hs;
	}
	
	public List<Score> load(){
		//TODO use a "loadFromDatastore" function/request.
		List<Score> hs = new ArrayList<Score>();
		hs.add(new Score("scientist", "michel", 152));
		hs.add(new Score("monuments", "carat", 158));
		hs.add(new Score("scientist", "mael", 42));
		return hs;
	}
		
	@ApiMethod(path="/questions")
	public List<QuizzSection> listQuestions(@Named("category") @DefaultValue("") String category, @Named("number") @DefaultValue("3") int number) throws UnknownCategoryException{
		List<QuizzSection> sections = generator.generate(number,category);
		
		/*String pic = "https://commons.wikimedia.org/wiki/Special:FilePath/Alan_Turing_Aged_16.jpg?with=300";
		List<String> whoAns = new ArrayList<String>();
		whoAns.add("Alan Turing");
		whoAns.add("Donald Knuth");
		whoAns.add("Ron Rivest");
		
		List<String> whenAns = new ArrayList<String>();
		whenAns.add("1912");
		whenAns.add("1942");
		whenAns.add("1984");
		
		List<String> whereAns = new ArrayList<String>();
		whereAns.add("United Kingdom");
		
		sections.add(new QuizzSectionPerson(pic, whoAns, whenAns, whereAns));*/
		return sections;
	}
	
	@ApiMethod(path="/addscore")
	public void insertScore(@Named("category") String category, @Named("name") String name, @Named("score") int score){
		Score s = new Score(category, name, score);
		this.highscores.add(s);
		//TODO use a "storeInDataStore" function
	}
	
	public List<String> listCategories(){
		return generator.getCategories();
	}

}
