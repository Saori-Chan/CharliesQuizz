package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.datastore.DatastoreManager;
import charlies.entities.Athlete;
import charlies.exceptions.NoResultException;
import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

public class AthletesGenerator extends SectionGenerator {

	public AthletesGenerator(int nb, DatastoreManager datastore){
		super(nb, datastore);
	}
	
	public List<QuizzSection> generate(int nb) throws NoResultException {
				
		List<QuizzSection> list = new ArrayList<QuizzSection>();
		
		//String pic;
		List<String> answersWho;
		List<String> answersWhen;
		List<String> answersWhere;
		List<Athlete> athletes = datastore.listAthletes();
		int r, r1, r2;
		
		for (int i=0; i<nb; ++i){
			answersWho = new ArrayList<String>();
			answersWhen = new ArrayList<String>();
			answersWhere = new ArrayList<String>();
			
			r = (int)(Math.random() * athletes.size());
			Athlete a = athletes.get(r);
			
			r1 = (int)(Math.random() * athletes.size());
			r2 = (int)(Math.random() * athletes.size());
			Athlete a1 = athletes.get(r1);
			Athlete a2 = athletes.get(r2);
			while ((r == r1) 
					|| (r == r2) 
					|| (a.getName().equals(a1.getName()))
					|| (a.getName().equals(a2.getName()))
					|| (a.getBirth().equals(a1.getBirth()))
					|| (a.getBirth().equals(a2.getBirth()))
			){
				r1 = (int)(Math.random() * athletes.size());
				r2 = (int)(Math.random() * athletes.size());
				a1 = athletes.get(r1);
				a2 = athletes.get(r2);
			}
			
			answersWho.add(a.getName());
			answersWho.add(a1.getName());
			answersWho.add(a2.getName());
			answersWhen.add(a.getBirth());
			answersWhen.add(a1.getBirth());
			answersWhen.add(a2.getBirth());
			answersWhere.add(a.getPlace());
			
			list.add(new QuizzSectionPerson(a.getPic(), answersWho, answersWhen, answersWhere, null, a.getAbst(), a.getLink()));
		}
		
		return list;
	}
}
