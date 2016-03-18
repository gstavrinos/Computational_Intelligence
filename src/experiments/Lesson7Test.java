package experiments;

import algorithms.CLPSO;
import algorithms.OnePlusOneEvolutionStrategy_v1;
import algorithms.OnePlusOneEvolutionStrategy_v2;
import algorithms.PSO;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

/**
 * Created by george on 3/15/16.
 */
public class Lesson7Test  extends Experiment {

    public Lesson7Test(int probDim)
    {
        super(probDim,"Lesson7Test");

        Algorithm a;
        Problem p;

        //a = new PSO();
        //add(a); //add it to the list
        a = new CLPSO();
        add(a); //add it to the list

        p = new BaseFunctions.Sphere(probDim);
        add(p);//add it to the list
        p = new BaseFunctions.Rastigin(probDim);
        add(p);
        p = new BaseFunctions.Schwefel(probDim);
        add(p);
        p = new BaseFunctions.Michalewicz(probDim);
        add(p);

    }

}