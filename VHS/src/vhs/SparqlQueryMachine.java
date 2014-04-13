package vhs;

import java.io.*;
import java.util.*;
import java.lang.*;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

public class SparqlQueryMachine {
	static final String dataset1150NameSpace = "http://logd.tw.rpi.edu/source/data-gov/dataset/1150/version/1st-anniversary";
	static final String dataset1151NameSpace = "http://logd.tw.rpi.edu/source/data-gov/dataset/1151/version/1st-anniversary";
	
	static final String commonPrefix = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
			+ "PREFIX xsd2: <http://www.w3.org/TR/xmlschema-2/#> "
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
			+ "PREFIX pmlp: <http://inference-web.org/2.0/pml-provenance.owl#> "
			+ "PREFIX pmlj: <http://inference-web.org/2.0/pml-justification.owl#> "
			+ "PREFIX tw_service: <http://logd.tw.rpi.edu/source/tw-rpi-edu/service/> "
			+ "PREFIX tw_converter: <http://logd.tw.rpi.edu/source/tw-rpi-edu/service/csv2rdf4lod/version/2011-Mar-01/> "
			+ "PREFIX provenance: <http://logd.tw.rpi.edu/source/data-gov/provenance/1151/version/1st-anniversary/conversion/raw/> "
			+ "PREFIX conversion: <http://purl.org/twc/vocab/conversion/> "
			+ "PREFIX ov: <http://open.vocab.org/terms/> "
			+ "PREFIX dcterms: <http://purl.org/dc/terms/> "
			+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
			+ "PREFIX void: <http://rdfs.org/ns/void#> "
			+ "PREFIX doap: <http://usefulinc.com/ns/doap#> "
			+ "PREFIX scovo: <http://purl.org/NET/scovo#> "
			+ "PREFIX sdmx: <http://purl.org/linked-data/cube#> "
			+ "PREFIX vann: <http://purl.org/vocab/vann/> "
			+ "PREFIX data-gov_vocab: <http://logd.tw.rpi.edu/source/data-gov/vocab/> "
			+ "PREFIX base_vocab: <http://logd.tw.rpi.edu/vocab/> "
			+ "PREFIX raw: <http://logd.tw.rpi.edu/source/data-gov/dataset/1151/vocab/raw/> "
			+ "PREFIX sioc: <http://rdfs.org/sioc/ns#> ";

	static final String dataset1150Prefix = " PREFIX ds1150_global_value: <http://logd.tw.rpi.edu/source/data-gov/dataset/1150/> "
			+ " PREFIX ds1150_vocab: <http://logd.tw.rpi.edu/source/data-gov/dataset/1150/vocab/> "
			+ " PREFIX ds1150: <http://logd.tw.rpi.edu/source/data-gov/dataset/1150/version/1st-anniversary/> ";

	static final String dataset1151Prefix = " PREFIX ds1151_vocab: <http://logd.tw.rpi.edu/source/data-gov/dataset/1151/vocab/> "
			+ "PREFIX ds1151: <http://logd.tw.rpi.edu/source/data-gov/dataset/1151/version/1st-anniversary/> "
			+ "PREFIX ds1151_global_value: <http://logd.tw.rpi.edu/source/data-gov/dataset/1151/> ";

	public Model createModel(String filename, String namespace) {

		InputStream in = null;
		Model model = null;
		try {

			in = new FileInputStream(new File(filename));

			// Create an empty in-memory model and populate it from the graph
			// model = ModelFactory.createMemModelMaker().createModel(null);
			model = ModelFactory.createOntologyModel();
			model.read(in, namespace); // null base URI, since model URIs
										// are absolute
			in.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return model;
	}

	public ResultSet issueSPARQLQuery(String queryString, Model model) {

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet response = qe.execSelect();

		// Output query results
		//ResultSetFormatter.out(System.out, response, query);
		
		// Important - free up resources used running the query
		//qe.close();
		return response;
		
	}
	
//	public static void main(String g[])
//	{
//		String query = commonPrefix + dataset1150Prefix 
//		 		+ " SELECT ?S ?O "
//		 		+ " WHERE { "
//		 		+ " ?S <http://logd.tw.rpi.edu/source/data-gov/dataset/1150/vocab/raw/state> ?O ."
//		 		+ " }  ";
//		issueSPARQLQuery(query,createModel("C:\\Users\\Tejashree\\workspace\\VHS\\dataset1150.rdf", dataset1150NameSpace));
//
//	}
}
