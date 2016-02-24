package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore;

import java.util.Random;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;

public class SimulatedAnnealing extends Algorithm {

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxEvaluations) throws Exception {
        double method = getParameter("method");
        float T = 1f;
        float alpha = Float.parseFloat(getParameter("alpha")+"");
        float Tmin = 0.1f;
        RunAndStore.FTrend FT = new RunAndStore.FTrend();
        int problemDimension = problem.getDimension();
        double[][] bounds = problem.getBounds();
        int currEvaluations = 0;
        double[] xnew = new double[problemDimension];
        double fxnew = 0;
        double[] xcb = new double[problemDimension];
        double fxcb;
        if (initialSolution != null)
        {
            xcb = initialSolution;
            fxcb = initialFitness;
        }
        else
        {
            xcb = generateRandomSolution(bounds, problemDimension);
            fxcb = problem.f(xcb);
            currEvaluations++;              //we ran f(x) once already!
        }

        FT.add(0, fxcb);
        while(currEvaluations < maxEvaluations && T > Tmin) {  //I need to minimize the use of f(x)
            for (int i = 0; i < problemDimension && currEvaluations < maxEvaluations && T > Tmin; i++) {
                xnew[i] = neighborSolution(xcb[i]);
                if(xnew[i] < bounds[i][0] || xnew[i] > bounds[i][1]){
                    toro(xnew, bounds);
                }
                fxnew = problem.f(xnew);
                currEvaluations++;
                if(method == 1f) {
                    if (Math.random() < Math.exp((fxcb - fxnew) / T)) {
                        xcb[i] = xnew[i];
                        fxcb = fxnew;
                    }
                }
                else{
                    if (Math.random() < Math.exp((fxcb - fxnew) / Math.sqrt(T))) {
                        xcb[i] = xnew[i];
                        fxcb = fxnew;
                    }
                }
                FT.add(currEvaluations, fxnew);
                T = alpha * T;
            }
        }

        finalBest = xcb;

        return FT;
    }

    private double neighborSolution(double xcb){
        return xcb + new Random().nextInt();
    }

}
