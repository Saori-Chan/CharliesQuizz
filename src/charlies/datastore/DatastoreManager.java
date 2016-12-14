package charlies.datastore;

import java.util.ArrayList;
import java.util.List;

import charlies.entities.Athlete;
import charlies.entities.Battle;
import charlies.entities.Scientist;
import charlies.entities.Score;
import charlies.generators.SparqlService;

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
	SparqlService sparql;
	
	public DatastoreManager(){
		 datastore = DatastoreServiceFactory.getDatastoreService();
		 sparql = new SparqlService();
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
		
		for (Entity e : results){
			String category = (String) e.getProperty("category");
			String name = (String) e.getProperty("name");
			String pic = (String) e.getProperty("pic");
			long score = (long) e.getProperty("score");
			hs.add(new Score(category, name, pic, score));
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
	
	public int getBetterPlace(String nameP){
		int place = 0;
		List<Score> hs = new ArrayList<Score>();
		Query q = new Query("Score").addSort("score", SortDirection.DESCENDING);;
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(10));
		
		for (Entity e : results){
			String Pname = (String) e.getProperty("name");
			if (Pname.equals(nameP)){
				return place+1;
			} else {
				++place;
			}
		}
		return place;
	}
	
	public int getBetterPlace(String nameP, String cat){
		int place = 0;
		List<Score> hs = new ArrayList<Score>();
		Filter catFilter = new FilterPredicate("category", FilterOperator.EQUAL, cat);
		Query q = new Query("Score").setFilter(catFilter).addSort("score", SortDirection.DESCENDING);;
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(10));
		
		for (Entity e : results){
			String Pname = (String) e.getProperty("name");
			if (Pname.equals(nameP)){
				return place+1;
			} else {
				++place;
			}
		}
		
		return place;
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

	public void fillScientists(List<Scientist> scientists) {
		Entity e;
		for (Scientist s : scientists){
			e = new Entity("Scientist");
			e.setProperty("pic", s.getPic());
			e.setProperty("name", s.getName());
			e.setProperty("birth", s.getBirth());
			e.setProperty("place", s.getPlace());
			datastore.put(e);			
		}
	}

	public void fillBattles(List<Battle> battles) {
		Entity e;
		for (Battle b : battles){
			e = new Entity("Battle");
			e.setProperty("pic", b.getPic());
			e.setProperty("commanders", b.getCommanders());
			e.setProperty("date", b.getDate());
			e.setProperty("place", b.getPlace());
			datastore.put(e);			
		}
	}

	public void fillAthletes(List<Athlete> athletes) {
		Entity e;
		for (Athlete a : athletes){
			e = new Entity("Athlete");
			e.setProperty("pic", a.getPic());
			e.setProperty("name", a.getName());
			e.setProperty("birth", a.getBirth());
			e.setProperty("place", a.getPlace());
			datastore.put(e);			
		}
	}

	public List<Scientist> listScientists() {
		List<Scientist> scientists = new ArrayList<Scientist>();
		Query q = new Query("Scientist");
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		for (Entity e : results){
			String pic = (String) e.getProperty("pic");
			String name = (String) e.getProperty("name");
			String birth = (String) e.getProperty("birth");
			String place = (String) e.getProperty("place");
			scientists.add(new Scientist(pic, name, birth, place));
		}
		
		return scientists;
	}

	public List<Battle> listBattles() {
		List<Battle> battles = new ArrayList<Battle>();
		Query q = new Query("Battle");
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		for (Entity e : results){
			String pic = (String) e.getProperty("pic");
			String name = (String) e.getProperty("commanders");
			String birth = (String) e.getProperty("dates");
			String place = (String) e.getProperty("place");
			battles.add(new Battle(pic, name, birth, place));
		}
		
		return battles;
	}
	
	public List<Athlete> listAthletes() {
		List<Athlete> athletes = new ArrayList<Athlete>();
		Query q = new Query("Athlete");
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		for (Entity e : results){
			String pic = (String) e.getProperty("pic");
			String name = (String) e.getProperty("name");
			String birth = (String) e.getProperty("birth");
			String place = (String) e.getProperty("place");
			athletes.add(new Athlete(pic, name, birth, place));
		}
		
		return athletes;
	}
		
}
