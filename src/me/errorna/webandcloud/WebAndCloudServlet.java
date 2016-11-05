package me.errorna.webandcloud;

import java.io.IOException;
import javax.servlet.http.*;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@SuppressWarnings("serial")
public class WebAndCloudServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		resp.getWriter().println(this.doQuery());
	}
	
	public String doQuery() {
		
		String ret = "";
		
		String queryString = 
				"PREFIX dbr: <http://dbpedia.org/resource/>" +
				"PREFIX dbp: <http://dbpedia.org/property/>" +
				"PREFIX dbo: <http://dbpedia.org/ontology/>" +

				"select distinct ?name ?by ?n" +
				"where {" +
				"	?p a dbo:Scientist;" +
				"	dbp:name ?name;" +
				"	dbp:field dbr:Computer_science;" +
				"	dbo:birthDate ?by;" +
				"	dbo:nationality ?n;" +
				"	dbo:thumbnail ?pic." +
				"} LIMIT 100";
		
		Query query = QueryFactory.create(queryString);
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		
		try {
			ResultSet results = qexec.execSelect();
			
			for(; results.hasNext();) {
				QuerySolution qs = results.next();
				ret += qs.toString() + "<br>";
			}
		}
		finally {
			qexec.close();
		}
		
		return ret;
	}
}
