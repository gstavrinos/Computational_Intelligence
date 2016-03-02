package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore;

import java.util.Random;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;

/**
 * Created by george on 3/1/16.
 */
public class GeneticAlgorithm extends Algorithm{

    @Override
    public RunAndStore.FTrend execute(Problem problem, int maxGenerations) throws Exception {

        int population = getParameter("population").intValue();
        int tournament_size = getParameter("tournament_size").intValue();
        int children = getParameter("children").intValue();
        int problemDimension = problem.getDimension();
        double[][] bounds = problem.getBounds();

        RunAndStore.FTrend FT = new RunAndStore.FTrend();

        double individuals_value[][] = new double[population][problemDimension];
        double individuals_fitness[] = new double[population];

        //Generating initial population.
        for (int i = 0; i< population; i++){
            individuals_value[i] = generateRandomSolution(bounds, problemDimension);
        }

        //maxEvaluations is now maxGenerations, so I won't increase it here.
        for (int i = 0; i< population; i++){
            individuals_fitness[i] = problem.f(individuals_value[i]);
        }
        //Sort the individuals by fitness so that we can replace them later (bubble sort)
        for(int i=0; i < population; i++){
            for(int j=1; j < (population-i); j++){

                if(individuals_fitness[j-1] > individuals_fitness[j]){
                    //swap the elements!
                    double tempf = individuals_fitness[j-1];
                    double tempi[] = individuals_value[j-1];
                    individuals_fitness[j-1] = individuals_fitness[j];
                    individuals_value[j-1] = individuals_value[j];
                    individuals_fitness[j] = tempf;
                    individuals_value[j] = tempi;
                }

            }
        }

        int currGenerations = 0;
        //Stop when only when we hit maxGenerations
        while(currGenerations < maxGenerations){
            FT.add(currGenerations, individuals_fitness[0]);
            //Let's get two parents with tournament selection
            double parent1[] = tournamentSelection(individuals_value, individuals_fitness, tournament_size, problemDimension, population);
            double parent2[] = tournamentSelection(individuals_value, individuals_fitness, tournament_size, problemDimension, population);
            //Now we need to make some children (with box-crossover)
            double children_[][] = new double[children][problemDimension];
            for(int i = 0;i < children; i++){
                for(int j = 0;j < problemDimension; j++) {
                    children_[i][j] = Math.min(parent1[j], parent2[j]) + Math.random() * Math.abs(parent1[j] - parent2[j]);
                }
            }
            //Let's now mutate those children by adding a Gaussian distribution to their values.
            for(int i = 0;i < children; i++){
                for(int j = 0;j < problemDimension; j++) {
                    children_[i][j] = children_[i][j] + new Random().nextGaussian();
                }
            }
            //Saturate our newly created children with toroidal correction
            for(int i = 0;i < children; i++){
                children_[i] = toro(children_[i], bounds);
            }
            //Now we are going to replace the individuals with the worst fitness with our children
            for(int i = 0;i < children; i++){
                individuals_value[population-1-i] = children_[i];
                individuals_fitness[population-1-i] = problem.f(children_[i]);
            }

            //Sort the individuals by fitness so that we can replace them later (bubble sort)
            for(int i=0; i < population; i++){
                for(int j=1; j < (population-i); j++){

                    if(individuals_fitness[j-1] > individuals_fitness[j]){
                        //swap the elements!
                        double tempf = individuals_fitness[j-1];
                        double tempi[] = individuals_value[j-1];
                        individuals_fitness[j-1] = individuals_fitness[j];
                        individuals_value[j-1] = individuals_value[j];
                        individuals_fitness[j] = tempf;
                        individuals_value[j] = tempi;
                    }

                }
            }

            currGenerations++;

        }

        finalBest = individuals_value[0];
        //FT.add(currGenerations, individuals_fitness[0]);

        return FT;
    }

    private double[] tournamentSelection(double individuals[][], double fitness[], int size, int probDimension, int population){
        double tournament_values[][] = new double[size][probDimension];
        double tournament_fitness[] = new double[size];
        for(int i = 0; i < size; i++){
            //Get random competitors
            int rand_sel = (int)(Math.random() * population);
            tournament_values[i] = individuals[rand_sel];
            tournament_fitness[i] = fitness[rand_sel];
        }

        double besti[] = tournament_values [0];
        double bestf = tournament_fitness[0];
        for(int i = 0; i < size; i++){
            //And now... Let the games begin!
            if(tournament_fitness[i] < bestf){
                besti = tournament_values[i];
                bestf = tournament_fitness[i];
            }
        }
        return besti;
    }
}
