package charlies.datastore;

import java.util.ArrayList;
import java.util.List;

import charlies.entities.Score;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class DatastoreManager {

	DatastoreService datastore;
	
	public DatastoreManager(){
		 datastore = DatastoreServiceFactory.getDatastoreService();;
	}
	
	public void insertScore(Score s){
		Entity e = new Entity("Score");
		e.setProperty("category", s.getCategory());
		e.setProperty("pic", s.getPic());
		e.setProperty("name", s.getName());
		e.setProperty("score", s.getScore());
		datastore.put(e);
	}

	public List<Score> listHighscores() {
		List<Score> hs = new ArrayList<Score>();
		Query q = new Query("Score").addSort("score", SortDirection.DESCENDING);;
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		int i=0;
		for (Entity e : results){
			if (i<10){
				String category = (String) e.getProperty("category");
				String name = (String) e.getProperty("name");
				String pic = (String) e.getProperty("pic");
				long score = (long) e.getProperty("score");
				hs.add(new Score(category, name, pic, score));
				++i;
			} else {
				break;
			}
		}
		
		return hs;
	}

	public List<Score> listHighscores(String cat) {
		List<Score> hs = new ArrayList<Score>();
		Filter catFilter = new FilterPredicate("category", FilterOperator.EQUAL, cat);
		Query q = new Query("Score").setFilter(catFilter).addSort("score", SortDirection.DESCENDING);;
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		int i=0;
		for (Entity e : results){
			if (i<10){
				String category = (String) e.getProperty("category");
				String name = (String) e.getProperty("name");
				String pic = (String) e.getProperty("pic");
				long score = (long) e.getProperty("score");
				hs.add(new Score(category, name, pic, score));
				++i;
			} else {
				break;
			}
		}
		
		return hs;
	}
	
	/*public int getBetterPlace(String name){
		return 14;
	}
	
	public Score getBetterScore(String name){
		return new Score("athletes", "carat5", pic, 42);
	}
	
	*/
	
}
