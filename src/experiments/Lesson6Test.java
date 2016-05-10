package experiments;

import algorithms.OnePlusOneEvolutionStrategy_v1;
import algorithms.OnePlusOneEvolutionStrategy_v2;
import algorithms.S;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Lesson6Test extends Experiment {

    public Lesson6Test(int probDim, int variant)
    {
        super(probDim,"Lesson6Test");

        Algorithm a;
        Problem p;

        a = new S();
        add(a);
            a = new OnePlusOneEvolutionStrategy_v1();
        add(a); //add it to the list
            a = new OnePlusOneEvolutionStrategy_v2();

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
