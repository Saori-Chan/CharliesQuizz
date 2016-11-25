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

	private List<Score> highscores;
	private GeneratorManager generator = new GeneratorManager();
	private DatastoreManager manager = new DatastoreManager();
	
	@ApiMethod(path="/highscores")
	public Highscores getHighscores(@Named("category") @DefaultValue("") String category) {
		
		String pic = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg";
		
		List<Score> highscores = new ArrayList<Score>();
		Score scorePlayer = new Score("athletes", "carat5", pic, 42);
		int place = 14;
		
//		if (category.equals("")){
			highscores = manager.listHighscores();
//		} else {
//			highscores = manager.listHighscores(category);
//		}
		/*place = manager.getBetterPlace(name);
		scorePlayer = manager.getBetterScore(name);
		*/
		/*highscores.add(new Score("scientists", "carat", pic, 4200));
		highscores.add(new Score("scientists", "carat1", pic, 4200));
		highscores.add(new Score("battles", "carat2", pic, 4200));
		highscores.add(new Score("battles", "carat3", pic, 4200));
		highscores.add(new Score("athletes", "carat4", pic, 4200));
		highscores.add(new Score("athletes", "carat5", pic, 4200));*/
		
		Highscores scores = new Highscores(highscores,14,scorePlayer);
		
		return scores;
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
		Score s = new Score(category, pic, name, score);
		manager.insertScore(s);
	}
	
	@ApiMethod(path="/categories")
	public List<String> listCategories(){
		return generator.getCategories();
	}

}
