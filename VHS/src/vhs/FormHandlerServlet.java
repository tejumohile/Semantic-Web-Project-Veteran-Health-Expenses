package vhs;
import java.io.*;
import java.util.*;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import javax.servlet.*;
import javax.servlet.http.*;
public class FormHandlerServlet extends javax.servlet.http.HttpServlet
{
	String path = "C:\\Users\\Tejashree\\workspace\\VHS\\";
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		try
		{
			PrintWriter out = resp.getWriter();
			SparqlQueryMachine sqm = new SparqlQueryMachine();
			out.println("<html><body> Here are the results.... ");
			if(req.getParameter("state").equals("All States"))
			{				
				
				String query = SparqlQueryMachine.commonPrefix + SparqlQueryMachine.dataset1150Prefix 
				 		+ " SELECT ?S ?O "
				 		+ " WHERE { "
				 		+ " ?S <http://logd.tw.rpi.edu/source/data-gov/dataset/1150/vocab/raw/state> ?O ."
				 		+ " }  ";
				ResultSet result = (ResultSet) sqm.issueSPARQLQuery(query, 
						sqm.createModel(path + "dataset1150.rdf",
								SparqlQueryMachine.dataset1150NameSpace));
				
				//Printing the resultset
				while(result.hasNext())
				{
					out.println("<br>"+result.nextSolution().toString());
				}
				
				
				if(req.getParameter("graph").equals("Map"))
				{
					
				}
				else if(req.getParameter("graph").equals("Bar chart"))
				{
					
					
				}
				
			}
			else
			{
				out.println(req.getParameter("state"));
			}
			
			out.println("</body></html>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
