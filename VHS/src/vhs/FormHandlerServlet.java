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
			ResultSet result = null;
			out.println("<html><body> Here are the results.... ");

			if (req.getParameter("state").equals("All States")) {
				if (req.getParameter("area").equals("State")) {
					// show state wise
				} else if (req.getParameter("area").equals("County")) {
					// use county wise dataset

					dataset = "dataset1150.rdf";
					Data1150 dataTypes = new Data1150();
					query += SparqlQueryMachine.dataset1150Prefix
							+ " SELECT ?S ?O ?P ?M " + " WHERE { " 
							+ " ?S " + dataTypes.attributes.get("State") + "  ?O . "
							+ " ?S " + dataTypes.attributes.get("County") + "  ?P . "
							+ " ?P " + dataTypes.attributes.get(req.getParameter("column")) + "  ?M . "							
							+ " }  ";
					result = (ResultSet) sqm.issueSPARQLQuery(query, sqm
							.createModel(path + dataset,
									SparqlQueryMachine.dataset1150NameSpace));
				} else if (req.getParameter("area").equals(
						"Congressional District")) {

					dataset = "dataset1151.rdf";
					Data1151 dataTypes = new Data1151();
					query += SparqlQueryMachine.dataset1151Prefix
							+ " SELECT ?S ?O ?P ?M " + " WHERE { " 
							+ " ?S " + dataTypes.attributes.get("State") + "  ?O . "
							+ " ?S " + dataTypes.attributes.get("Congressional District") + "  ?P . "
							+ " ?P " + dataTypes.attributes.get(req.getParameter("column")) + "  ?M . "							
							+ " }  ";
					result = (ResultSet) sqm.issueSPARQLQuery(query, sqm
							.createModel(path + dataset,
									SparqlQueryMachine.dataset1151NameSpace));
				}

				// Printing the resultset
				while (result.hasNext()) {
					System.out.println("<br>"
							+ result.nextSolution().toString());
				}

				if (req.getParameter("graph").equals("Map")) {

				} else if (req.getParameter("graph").equals("Bar chart")) {

				}

			} else {
				out.println(req.getParameter("state"));
			}

			out.println("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
