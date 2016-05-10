package experiments;

import algorithms.CustomMemeticAlgorithm;
import algorithms.DifferentialEvolution_rand1exp;
import algorithms.RIS;
import benchmarks.BaseFunctions;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Lesson9Test extends Experiment {

    public Lesson9Test(int probDim, int variant)
    {
        super(probDim,"Lesson9Test");

        Algorithm a;
        Problem p;

        /*if(variant == 1) {
            a = new RIS();
        }
        else{
            a = new CustomMemeticAlgorithm();
        }*/

        a = new RIS();

        add(a); //add it to the list

        a = new CustomMemeticAlgorithm();

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
