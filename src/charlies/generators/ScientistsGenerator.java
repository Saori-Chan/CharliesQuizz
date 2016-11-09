package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionPerson;

import com.hp.hpl.jena.query.QuerySolution;

public class ScientistsGenerator extends SectionGenerator {

	public ScientistsGenerator(){
		super();
		this.request += "SELECT distinct ?name ?nat ?birth ?pic ?p" +
						"WHERE {" +
						"	?p a dbo:Scientist;" +
						"   	dbp:name ?name;" +
						"		dbp:field dbr:Computer_science;" +
						"		dbo:birthDate ?birth;" +
						"		dbo:birthPlace ?country;" +
						"		dbo:thumbnail ?pic." +
						"	?country a dbo:Country;" +
						"    	  	 dbp:commonName ?nat." +
						//"	FILTER NOT EXISTS {" +
						//"		?p a dbo:Scientist;" +
						//"   	   dbp:name ?name;" +
						//"   	   dbp:field dbr:Computer_science." +
						//"		FILTER REGEX(?name, ',')" +
						//"	}" +
						//"	FILTER REGEX(?birth,'-')" +
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
		
		QuerySolution sol;
		int r;
		
		for (int i=0; i<nb; ++i){
			answersWho = new ArrayList<String>();
			answersWhen = new ArrayList<String>();
			answersWhere = new ArrayList<String>();
			r = (int)(Math.random() * solutions.size());
			sol = solutions.get(r);
			answersWho.add(sol.getLiteral("name").toString());
			answersWhen.add(sol.getLiteral("birth").toString());
			answersWhere.add(sol.getLiteral("nat").toString());
			list.add(new QuizzSectionPerson(sol.getResource("pic").toString(), answersWho, answersWhen, answersWhere));
		}
		return list;
	}

}
