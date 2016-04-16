/** @file RunExperiments.java
 *  
 *  @author Fabio Caraffini
*/
import java.util.Vector; 
import interfaces.Experiment;
import static utils.RunAndStore.resultsFolder;
import experiments.*;

/** 
* This class contains the main method and has to be used for launching experiments.
*/
public class RunExperiments
{
	
	
	/** 
	* Main method.
	* This method has to be modified in order to launch a new experiment.
	*/
	public static void main(String[] args) throws Exception
	{	
		
		// make sure that "results" folder exists
		resultsFolder();
	
	
		Vector<Experiment> experiments = new Vector<Experiment>();////!< List of problems 
	
			
		//@@@ MODIFY THIS PART @@@

		/*
		experiments.add(new Lesson3Test(10));
		experiments.add(new Lesson3Test(30));
		experiments.add(new Lesson3Test(50));

		experiments.add(new Lesson4Test(10));
		experiments.add(new Lesson4Test(30));
		experiments.add(new Lesson4Test(50));

		experiments.add(new Lesson5Test(10));
		experiments.add(new Lesson5Test(50));


		experiments.add(new Lesson6Test(10,1));
		experiments.add(new Lesson6Test(50,1));
		experiments.add(new Lesson6Test(10,2));
		experiments.add(new Lesson6Test(50,2));



		experiments.add(new Lesson7Test(10));
		experiments.add(new Lesson7Test(50));*/

		//experiments.add(new Lesson8Test(10,1,0));
		//experiments.add(new Lesson8Test(50,1,0));
		//experiments.add(new Lesson8Test(100,1,0));
		experiments.add(new Lesson8Test(10,2,0));
		experiments.add(new Lesson8Test(50,2,0));
		experiments.add(new Lesson8Test(100,2,0));
		experiments.add(new Lesson8Test(10,2,1));
		experiments.add(new Lesson8Test(50,2,1));
		experiments.add(new Lesson8Test(100,2,1));

		//@@@@@@
	
	
	
		for(Experiment experiment : experiments)
		{
			//experiment.setShowPValue(true);
			experiment.setNrRuns(30);
			experiment.setBudgetFactor(10);
			experiment.startExperiment();
			System.out.println("");
			experiment = null;
		}
	
		
		
	}
	
	

}
