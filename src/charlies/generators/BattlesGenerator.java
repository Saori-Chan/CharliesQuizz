package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.datastore.DatastoreManager;
import charlies.entities.Battle;
import charlies.exceptions.NoResultException;
import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

public class BattlesGenerator extends SectionGenerator {
	

	public BattlesGenerator(int nb, DatastoreManager datastore){
		super(nb, datastore);
	}
	
	public List<QuizzSection> generate(int nb) throws NoResultException {
				
		List<QuizzSection> list = new ArrayList<QuizzSection>();
		
		//String pic;
		List<String> answersWho;
		List<String> answersWhen;
		List<String> answersWhere;
		List<Battle> battles = datastore.listBattles();
		int r, r1, r2;
		
		for (int i=0; i<nb; ++i){
			answersWho = new ArrayList<String>();
			answersWhen = new ArrayList<String>();
			answersWhere = new ArrayList<String>();
			
			r = (int)(Math.random() * battles.size());
			r1 = (int)(Math.random() * battles.size());
			r2 = (int)(Math.random() * battles.size());
			while (!(r != r1) && (r != r2)){
				r1 = (int)(Math.random() * battles.size());
				r2 = (int)(Math.random() * battles.size());
			}
			
			Battle b = battles.get(r);
			Battle b1 = battles.get(r1);
			Battle b2 = battles.get(r2);
			answersWho.add(b.getCommanders());
			answersWho.add(b1.getCommanders());
			answersWho.add(b2.getCommanders());
			answersWhen.add(b.getDates());
			answersWhen.add(b1.getDates());
			answersWhen.add(b2.getDates());
			answersWhere.add(b.getPlace());
			
			list.add(new QuizzSectionPerson(b.getPic(), answersWho, answersWhen, answersWhere));
		}
		
		return list;
	}
	
}
