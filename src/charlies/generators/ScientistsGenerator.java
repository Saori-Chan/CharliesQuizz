package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.exceptions.NoResultException;

import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

import com.hp.hpl.jena.query.QuerySolution;

public class ScientistsGenerator extends SectionGenerator {

	public ScientistsGenerator(int nbAnswers){
		super(nbAnswers);
		this.request += "SELECT distinct (STR(?n) AS ?name) (STR(?nat) AS ?c) ?b ?pic ?p" +
						"WHERE {" +
						"	?p a dbo:Scientist;" +
						"   	dbp:name ?n;" +
						"		dbp:field dbr:Computer_science;" +
						"		dbo:birthDate ?b;" +
						"		dbo:birthPlace ?country;" +
						"		dbo:thumbnail ?pic ." +
						"	?country a dbo:Country;" +
						"    	  	 dbp:commonName ?nat ." +
						"	FILTER NOT EXISTS {" +
						"		?p a dbo:Scientist;" +
						"   	   dbp:name ?n;" +
						"   	   dbp:field dbr:Computer_science ." +
						"		FILTER REGEX(?n, ',')" +
						"	}" +
						"	FILTER REGEX(?b,'-')" +
						"}";
	}
	
	public List<QuizzSection> generate(int nb) throws NoResultException {
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
			
			answersWhen.add(sol.getLiteral("b").toString());
			answersWhen.add(sol1.getLiteral("b").toString());
			answersWhen.add(sol2.getLiteral("b").toString());
			
			String location = super.fixLocation(sol.getLiteral("c").toString());
			answersWhere.add(location);
			list.add(new QuizzSectionPerson(sol.getResource("pic").toString(), answersWho, answersWhen, answersWhere));
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
