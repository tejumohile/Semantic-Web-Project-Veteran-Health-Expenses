package vhs;

import java.util.*;

import com.hp.hpl.jena.query.ResultSet;
public class ResultSetAdapter {

	// ResultSet with two columns
	public static HashMap<String, Double> sum(ResultSet res)
	{
		HashMap<String, Double> resultHM = new HashMap<>();
		while (res.hasNext()) {
			String solution = res.nextSolution().toString();
			String [] solutionSplit = solution.split("\"");
			if(!resultHM.containsKey(solutionSplit[1]))
			{
				resultHM.put(solutionSplit[1], Double.parseDouble(solutionSplit[3]));
				
			}
			else
			{
				resultHM.put(solutionSplit[1], resultHM.get(solutionSplit[1])
											+ Double.parseDouble(solutionSplit[3]));
			}
		}
		return resultHM;
	}
	
}
