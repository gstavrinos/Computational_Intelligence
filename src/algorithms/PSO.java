package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.MatLab;
import utils.RunAndStore;

import java.util.Random;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;

/**
 * Created by george on 3/15/16.
 */
public class PSO extends Algorithm {

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxGenerations) throws Exception {

        int NP = 50;
        int problemDimension = problem.getDimension();
        double Vmax = problem.getBounds()[0][1]-problem.getBounds()[0][0];
        double Vmin = -Vmax;
        double phimin = 0.4;
        double phimax = 0.9;

        RunAndStore.FTrend FT = new RunAndStore.FTrend();

        double xi[][] = new double[NP][problemDimension];
        double vi[][] = new double[NP][problemDimension];
        double xilb[][] = new double[NP][problemDimension];
        double xgb[] = new double[NP];
        double fxilb[] = new double[NP];
        double fxgb = 0;

        //initializing values
        for(int i = 0;i<NP;i++){
            Random random = new Random();
            vi[i] = generateRandomSolution(problem.getBounds(), problemDimension);//random.nextDouble()*(problem.getBounds()[0][1] - problem.getBounds()[0][0] + 1) + problem.getBounds()[0][0];
            xi[i] = generateRandomSolution(problem.getBounds(), problemDimension);
            xilb = xi;
            double fxi = problem.f(xi[i]);
            fxilb[i] = fxi;
            if(i == 0){
                xgb = xi[i];
                fxgb = fxi;
            }
            else{
                if(fxi <= problem.f(xgb) || i == 0){
                    xgb = xi[i];
                    fxgb = fxi;
                }
            }
        }

        double phi2 = 1.49618; //Clerc's suggestion
        double phi3 = 1.49618;
        FT.add(0, fxgb);
        int currGenerations = 0;
        int global_iteration = 0;
        while(currGenerations < maxGenerations){
            for(int i = 0;i<NP;i++){
                global_iteration++;
                double phi1 = phimax - ((phimin-phimax)/maxGenerations)*currGenerations;
                vi[i] = MatLab.sum(MatLab.multiply(phi1,vi[i]), MatLab.sum(MatLab.multiply(phi2, MatLab.subtract(xilb[i], xi[i])), MatLab.multiply(phi3, MatLab.subtract(xgb,xi[i]))));
                xi[i] = MatLab.sum(xi[i], vi[i]);
                double fxi = problem.f(xi[i]);
                if(fxi <= fxilb[i]){
                    xilb[i] = xi[i];
                    fxilb[i] = fxi;
                    if(fxi <= fxgb){
                        xgb = xi[i];
                        fxgb = fxi;
                        FT.add(global_iteration,fxgb);
                    }
                    //update swarm
                }
            }
            currGenerations++;
        }

        finalBest = xgb;

        return FT;
    }



}
