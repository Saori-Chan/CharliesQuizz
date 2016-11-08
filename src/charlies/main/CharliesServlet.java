package charlies.main;

import java.io.IOException;

import javax.servlet.http.*;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class CharliesServlet {
	
	//Get a row
	private QuerySolution getOne(){
		String queryString = 
				"PREFIX dbr: <http://dbpedia.org/resource/>" +
				"PREFIX dbp: <http://dbpedia.org/property/>" +
				"PREFIX dbo: <http://dbpedia.org/ontology/>" +
				"select distinct ?birth ?pic ?name ?nat ?p" +
				"where {" +
				"?p a dbo:Scientist;" +
				"   dbp:name ?name;" +
				"   dbp:field dbr:Computer_science;" +
				"   dbo:birthDate ?birth;" +
				"   dbo:birthPlace ?nat;" +
				"   dbo:thumbnail ?pic." +
				"}";
		
		
		
		Query query = QueryFactory.create(queryString);
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		
		ResultSet results = qexec.execSelect();
		return results.next();
	}
	
	public String generate() {
		QuerySolution sol = getOne();
		
		String ret = "<img src=\"" + sol.getResource("pic") + "\"/>" ;
		ret += whoQuestion() + "</br>";
		ret += sol.getLiteral("name");
		
		return ret;
	}

	//Utile quand on aura mis l'héritage en place
	private String whoQuestion() {
		return "Who is it ?";
	}

	public QuerySolution doQuery() {
		
		String ret = "<table>";
		//TODO : trouver pourquoi la dernière variable de la requête est à null
		//TMPFIX : ajout de ?p pour pallier au problème
		String queryString = 
				"PREFIX dbr: <http://dbpedia.org/resource/>" +
				"PREFIX dbp: <http://dbpedia.org/property/>" +
				"PREFIX dbo: <http://dbpedia.org/ontology/>" +
				"select distinct ?birth ?pic ?name ?nat ?p" +
				"where {" +
				"?p a dbo:Scientist;" +
				"   dbp:name ?name;" +
				"   dbp:field dbr:Computer_science;" +
				"   dbo:birthDate ?birth;" +
				"   dbo:birthPlace ?nat;" +
				"   dbo:thumbnail ?pic." +
				"}";
		
		
		Query query = QueryFactory.create(queryString);
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		
		try {
			ResultSet results = qexec.execSelect();
			return results.next();
		}
		finally {
			qexec.close();
		}
	}
}
