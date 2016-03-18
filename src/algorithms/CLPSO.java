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
public class CLPSO extends Algorithm {

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxEvaluations) throws Exception {

        //This code is based on
        //https://books.google.gr/books?id=6DT3BwAAQBAJ&pg=PA232&lpg=PA232&dq=clpso+pseudo+code&source=bl&ots=7SednNT9Bk&sig=hGv5TpfeYTw_np3e3d2iYJEAz8Y&hl=en&sa=X&ved=0ahUKEwjJ_4y7gMbLAhVlc3IKHdKsDwAQ6AEIGjAA#v=onepage&q=clpso%20pseudo%20code&f=false
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
        double xirlb[][] = new double[NP][problemDimension];
        double xgb[] = new double[NP];
        double fxilb[] = new double[NP];
        double fxgb = 0;
        double Pc[] = new double[NP];

        //initializing values
        for(int i = 0;i<NP;i++){
            vi[i] = generateRandomSolution(problem.getBounds(), problemDimension);//random.nextDouble()*(problem.getBounds()[0][1] - problem.getBounds()[0][0] + 1) + problem.getBounds()[0][0];
            xi[i] = generateRandomSolution(problem.getBounds(), problemDimension);
            Pc[i] = 0.05+0.45*(((Math.exp(10*(i+1-1)/(NP-1)))-1)/(Math.exp(10)-1));
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

        double c = 1; //http://www.indjst.org/index.php/indjst/article/viewFile/47643/38660
        FT.add(0, fxgb);
        Random random = new Random();
        int currEvaluations = 0;
        while(currEvaluations < maxEvaluations){
            for(int i = 0;i<NP;i++){
                double w = ((w0-0.2)*(maxEvaluations-currEvaluations)/maxEvaluations)+0.2;
                double rand = random.nextDouble();
                if(rand < Pc[i]){
                    xirlb[i] = xilb[i];
                }
                else{
                    int r1 = (int)(Math.random() * NP);
                    while(r1==i){//we want any particle except the one we are currently updating
                        r1= (int)(Math.random() * NP);
                    }
                    int r2 = (int)(Math.random() * NP);
                    while(r2==i || r2==r1){//we want any particle except the one we are currently updating and not the one chosen above
                        r2= (int)(Math.random() * NP);
                    }
                    //choose the fittest
                    if(fxilb[r1]<fxilb[r2]){
                        xirlb[i] = xilb[r1];
                    }
                    else{
                        xirlb[i] = xilb[r2];
                    }
                }
                //vi[i] = MatLab.sum(MatLab.multiply(phi1,vi[i]), MatLab.sum(MatLab.multiply(phi2, MatLab.subtract(xilb[i], xi[i])), MatLab.multiply(phi3, MatLab.subtract(xgb,xi[i]))));
                double U[][] =  new double[NP][problemDimension];
                for(int ii=0;ii<NP;ii++) {
                    U[ii] = generateRandomSolution(new double[]{0, 1}, problemDimension);
                }
                vi[i] = MatLab.sum(MatLab.multiply(w,vi[i]),MatLab.multiply(MatLab.multiply(c,U),MatLab.subtract(xirlb[i],xi[i])));
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
