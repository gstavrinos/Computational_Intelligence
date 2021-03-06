package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore;

import java.util.Random;

import static utils.MatLab.*;
import static utils.algorithms.Misc.generateRandomSolution;

/**
 * Created by george on 4/14/16.
 */
public class jDifferentialEvolution_rand1exp_bin extends Algorithm {

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxGenerations) throws Exception {

        int population = getParameter("population").intValue(); //M
        int bin_exp = getParameter("bin_exp").intValue(); //bin or exp?
        double F = 0.5;
        double CR = 0.8;
        float t1 = 0.1f;
        float t2 = 0.1f;
        float Fl = 0.1f;
        float Fu = 0.9f;
        RunAndStore.FTrend FT = new RunAndStore.FTrend();

        int problemDimension = problem.getDimension();
        double[][] bounds = problem.getBounds();

        double individuals_value[][] = new double[population][problemDimension];
        double individuals_fitness[] = new double[population];

        int g = 0;
        double fbest = Double.MAX_VALUE;
        double xbest[] = new double[problemDimension];

        for(int i=0; i < population; i++){
            individuals_value[i] = generateRandomSolution(bounds, problemDimension);
            individuals_fitness[i] = problem.f(individuals_value[i]);
            if(individuals_fitness[i] < fbest){
                xbest = individuals_value[i];
                fbest = individuals_fitness[i];
            }
        }
	FT.add(g, fbest);
        Random random = new Random();

        double individuals_value_next[][] = individuals_value;
        double individuals_fitness_next[] = individuals_fitness;

        while(g < maxGenerations){

            //get the g+1 population
            individuals_fitness = individuals_fitness_next;
            individuals_value = individuals_value_next;

            for(int j=0;j<population;j++){
                //implement the mutation (/rand/1 part of the algorithm)
                double xj[] = individuals_value[j];
                int r1 = random.nextInt(population);
                int r2 = random.nextInt(population);
                int r3 = random.nextInt(population);
                while(!(r1!=r2 && r1!=r3 && r1!=j && r2!=r3 && r2!=j && r3!=j)) {
                    r1 = random.nextInt(population);
                    r2 = random.nextInt(population);
                    r3 = random.nextInt(population);
                }
                double xr1[] = individuals_value[r1];
                double xr2[] = individuals_value[r2];
                double xr3[] = individuals_value[r3];
                double rand2 = random.nextDouble();
                double rand4 = random.nextDouble();
                if(rand2<t1){
                    double rand1 = random.nextDouble();
                    F = Fl+rand1*Fu;
                }
                if(rand4<t2){
                    double rand3 = random.nextDouble();
                    CR = rand3;
                }
                double xm[] = sum(xr1,multiply(F,(subtract(xr2,xr3))));
                //implement the crossover (exp part of the algorithm)
                double xoff[];
                if(bin_exp > 0) {
                    xoff = exponentialXO(xj, xm, problemDimension, CR);
                }
                else{
                    xoff = binomialXO(xj, xm, problemDimension, CR);
                }
                double fxoff = problem.f(xoff);
                if(fxoff <= individuals_fitness[j]){
                    individuals_value_next[j] = xoff;
                    individuals_fitness_next[j] = fxoff;
                }
                else{
                    individuals_value_next[j] = individuals_value[j];
                    individuals_fitness_next[j] = individuals_fitness[j];
                }
            }
            g++;
            for(int i=0; i < population; i++){
                if(individuals_fitness[i] < fbest){
                    xbest = individuals_value[i];
                    fbest = individuals_fitness[i];
                }
            }
            FT.add(g, fbest);
        }

        finalBest = xbest;

        return FT;
    }

    double[] exponentialXO(double[] x1, double[] x2, int n, double CR){
        double xoff[] = x2;
        int index = new Random().nextInt(n-1);
        xoff[index] = x2[index];//is there any reason to do this??!
        int i = index + 1;
        double U = new Random().nextDouble();
        if(i<n) {//we don't need IOoB, thank you!
            while (U <= CR || i != index) {
                U = new Random().nextDouble();
                xoff[i] = x1[i];
                i++;
                if (i >= n) {
                    i = 0;
                }
            }
        }
        return xoff;
    }

    double[] binomialXO(double[] x1, double[] x2, int n, double CR){
        double xoff[] = new double[n];
        int index = new Random().nextInt(n-1);
        for(int i=0;i<n;i++){
            if(new Random().nextDouble() <= CR || i == index){
                xoff[i] = x1[i];
            }
            else{
                xoff[i] = x2[i];
            }
        }
        return xoff;
    }

}
