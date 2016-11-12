package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.hp.hpl.jena.query.QuerySolution;

import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

public class AthletesGenerator extends SectionGenerator {

	public AthletesGenerator(){
		super();
		this.request += "select DISTINCT ?name ?date ?c ?pic ?p" +
						"where {" +
						"{" + 
						"   ?p a dbo:Athlete;" +
						"  	   dbp:name ?name;" +
						"	   dbp:birthDate ?date;" +
						"	   dbp:birthPlace ?country;" +
						" 	   dbo:thumbnail ?pic." +
						"   ?x dbp:gold ?p." +
						"   ?country a dbo:Country;" +
						"            dbp:commonName ?c." +
						"   FILTER NOT EXISTS {" +
						"      ?p a dbo:Athlete;" +
						"         dbp:name ?name." +
						"      ?x dbp:gold ?p." +
						"      FILTER regex(?name,',')" +
						"	}" +
						"}" +
						"FILTER NOT EXISTS {" +
						"   ?p a dbo:Athlete;" +
						"      dbp:birthDate ?date." +
						"   ?x dbp:gold ?p." +
						"   FILTER regex(?date,'--')" +
						"}" +
						"}"; 		
	}
	
	public List<QuizzSection> generate(int nb) {
		List<QuizzSection> list = new ArrayList<QuizzSection>();
		List<QuerySolution> solutions = executeRequest();
		if (solutions.size() < 1) {
			throw new NoResultException();
		}
		List<String> answersWho;
		List<String> answersWhen;
		List<String> answersWhere;
		
		QuerySolution sol, sol1, sol2;
		int r, r1, r2;
		
		for (int i=0; i<nb; ++i){
			answersWho = new ArrayList<String>();
			answersWhen = new ArrayList<String>();
			answersWhere = new ArrayList<String>();
			r = (int)(Math.random() * solutions.size());
			r1 = (int)(Math.random() * solutions.size());
			r2 = (int)(Math.random() * solutions.size());
			while (!(r != r1) && (r != r2)){
				r1 = (int)(Math.random() * solutions.size());
				r2 = (int)(Math.random() * solutions.size());
			}
			sol = solutions.get(r);
			sol1 = solutions.get(r1);
			sol2 = solutions.get(r2);
			answersWho.add(sol.getLiteral("name").toString());
			answersWho.add(sol1.getLiteral("name").toString());
			answersWho.add(sol2.getLiteral("name").toString());
			answersWhen.add(sol.getLiteral("date").toString());
			answersWhen.add(sol1.getLiteral("date").toString());
			answersWhen.add(sol2.getLiteral("date").toString());
			
			String location = sol.getLiteral("c").toString();
			switch (location){
				
			}
			answersWhere.add(location);
			list.add(new QuizzSectionPerson(sol.getResource("pic").toString(), answersWho, answersWhen, answersWhere));
		}
		return list;
	}

}
