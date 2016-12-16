package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.datastore.DatastoreManager;
import charlies.entities.Scientist;
import charlies.exceptions.NoResultException;
import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

public class ScientistsGenerator extends SectionGenerator {

	public ScientistsGenerator(int nb, DatastoreManager datastore){
		super(nb, datastore);
	}
	
	public List<QuizzSection> generate(int nb) throws NoResultException {
				
		List<QuizzSection> list = new ArrayList<QuizzSection>();
		
		//String pic;
		List<String> answersWho;
		List<String> answersWhen;
		List<String> answersWhere;
		List<Scientist> scientists = datastore.listScientists();
		int r, r1, r2;
		
		for (int i=0; i<nb; ++i){
			answersWho = new ArrayList<String>();
			answersWhen = new ArrayList<String>();
			answersWhere = new ArrayList<String>();
			
			r = (int)(Math.random() * scientists.size());
			Scientist s = scientists.get(r);
			
			r1 = (int)(Math.random() * scientists.size());
			r2 = (int)(Math.random() * scientists.size());
			Scientist s1 = scientists.get(r1);
			Scientist s2 = scientists.get(r2);
			while ((r == r1) 
					|| (r == r2) 
					|| (s.getName().equals(s1.getName()))
					|| (s.getName().equals(s2.getName()))
					|| (s.getBirth().equals(s1.getBirth()))
					|| (s.getBirth().equals(s2.getBirth()))
			){
				r1 = (int)(Math.random() * scientists.size());
				r2 = (int)(Math.random() * scientists.size());
				s1 = scientists.get(r1);
				s2 = scientists.get(r2);
			}
			
			answersWho.add(s.getName());
			answersWho.add(s1.getName());
			answersWho.add(s2.getName());
			answersWhen.add(s.getBirth());
			answersWhen.add(s1.getBirth());
			answersWhen.add(s2.getBirth());
			answersWhere.add(s.getPlace());
			
			list.add(new QuizzSectionPerson(s.getPic(), answersWho, answersWhen, answersWhere, null, s.getAbst(), s.getLink()));
		}
		
		return list;
	}

}
