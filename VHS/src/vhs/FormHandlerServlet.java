package vhs;

import java.io.*;
import java.util.*;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import javax.servlet.*;
import javax.servlet.http.*;

public class FormHandlerServlet extends javax.servlet.http.HttpServlet {
	String path = "C:\\Users\\Tejashree\\workspace\\VHS\\";

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			PrintWriter out = resp.getWriter();
			SparqlQueryMachine sqm = new SparqlQueryMachine();
			String query = SparqlQueryMachine.commonPrefix;
			String dataset = null;
			HashMap<String, Double> result = null;
			out.println("<html><body> Here are the results.... ");

			if (req.getParameter("state").equals("All States")) {
				if (req.getParameter("area").equals("State")) {
					// show state wise
				} else if (req.getParameter("area").equals("County")) {
					// use county wise dataset

					dataset = "dataset1150.rdf";
					Data1150 dataTypes = new Data1150();
					query += SparqlQueryMachine.dataset1150Prefix
							+ " SELECT ?O ?M " + " WHERE { " 
							+ " ?S " + dataTypes.attributes.get("State") + "  ?O . "
							+ " ?S " + dataTypes.attributes.get("County") + "  ?P . "
							+ " ?P " + dataTypes.attributes.get(req.getParameter("column")) + "  ?M  "											
							+ " } ";
					result = ResultSetAdapter.sum( (ResultSet) sqm.issueSPARQLQuery(query, sqm
							.createModel(path + dataset,
									SparqlQueryMachine.dataset1150NameSpace)) );
				} else if (req.getParameter("area").equals(
						"Congressional District")) {

					dataset = "dataset1151.rdf";
					Data1151 dataTypes = new Data1151();
					query += SparqlQueryMachine.dataset1151Prefix
							+ " SELECT ?O ?M " + " WHERE { " 
							+ " ?S " + dataTypes.attributes.get("State") + "  ?O . "
							+ " ?S " + dataTypes.attributes.get("Congressional District") + "  ?P . "
							+ " ?P " + dataTypes.attributes.get(req.getParameter("column")) + "  ?M  "							
							+ " }  ";
					result = ResultSetAdapter.sum( (ResultSet) sqm.issueSPARQLQuery(query, sqm
							.createModel(path + dataset,
									SparqlQueryMachine.dataset1151NameSpace)) );
				}

			} 
			else  		//For one particular State
			{
				if (req.getParameter("area").equals("County")) {
					// use county wise dataset

					dataset = "dataset1150.rdf";
					Data1150 dataTypes = new Data1150();
					query += SparqlQueryMachine.dataset1150Prefix
							+ " SELECT ?CountyName ?M " + " WHERE { " 
							+ " ?S " + dataTypes.attributes.get("State") + " \""+req.getParameter("state")+"\"  . "
							+ " ?S " + dataTypes.attributes.get("County") + "  ?BlankNode . "
							+ " ?BlankNode " + dataTypes.attributes.get("Name") + " ?CountyName ."
							+ " ?BlankNode " + dataTypes.attributes.get(req.getParameter("column")) + "  ?M . "											
							+ " } ";
					result = ResultSetAdapter.sum( (ResultSet) sqm.issueSPARQLQuery(query, sqm
							.createModel(path + dataset,
									SparqlQueryMachine.dataset1150NameSpace)) );
				} else if (req.getParameter("area").equals(
						"Congressional District")) {

					dataset = "dataset1151.rdf";
					Data1151 dataTypes = new Data1151();
					query += SparqlQueryMachine.dataset1151Prefix
							+ " SELECT ?DistrictName ?M " + " WHERE { " 
							+ " ?S " + dataTypes.attributes.get("State") + " \""+req.getParameter("state")+"\"  . "
							+ " ?S " + dataTypes.attributes.get("Congressional District") + "  ?BlankNode . "
							+ " ?BlankNode " + dataTypes.attributes.get("Name") + " ?DistrictName . "
							+ " OPTIONAL { ?BlankNode " + dataTypes.attributes.get(req.getParameter("column")) + "  ?M } . "											
							+ " } ";
					result = ResultSetAdapter.sum( (ResultSet) sqm.issueSPARQLQuery(query, sqm
							.createModel(path + dataset,
									SparqlQueryMachine.dataset1151NameSpace)) );
				}
			}
			System.out.println(result.keySet().toString() + " " + result.keySet().size());
			// Printing the resultset
			Object [] places = result.keySet().toArray();
			for (int i =  0 ; i < places.length; i++) {
				//create JSON Objects here
				
				System.out.println("<br> " +  (String)places[i]
							+ " " +result.get((String)places[i]) );
			}
			
			// Drawing the Graph from the result HashMap
			if (req.getParameter("graph").equals("Map")) {

			} else if (req.getParameter("graph").equals("Bar chart")) {

			}
			out.println("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
