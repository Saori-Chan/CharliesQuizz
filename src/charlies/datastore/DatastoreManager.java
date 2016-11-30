package charlies.datastore;

import java.util.ArrayList;
import java.util.List;

import charlies.entities.Score;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
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
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(10));
		
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
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(10));
		
		for (Entity e : results){
			String category = (String) e.getProperty("category");
			String name = (String) e.getProperty("name");
			String pic = (String) e.getProperty("pic");
			long score = (long) e.getProperty("score");
			hs.add(new Score(category, name, pic, score));
		}
		
		return hs;
	}
	
	public int getBetterPlace(String name){
		return 14;
	}
	
	public int getBetterPlace(String name, String cat){
		return 42;
	}
	
	public Score getBetterScore(String pName){
		Filter nameFilter = new FilterPredicate("name", FilterOperator.EQUAL, pName);
		Query q = new Query("Score").setFilter(nameFilter).addSort("score", SortDirection.DESCENDING);;
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		
		Entity e = results.get(0);
		String category = (String) e.getProperty("category");
		String name = (String) e.getProperty("name");
		String pic = (String) e.getProperty("pic");
		long score = (long) e.getProperty("score");
		Score s = new Score(category, name, pic, score);
		
		return s;
	}
	
	public Score getBetterScore(String pName, String cat){
		Filter nameFilter = new FilterPredicate("name", FilterOperator.EQUAL, pName);
		Filter catFilter = new FilterPredicate("category", FilterOperator.EQUAL, cat);
		Filter duoFilter = CompositeFilterOperator.and(nameFilter,catFilter);
		Query q = new Query("Score").setFilter(duoFilter).addSort("score", SortDirection.DESCENDING);;
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		
		Entity e = results.get(0);
		String category = (String) e.getProperty("category");
		String name = (String) e.getProperty("name");
		String pic = (String) e.getProperty("pic");
		long score = (long) e.getProperty("score");
		Score s = new Score(category, name, pic, score);
		
		return s;
	}
	
	
}
