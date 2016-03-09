package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;

import java.util.Random;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;

public class OnePlusOneEvolutionStrategy_v1 extends Algorithm {

    @Override
    public FTrend execute(Problem problem, int maxRepetitions) throws Exception {
        double sigma = 2;
        double TolSigma = Math.pow(10,-15);
        double MaxNoImp = 4*4*Math.log(10)/Math.log(1.5);
        FTrend FT = new FTrend();
        int problemDimension = problem.getDimension();
        double[][] bounds = problem.getBounds();
        int currRepetitions = 0;
        double[] x = new double[problemDimension];
        double fx = 0;
        if (initialSolution != null)
        {
            x = initialSolution;
            fx = initialFitness;
        }
        else
        {
            x = generateRandomSolution(bounds, problemDimension);
            fx = problem.f(x);
        }

        int curNoImp = 0;

        double[] x_off = new double[problemDimension];
        double fx_off;

        FT.add(0, fx);
        int global_iteration = 0;

        while(currRepetitions < maxRepetitions) {
            currRepetitions+=1;
            while (sigma > TolSigma && curNoImp < MaxNoImp) {
                boolean newx = false;
                for(int i = 0;i<x.length;i++) {
                    x_off[i] = x[i] + sigma * (new Random().nextGaussian());
                }
                fx_off = problem.f(x_off);
                if(fx_off < fx){
                    x = x_off;
                    sigma *= 1.5;
                    newx = true;
                }
                else if(fx_off < fx){
                    curNoImp++;
                    x = x;//xn+1 = xn. This line could be missing. It's here for completeness.
                    sigma = sigma; //This line could be missing. It's here for completeness.
                }
                else{
                    curNoImp++;
                    x = x;//xn+1 = xn. This line could be missing. It's here for completeness.
                    sigma *= Math.pow(1.5,-(1/4));
                }
                if(newx) {
                    fx = problem.f(x);//update f(x)
                }
                global_iteration++;
                FT.add(global_iteration,fx);
            }
        }

        finalBest = x;

        return FT;
    }

}
