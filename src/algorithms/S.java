package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;

public class S extends Algorithm {

    @Override
    public FTrend execute(Problem problem, int maxEvaluations) throws Exception {
        float alpha = 0.4f;
        FTrend FT = new FTrend();
        int problemDimension = problem.getDimension();
        double[][] bounds = problem.getBounds();
        int currEvaluations = 0;
        double[] xs = new double[problemDimension];
        double fxs = 0;
        double[] xsbest = new double[problemDimension];
        double fxsbest;
        if (initialSolution != null)
        {
            xsbest = initialSolution;
            fxsbest = initialFitness;
        }
        else
        {
            xsbest = generateRandomSolution(bounds, problemDimension);
            fxsbest = problem.f(xsbest);
            currEvaluations++;              //we run f(x) once already!
        }

        FT.add(0, fxsbest);

        double[] delta = new double[problemDimension];
        for(int i=0;i<problemDimension;i++){
            delta[i] = alpha*(bounds[i][1]-bounds[i][0]);
        }

        while(currEvaluations < maxEvaluations){  //I need to minimize the use of f(x)
            boolean improved = false;
            for (int i = 0; i < problemDimension && currEvaluations < maxEvaluations; i++){
                xs[i] = xsbest[i] - delta[i];
                if(xs[i] < bounds[i][0] || xs[i] > bounds[i][1]){
                    xs = toro(xs, bounds);
                }
                fxs = problem.f(xs);
                currEvaluations++;
                if(fxs <= fxsbest){
                    xsbest[i] = xs[i];
                    fxsbest = fxs;
                    improved = true;
                }
                else{
                    xs[i] = xsbest[i] + delta[i]/2;
                    if(xs[i] < bounds[i][0] || xs[i] > bounds[i][1]){
                        xs = toro(xs, bounds);
                    }
                    fxs = problem.f(xs);
                    currEvaluations++;
                    if(fxs <= fxsbest){
                        xsbest[i] = xs[i];
                        fxsbest = fxs;
                        improved = true;
                    }
                    else{
                        xs[i] = xsbest[i];
                        if(xs[i] < bounds[i][0] || xs[i] > bounds[i][1]){
                            xs = toro(xs, bounds);
                        }
                    }
                }
            }
            FT.add(currEvaluations, fxsbest);
            if(!improved){
                for(int i=0;i<problemDimension;i++){
                    delta[i] /= 2;
                }
            }
        }

        finalBest = xsbest;

        return FT;
    }

}
