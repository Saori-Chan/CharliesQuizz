package charlies.datastore;

import charlies.entities.Score;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

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

	/*public List<Score> listHighscores() {
		List<Score> hs = new ArrayList<Score>();
		String r = "SELECT * FROM Score";
		Query<Entity> query = Query.entityQueryBuilder().kind("Score").build();
		QueryResults<Entity> results = datastore.run(query);
		while (results.hasNext()) {
			Entity entity = results.next();
		    hs.add(new Score(entity.getString("category"),
		    		entity.getString("name"),
		    		entity.getString("pic"),
		    		entity.getLong("score")));
		}
		return hs;
	}

	public List<Score> listHighscores(String category) {
		List<Score> hs = new ArrayList<Score>();
		String r = "SELECT * FROM Score";
		Query<Entity> query = Query.entityQueryBuilder().kind("Score").filter(category).build();
		QueryResults<Entity> results = datastore.run(query);
		while (results.hasNext()) {
			Entity entity = results.next();
		    hs.add(new Score(entity.getString("category"),
		    		entity.getString("name"),
		    		entity.getString("pic"),
		    		entity.getLong("score")));
		}
		return hs;
	}*/
	
}
