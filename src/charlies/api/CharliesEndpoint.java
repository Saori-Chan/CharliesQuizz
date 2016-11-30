package charlies.api;

import java.util.ArrayList;
import java.util.List;

import charlies.datastore.DatastoreManager;
import charlies.entities.Highscores;
import charlies.entities.Score;
import charlies.exceptions.NoResultException;
import charlies.exceptions.UnknownCategoryException;
import charlies.generators.GeneratorManager;
import charlies.sections.QuizzSection;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.DefaultValue;
import com.google.api.server.spi.config.Named;

@Api(name = "charlies", version="v1")
public class CharliesEndpoint {

	private GeneratorManager generator = new GeneratorManager();
	private DatastoreManager manager = new DatastoreManager();
	
	@ApiMethod(path="/highscores")
	public Highscores getHighscores(@Named("category") @DefaultValue("") String category) {
		
		List<Score> highscores = new ArrayList<Score>();
		Score scorePlayer;
		int place;
		
		if (category.equals("")){
			highscores = manager.listHighscores();
			place = manager.getBetterPlace("Antoine CARAT");
		} else {
			highscores = manager.listHighscores(category);
			place = manager.getBetterPlace("Antoine CARAT", category);
		}
		scorePlayer = manager.getBetterScore("Antoine CARAT");
		
		return new Highscores(highscores,place,scorePlayer);
	}
		
	@ApiMethod(path="/questions")
	public List<QuizzSection> listQuestions(@Named("category") @DefaultValue("") String category, @Named("number") @DefaultValue("3") int number, @Named("nbAnswsers") @DefaultValue("3") int nbAnswers) throws UnknownCategoryException, NoResultException{
		List<QuizzSection> sections = generator.generate(number,category, nbAnswers);
		
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
	public void insertScore(@Named("category") String category, @Named("pic") String pic, @Named("name") String name, @Named("score") int score){
		Score s = new Score(category, name, pic, score);
		manager.insertScore(s);
	}
	
	@ApiMethod(path="/categories")
	public List<String> listCategories(){
		return generator.getCategories();
	}

}
