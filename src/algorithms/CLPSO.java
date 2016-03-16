package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.MatLab;
import utils.RunAndStore;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;

/**
 * Created by george on 3/15/16.
 */
public class CLPSO extends Algorithm {

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxEvaluations) throws Exception {

        int NP = 50;
        int problemDimension = problem.getDimension();
        double Vmax = problem.getBounds()[0][1]-problem.getBounds()[0][0];
        double Vmin = -Vmax;
        double phimin = 0.4;
        double phimax = 0.9;
        double w0 = 0.9;

        RunAndStore.FTrend FT = new RunAndStore.FTrend();

        double xi[][] = new double[NP][problemDimension];
        double vi[][] = new double[NP][problemDimension];
        double xilb[][] = new double[NP][problemDimension];
        double xgb[] = new double[NP];
        double fxilb[] = new double[NP];
        double fxgb = 0;

        //initializing values
        for(int i = 0;i<NP;i++){
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
        int currEvaluations = 0;
        while(currEvaluations < maxEvaluations){
            for(int i = 0;i<NP;i++){
                double phi1 = phimax - ((phimin-phimax)/maxEvaluations)*currEvaluations;
                //vi[i] = MatLab.sum(MatLab.multiply(phi1,vi[i]), MatLab.sum(MatLab.multiply(phi2, MatLab.subtract(xilb[i], xi[i])), MatLab.multiply(phi3, MatLab.subtract(xgb,xi[i]))));
                vi[i] = ;//TODO pseudo code here: https://books.google.gr/books?id=6DT3BwAAQBAJ&pg=PA232&lpg=PA232&dq=clpso+pseudo+code&source=bl&ots=7SednNT9Bk&sig=hGv5TpfeYTw_np3e3d2iYJEAz8Y&hl=en&sa=X&ved=0ahUKEwjJ_4y7gMbLAhVlc3IKHdKsDwAQ6AEIGjAA#v=onepage&q=clpso%20pseudo%20code&f=false
                xi[i] = MatLab.sum(xi[i], vi[i]);
                //if one of the values in this vector is out of bounds, use toroidal correction for this vector
                for(int j = 0; j < problemDimension; j++) {
                    if (xi[i][j] < problem.getBounds()[0][0] || xi[i][j] > problem.getBounds()[0][1]) {
                        xi[i] = toro(xi[i], problem.getBounds());
                        break;
                    }
                }
                double fxi = problem.f(xi[i]);
                currEvaluations++;
                if(fxi <= fxilb[i]){
                    xilb[i] = xi[i];
                    fxilb[i] = fxi;
                    if(fxi <= fxgb){
                        xgb = xi[i];
                        fxgb = fxi;
                    }
                }
            }
            FT.add(currEvaluations,fxgb);
        }

        finalBest = xgb;

        return FT;
    }



}
