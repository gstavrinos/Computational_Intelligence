package experiments;

import algorithms.GeneticAlgorithm;
import algorithms.SimulatedAnnealing;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Lesson5Test extends Experiment {

    public Lesson5Test(int probDim)
    {
        super(probDim,"Lesson5Test");

        Algorithm a;
        Problem p;

        a = new GeneticAlgorithm();

        a.setParameter("population",1000d);
        a.setParameter("tournament_size", 2d);
        a.setParameter("children", 2d);

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
