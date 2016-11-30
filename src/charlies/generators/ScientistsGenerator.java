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
		
		String pic;
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
			r1 = (int)(Math.random() * scientists.size());
			r2 = (int)(Math.random() * scientists.size());
			while (!(r != r1) && (r != r2)){
				r1 = (int)(Math.random() * scientists.size());
				r2 = (int)(Math.random() * scientists.size());
			}
			
			Scientist s = scientists.get(r);
			Scientist s1 = scientists.get(r1);
			Scientist s2 = scientists.get(r2);
			answersWho.add(s.getName());
			answersWho.add(s1.getName());
			answersWho.add(s2.getName());
			answersWhen.add(s.getBirth());
			answersWhen.add(s1.getBirth());
			answersWhen.add(s2.getBirth());

			list.add(new QuizzSectionPerson(s.getPic(), answersWho, answersWhen, answersWhere));
		}
		
		/*for (int i=0; i<nb; ++i){
			answersWho = new ArrayList<String>();
			answersWhen = new ArrayList<String>();
			answersWhere = new ArrayList<String>();
			
			//Good answer
			int r = (int)(Math.random() * solutions.size());
			QuerySolution sol = solutions.get(r);
			answersWho.add(sol.getLiteral("name").toString());
			answersWhen.add(sol.getLiteral("birth").toString());
			String location = super.fixLocation(sol.getLiteral("nat").toString());
			answersWhere.add(location);
			String pic = sol.getResource("pic").toString();
			
			//Bad answers
			List<Integer> picked = new ArrayList<Integer>();
			picked.add(r);
			for (int j=0; j<nbAnswers-1; ++j){
				int badR = (int)(Math.random() * solutions.size());
				while (picked.contains(badR)){
					badR = (int)(Math.random() * solutions.size());
				}
				QuerySolution badSol = solutions.get(badR);
				answersWho.add(badSol.getLiteral("name").toString());
				answersWhen.add(badSol.getLiteral("birth").toString());
			}
			
			list.add(new QuizzSectionPerson(pic , answersWho, answersWhen, answersWhere));
		}*/
		
		return list;
	}

}
