package experiments;

import algorithms.SimulatedAnnealing;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Lesson4Test extends Experiment {

    public Lesson4Test(int probDim)
    {
        super(probDim,"Lesson4Test");

        Algorithm a;// ///< A generic optimiser.
        Problem p;// ///< A generic problem.

        //Each time, only one of the method and alpha parameters are uncommented.
        a = new SimulatedAnnealing();

        a.setParameter("method",1d);//  /T
        //a.setParameter("method",0d);//  /sqrt(T)

        a.setParameter("alpha", 0.99d);
        //a.setParameter("alpha", 0.5d);
        //a.setParameter("alpha", 1/Math.log(0.99));
        //a.setParameter("alpha", 1/Math.log(0.5));
        add(a); //add it to the list


        a = new SimulatedAnnealing();
        a.setParameter("method",0d);//  /sqrt(T)
        a.setParameter("alpha", 1/Math.log(0.99));

        add(a);

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
