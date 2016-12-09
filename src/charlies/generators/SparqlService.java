package charlies.generators;

import java.util.ArrayList;
import java.util.List;

import charlies.exceptions.NoResultException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class SparqlService {
	
	private String request;

	public List<QuerySolution> getScientists(int number) throws NoResultException {
		this.request = "PREFIX dbr: <http://dbpedia.org/resource/>" +
					   "PREFIX dbp: <http://dbpedia.org/property/>" +
					   "PREFIX dbo: <http://dbpedia.org/ontology/>" +
					   "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
					   "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
					   "SELECT distinct (STR(?n) AS ?name) (STR(?nat) AS ?c) ?b ?pic ?p" +
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
						"}" +
						"LIMIT" + number;
		List<QuerySolution> solutions = new ArrayList<QuerySolution>();
		Query query = QueryFactory.create(request.toString());
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = qexec.execSelect();
		if (!results.hasNext()){
			throw new NoResultException();
		}
		while (results.hasNext()){
			solutions.add(results.next());
		}
		qexec.close();
		return solutions;
	}
	
	public List<QuerySolution> getBattles(int number) throws NoResultException {
		this.request = "PREFIX dbr: <http://dbpedia.org/resource/>" +
					   "PREFIX dbp: <http://dbpedia.org/property/>" +
					   "PREFIX dbo: <http://dbpedia.org/ontology/>" +
					   "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
					   "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
					   "SELECT ?pic (STR(?n) AS ?nameBattle) ?date (STR(?c) AS ?coun) (STR(?com) AS ?comm)  ?p" +
					   "WHERE {" +
					   "?p a dbo:MilitaryConflict;" +
					   "   dbo:thumbnail ?pic;" +
					   "   dbp:conflict ?n;" +
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
					   "}" +
					   "LIMIT" + number;
	List<QuerySolution> solutions = new ArrayList<QuerySolution>();
	Query query = QueryFactory.create(request.toString());
	QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	ResultSet results = qexec.execSelect();
	if (!results.hasNext()){
		throw new NoResultException();
	}
	while (results.hasNext()){
		solutions.add(results.next());
	}
	qexec.close();
	return solutions;
	}
	
	public List<QuerySolution> getAthletes(int number) throws NoResultException {
		this.request =  "PREFIX dbr: <http://dbpedia.org/resource/>" +
						"PREFIX dbp: <http://dbpedia.org/property/>" +
						"PREFIX dbo: <http://dbpedia.org/ontology/>" +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
				   		"SELECT DISTINCT (STR(?n) AS ?name) ?date (STR(?nat) AS ?c) ?pic ?p" +
					    "WHERE {" +
						"{" + 
						"   ?p a dbo:Athlete;" +
						"  	   dbp:name ?n;" +
						"	   dbp:birthDate ?date;" +
						"	   dbp:birthPlace ?country;" +
						" 	   dbo:thumbnail ?pic." +
						"   ?x dbp:gold ?p." +
						"   ?country a dbo:Country;" +
						"            dbp:commonName ?nat." +
						"   FILTER NOT EXISTS {" +
						"      ?p a dbo:Athlete;" +
						"         dbp:name ?n." +
						"      ?x dbp:gold ?p." +
						"      FILTER regex(?n,',')" +
						"	}" +
						"}" +
						"FILTER NOT EXISTS {" +
						"   ?p a dbo:Athlete;" +
						"      dbp:birthDate ?date." +
						"   ?x dbp:gold ?p." +
						"   FILTER regex(?date,'--')" +
						"}" +
						"}";
		
		List<QuerySolution> solutions = new ArrayList<QuerySolution>();
		Query query = QueryFactory.create(request.toString());
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		ResultSet results = qexec.execSelect();
		if (!results.hasNext()){
			throw new NoResultException();
		}
		while (results.hasNext()){
			solutions.add(results.next());
		}
		qexec.close();
		return solutions;
	}

}
