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

	public List<QuerySolution> getScientists() throws NoResultException {
		this.request = "PREFIX dbr: <http://dbpedia.org/resource/>" +
					   "PREFIX dbp: <http://dbpedia.org/property/>" +
					   "PREFIX dbo: <http://dbpedia.org/ontology/>" +
					   "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
					   "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
					   "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
					   "SELECT distinct (STR(?n) AS ?name) (STR(?nat) AS ?c) (STR(?abstract) AS ?abst) ?link ?b ?pic ?p" +
						"WHERE {" +
						"	?p a dbo:Scientist;" +
						"   	dbp:name ?n;" +
						"		dbp:field dbr:Computer_science;" +
						"		dbo:birthDate ?b;" +
						"		dbo:birthPlace ?country;" +
						"		dbo:thumbnail ?pic;" +
						"       rdfs:comment ?abstract;" +
						"       foaf:isPrimaryTopicOf ?link." +
						"	?country a dbo:Country;" +
						"    	  	 dbp:commonName ?nat ." +
   						" 	FILTER ( lang(?abstract) = \"en\" )" +
						"	FILTER NOT EXISTS {" +
						"		?p a dbo:Scientist;" +
						"   	   dbp:name ?n;" +
						"   	   dbp:field dbr:Computer_science ." +
						"		FILTER REGEX(?n, ',')" +
						"	}" +
						"	FILTER REGEX(?b,'-')" +
						"}" ;
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
	
	public List<QuerySolution> getBattles() throws NoResultException {
		this.request = "PREFIX dbr: <http://dbpedia.org/resource/>" +
					   "PREFIX dbp: <http://dbpedia.org/property/>" +
					   "PREFIX dbo: <http://dbpedia.org/ontology/>" +
					   "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
					   "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
					   "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
					   "SELECT DISTINCT ?pic (STR(?n) AS ?nameBattle) ?dateD (STR(?c) AS ?coun) (STR(?com) AS ?comm) (STR(?abstract) AS ?abst) ?link  ?p" +
					   "WHERE {" +
					   "?p a dbo:MilitaryConflict;" +
					   "   dbo:thumbnail ?pic;" +
					   "   dbp:conflict ?n;" +
					   "   dbo:date ?dateD;" +
					   "   dbp:place ?country;" +
					   "   dbo:commander ?commandant;" +
					   "   rdfs:comment ?abstract;" +
					   "   foaf:isPrimaryTopicOf ?link." +
					   "?country a dbo:Country;" +
					   "         dbp:commonName ?c." +
					   "?commandant dbp:name ?com." +
					   "FILTER ( lang(?abstract) = \"en\" )" +
					   "FILTER NOT EXISTS {" +
					   "   ?p a dbo:MilitaryConflict;" +
					   "      dbo:date ?dateD." +
					   "   FILTER regex(?dateD,'--')" +
					   "}" +
					   "FILTER (?dateD > '1700-01-01'^^xsd:date)" +
					   "}" +
					   "ORDER BY ?dateD";
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
	
	public List<QuerySolution> getAthletes() throws NoResultException {
		this.request =  "PREFIX dbr: <http://dbpedia.org/resource/>" +
						"PREFIX dbp: <http://dbpedia.org/property/>" +
						"PREFIX dbo: <http://dbpedia.org/ontology/>" +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
						"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
				   		"SELECT DISTINCT (STR(?n) AS ?name) ?date (STR(?nat) AS ?c) (STR(?abstract) AS ?abst) ?link ?pic ?p" +
					    "WHERE {" +
						"{" + 
						"   ?p a dbo:Athlete;" +
						"  	   dbp:name ?n;" +
						"	   dbp:birthDate ?date;" +
						"	   dbp:birthPlace ?country;" +
						" 	   dbo:thumbnail ?pic;" +
						"      rdfs:comment ?abstract;" +
						"      foaf:isPrimaryTopicOf ?link." +
						"   ?x dbp:gold ?p." +
						"   ?country a dbo:Country;" +
						"            dbp:commonName ?nat." +
						" 	FILTER ( lang(?abstract) = \"en\" )" +
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
		while (results.hasNext()){
			solutions.add(results.next());
		}
		qexec.close();
		return solutions;
	}

}
