package experiments;

import algorithms.CMAES;
import algorithms.ISPO;
import algorithms.S;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Lesson3Test extends Experiment {

    public Lesson3Test(int probDim)
    {
        super(probDim,"Lesson3Test");

        Algorithm a;// ///< A generic optimiser.
        Problem p;// ///< A generic problem.

        a = new S();
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
