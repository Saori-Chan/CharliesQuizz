package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import charlies.sections.QuizzSection;

public abstract class SectionGenerator {

	protected String request;
	
	public SectionGenerator() {
		this.request = "PREFIX dbr: <http://dbpedia.org/resource/>" +
						"PREFIX dbp: <http://dbpedia.org/property/>" +
						"PREFIX dbo: <http://dbpedia.org/ontology/>";
	}

	public abstract List<QuizzSection> generate(int nb);
	
	public List<QuerySolution> executeRequest(){
		List<QuerySolution> solutions = new ArrayList<QuerySolution>();
		Query query = QueryFactory.create(request.toString());
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = qexec.execSelect();
		while (results.hasNext()){
			solutions.add(results.next());
		}
		qexec.close();
		return solutions;
	}

}
