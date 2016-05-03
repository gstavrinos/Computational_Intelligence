package experiments;


import algorithms.*;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

/**
 * Created by george on 5/1/16.
 */
public class TestAll extends Experiment {

    public TestAll(int probDim)
    {
        super(probDim,"TestAll");

        Algorithm a;// ///< A generic optimiser.
        Problem p;// ///< A generic problem.


        a = new CustomMemeticAlgorithm();

        add(a); //add it to the list

        a = new S();
        add(a); //add it to the list

        a = new SimulatedAnnealing();

        a.setParameter("method",1d);//  /T
        //a.setParameter("method",0d);//  /sqrt(T)

        a.setParameter("alpha", 0.99d);
        add(a); //add it to the list

        a = new GeneticAlgorithm();

        a.setParameter("population",1000d);
        a.setParameter("tournament_size", 2d);
        a.setParameter("children", 2d);

        add(a); //add it to the list

        a = new OnePlusOneEvolutionStrategy_v1();

        add(a); //add it to the list

        a = new OnePlusOneEvolutionStrategy_v2();

        add(a); //add it to the list

        a = new PSO();
        add(a); //add it to the list
        a = new CLPSO();
        add(a); //add it to the list


        a = new DifferentialEvolution_rand1exp();
        a.setParameter("population",100d);
        a.setParameter("bin_exp", 0d);
        add(a); //add it to the list

        a = new jDifferentialEvolution_rand1exp_bin();

        a.setParameter("population",100d);
        a.setParameter("bin_exp", 0d);
        add(a); //add it to the list

        a = new RIS();

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
