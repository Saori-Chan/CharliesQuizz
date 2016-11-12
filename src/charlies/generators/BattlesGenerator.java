package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import charlies.sections.QuizzSection;
import charlies.sections.QuizzSectionEvent;

import com.hp.hpl.jena.query.QuerySolution;

public class BattlesGenerator extends SectionGenerator {
	
	public BattlesGenerator() {
		super();
		request += "select ?pic ?nameBattle ?date ?c ?com ?p" +
				   "where {" +
				   "?p a dbo:MilitaryConflict;" +
				   "   dbo:thumbnail ?pic;" +
				   "   dbp:conflict ?nameBattle;" +
				   "   dbo:date ?date;" +
				   "   dbp:place ?country;" +
				   "   dbo:commander ?commandant." +
				   "?country a dbo:Country;" +
				   "         dbp:commonName ?c." +
				   "?commandant dbp:name ?com." +
				   "FILTER NOT EXISTS {" +
				   "   ?p a dbo:MilitaryConflict;" +
				   "      dbo:date ?date." +
				   "   FILTER regex(?date,'--')" +
				   "}" +
				   "FILTER (?date > '1700-01-01'^^xsd:date)" +
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
			answersWho.add(sol.getLiteral("com").toString());
			answersWho.add(sol1.getLiteral("com").toString());
			answersWho.add(sol2.getLiteral("com").toString());
			answersWhen.add(sol.getLiteral("date").toString());
			answersWhen.add(sol1.getLiteral("date").toString());
			answersWhen.add(sol2.getLiteral("date").toString());
			answersWhere.add(sol.getLiteral("c").toString());
			list.add(new QuizzSectionEvent(sol.getResource("pic").toString(), answersWho, answersWhen, answersWhere));
		}
		return list;
	}

}
