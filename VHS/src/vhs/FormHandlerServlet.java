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
			String GraphTitle ="";
			String XAxis = "";
			String webPage = "";
			webPage +="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""
					+ " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"> <head>";
			webPage +=" <script type='text/javascript' "
					+ " src='https://www.google.com/jsapi'></script>";
					webPage += "  <script type='text/javascript'> ";
					webPage +="   google.load('visualization', '1', {'packages': ['";
			//selecting graph packages
			
			if(req.getParameter("graph").equals("Map"))
			{
				webPage +="geomap";
			}
			else if (req.getParameter("graph").equals("Bar chart"))
			{
				webPage +="corechart";
			}
			webPage +="']}); ";
			webPage +="google.setOnLoadCallback(drawMap); "
					+ "    function drawMap() { "
					+ "      var data = google.visualization.arrayToDataTable(["
					+ "        ['State', '"+req.getParameter("column")+"'], ";
			

			if (req.getParameter("state").equals("All States")) {
				GraphTitle = req.getParameter("column") + " for " + " United States ";
				XAxis = "State";
				if (req.getParameter("area").equals("County")) {
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
				GraphTitle =  req.getParameter("column") + " for " + req.getParameter("state") ;
				if (req.getParameter("area").equals("County")) {
					// use county wise dataset
					XAxis = "County for " + req.getParameter("state");
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
					XAxis = "Congressional District " + req.getParameter("state");
					
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
			// Printing the resultset
			Object [] places = result.keySet().toArray();
			for (int i =  0 ; i < places.length; i++) {
				//create JSON Objects here
				 
				webPage +="['"+(String)places[i]
							+ "', " +result.get((String)places[i]) + " ]" ;
				if(i<places.length-1)
				{
					webPage +=",";
				}				
			}
			webPage +="     ]);"; // End of JSON Object			
			
			// Drawing the Graph from the result HashMap
			if (req.getParameter("graph").equals("Map") 
					&& req.getParameter("state").equals("All States")) {
				
				webPage +="var options = { width: 1500, height: 2000, region:'US', resolution:'provinces'};";
				webPage +="var chart = "
						+ "new google.visualization.GeoChart("
						+ "document.getElementById('chart_div'));";				
			} else if (req.getParameter("graph").equals("Bar chart")) {
				webPage +="var options = { "
						+ "title: '"+ GraphTitle +"', "
				        + " vAxis: {title: '"+XAxis+"',  titleTextStyle: {color: 'red'}}"
						+ " }; ";
				webPage +=" var chart ="
				        + " new google.visualization.BarChart(document.getElementById('chart_div'));";
				
			}
			webPage +="chart.draw(data, options);	} ";
			webPage +="</script> </head> <body> "+
				"	<div id='chart_div' style='width: 1500px; height: 2000px;'></div> " +
				"  </body></html>";
			System.out.println(webPage);
			out.println(webPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
