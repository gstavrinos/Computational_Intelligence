package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.MatLab;
import utils.RunAndStore;

import java.util.Arrays;
import java.util.Random;

import static utils.MatLab.*;
import static utils.algorithms.Misc.generateRandomSolution;

/**
 * Created by george on 4/25/16.
 */
public class RIS extends Algorithm {

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxEvaluations) throws Exception {

        RunAndStore.FTrend FT = new RunAndStore.FTrend();

        int count_evaluations = 0;

        int problemDimension = problem.getDimension();
        double[][] bounds = problem.getBounds();

        //x elite
        double xe[] = new double[problemDimension];
        double fxe;
        //x trial
        double xt[] = new double[problemDimension];
        double fxt;

        xe = generateRandomSolution(bounds, problemDimension);
        fxe = problem.f(xe);

        FT.add(0, fxe);
        int n = problemDimension;
        Random random = new Random();

        while(count_evaluations <  maxEvaluations) {
            //Re-sampling with inheritance
            int i = random.nextInt(n);
            xt = generateRandomSolution(bounds, problemDimension);
            fxt = problem.f(xt);
            double ae = 0.5;
            double CR = Math.pow(2, -1 / n * ae);

            int k = 0;

            while (random.nextDouble() <= CR && k < n) {
                xt[i] = xe[i];
                i++;
                if (i == n) {
                    i = 0;
                }
                k++;
            }
            fxe = problem.f(xe);
            fxt = problem.f(xt);
            count_evaluations++;
            if (fxt <= fxe) {
                xe = xt;
                fxe = fxt;
            }

            FT.add(count_evaluations, fxe);

            //Exploitative local search

            double r[] = new double[problemDimension];//ρ
            Arrays.fill(r, 0.4 * (bounds[0][1] - bounds[0][0]));
            double e = Math.pow(10, -6);//ε
            double xs[] = new double[problemDimension];
            double fxs;

            double eq6 = 0;
            for (int j = 0; j < problemDimension; j++) {
                eq6 += Math.pow(r[j] / (bounds[j][1] - bounds[j][0]), 2);
            }
            eq6 = Math.sqrt(eq6);

            while (eq6 < e && count_evaluations < maxEvaluations) {
                xs = xt;
                fxs = fxt;
                for (int j = 0; j < problemDimension && count_evaluations < maxEvaluations; j++) {
                    xs[j] = xt[j] - r[j];
                    fxs = problem.f(xs);
                    count_evaluations++;
                    if (fxs <= fxt) {
                        xt = xs;
                        fxt = fxs;
                    }
                    else {
                        xs[j] = xt[j] + r[j] / 2;
                        fxs = problem.f(xs);
                        count_evaluations++;
                        if (fxs <= fxt) {
                            xt = xs;
                            fxt = fxs;
                        }
                    }
                }
                if (fxt <= fxe) {
                    xe = xt;
                    fxe = fxt;
                }
                else {
                    r = MatLab.multiply(1 / 2, r);
                }
                FT.add(count_evaluations, fxe);
            }
        }

        finalBest = xe;

        return FT;

    }
}