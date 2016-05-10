package experiments;

import algorithms.DifferentialEvolution_rand1exp;
import algorithms.OnePlusOneEvolutionStrategy_v1;
import algorithms.OnePlusOneEvolutionStrategy_v2;
import algorithms.jDifferentialEvolution_rand1exp_bin;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Lesson8Test extends Experiment {

    public Lesson8Test(int probDim, int variant, int crossover)
    {
        super(probDim,"Lesson8Test");

        Algorithm a;
        Problem p;

        /*if(variant == 1) {
            a = new DifferentialEvolution_rand1exp();
        }
        else{
            a = new jDifferentialEvolution_rand1exp_bin();
        }*/
        a = new DifferentialEvolution_rand1exp();
        a.setParameter("population",100d);
        add(a); //add it to the list

        a = new jDifferentialEvolution_rand1exp_bin();
        a.setParameter("population",100d);
        a.setParameter("bin_exp",1.0);
        add(a); //add it to the list

        a = new jDifferentialEvolution_rand1exp_bin();
        a.setParameter("population",100d);
        a.setParameter("bin_exp",0.0);
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
