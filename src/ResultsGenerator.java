import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import org.apache.commons.io.input.ReversedLinesFileReader;
import static org.apache.commons.math3.stat.inference.TestUtils.tTest;

/**
 * Created by george on 5/1/16.
 */
public class ResultsGenerator {

    static String algorithms[] = {"CustomMemeticAlgorithm",
            "jDifferentialEvolution_rand1exp_bin",
            "DifferentialEvolution_rand1exp",
            "CLPSO",
            "PSO",
            "OnePlusOneEvolutionStrategy_v2",
            "OnePlusOneEvolutionStrategy_v1",
            "GeneticAlgorithm",
            "SimulatedAnnealing",
            "RIS",
            "S"};

    static LinkedList<String> all_files = new LinkedList();

    public void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();
        if (list == null){
            return;
        }
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk(f.getAbsolutePath());
            }
            all_files.addFirst(f.getAbsolutePath());
        }
    }

    public static int foundAlgorithm(String test){
        int index = 0;
        for(;index<algorithms.length;index++){
            if(test.equals(algorithms[index])){
                break;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int Na = 11;
        int Ntp = 4;
        double pvalues[][] = new double[Na][Ntp];//Na algorithms, Ntp problems
        ResultsGenerator rg = new ResultsGenerator();
        rg.walk("/home/george/IntelliJ_ws/CIO_Software/results/TestAll/" );
        int curr_alg = -1;
        int currProb = -1;
        int noOfRuns = 0;
        Collections.sort(all_files);
        for(int i=0;i<all_files.size();i++){
            File f = new File(all_files.get(i));
            String fn = f.getName();
            if(!f.isDirectory()) {
                int tmp = Integer.parseInt(fn.replace(".txt",""));
                if(tmp > noOfRuns) {
                    noOfRuns = tmp;
                }
            }
        }
        noOfRuns++;//filenames start from 0;
        double solutions10[][][][] = new double[Na][Ntp][noOfRuns][10];
        double solutions50[][][][] = new double[Na][Ntp][noOfRuns][50];
        double solutions100[][][][] = new double[Na][Ntp][noOfRuns][100];

        for(int i=0;i<all_files.size();i++){
            File f = new File(all_files.get(i));
            String fn = f.getName();
            int temp = foundAlgorithm(fn);
            if(temp < algorithms.length) {
                curr_alg = foundAlgorithm(fn);
            }
            else if(fn.contains("Michalewicz")){
                currProb = 0;
            }
            else if(fn.contains("Rastigin")){
                currProb = 1;
            }
            else if(fn.contains("Schwefel")){
                currProb = 2;
            }
            else if(fn.contains("Sphere")){
                currProb = 3;
            }
            else{
                int run_index = Integer.parseInt(fn.replace(".txt",""));
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String str = br .readLine();
                    br.close();
                    str = str.replace("# ","");//remove leading #
                    String sol_[] = str.split(" ");
                    if(sol_.length == 10) {
                        for(int j=0;j<10;j++){
                            solutions10[curr_alg][currProb][run_index][j] = Double.parseDouble(sol_[j]);
                        }
                    }
                    else if(sol_.length == 50){
                        for(int j=0;j<50;j++){
                            solutions50[curr_alg][currProb][run_index][j] = Double.parseDouble(sol_[j]);
                        }
                    }
                    else if(sol_.length == 100){
                        for(int j=0;j<100;j++){
                            solutions100[curr_alg][currProb][run_index][j] = Double.parseDouble(sol_[j]);
                        }
                    }
                }
                catch(Exception e){
                    System.err.println(e);
                }
            }
        }
        for(int i=1;i<Na;i++){
            for(int j=0;j<Ntp;j++){
                for(int k=0;k<noOfRuns;k++){
                    pvalues[i][j] += tTest(solutions10[0][j][k],solutions10[i][j][k]);
                }
            }
        }
        for(int i=1;i<Na;i++){
            for(int j=0;j<Ntp;j++){
                for(int k=0;k<noOfRuns;k++){
                    pvalues[i][j] += tTest(solutions50[0][j][k],solutions50[i][j][k]);
                }
            }
        }
        for(int i=1;i<Na;i++){
            for(int j=0;j<Ntp;j++){
                for(int k=0;k<noOfRuns;k++){
                    pvalues[i][j] += tTest(solutions100[0][j][k],solutions100[i][j][k]);
                }
            }
        }
        for(int i=0;i<Na;i++){
            for(int j=0;j<Ntp;j++){
                pvalues[i][j] /= (noOfRuns*3);
            }
        }
        //Holm-Bonferroni
        int R_[][] = new int[Na][Ntp];
        for(int i=0;i<Na;i++){
            for(int j=0;j<Ntp;j++){
                R_[i][j] = i;
            }
        }
        double z[] = new double[Na];
        Arrays.fill(z,0);
        for(int p=0;p<Ntp;p++) {
            double temp = 0;
            for (int i = 1; i < Na; i++) {
                for (int j = 2; j < (Na - i); j++) {
                    if (pvalues[j - 1][p] > pvalues[j][p]) {
                        temp = pvalues[j - 1][p];
                        pvalues[j - 1][p] = pvalues[j][p];
                        pvalues[j][p] = temp;
                        temp = R_[j - 1][p];
                        R_[j - 1][p] = R_[j][p];
                        R_[j][p] = (int)temp;
                    }
                }
            }
        }
        int R[] = new int[Na];//Ranks
        for (int j = 0; j < Na; j++) {
            for(int k=0;k<Ntp;k++) {
                R[j] += (Na - R_[j][k]) / Ntp;
            }
        }
        for (int j = 0; j < Na; j++) {
            z[j] = (R[j]-R[0])/Math.sqrt(Na*(Na+1)/(6*Ntp));
        }
        double pvalue[] = new double[Na];
        Arrays.fill(pvalue,0);
        for(int i=0;i<Na;i++){
            for(int j=0;j<Ntp;j++){
                pvalue[i] += pvalues[i][j]/Ntp;
            }
        }
        //sort all of the to print them in latex format!
        int itemp = 0;
        double dtemp = 0;
        String stemp = "";
        String algs[] = algorithms;
        double delta[] = new double[Na];
        Arrays.fill(delta,0.05);
        for(int i=0; i < Na; i++){
            for(int j=1; j < (Na-i); j++){
                if(R[j-1] < R[j]){
                    itemp = R[j-1];
                    R[j-1] = R[j];
                    R[j] = itemp;
                    stemp = algs[j - 1];
                    algs[j - 1] = algs[j];
                    algs[j] = stemp;
                    dtemp = pvalue[j - 1];
                    pvalue[j - 1] = pvalue[j];
                    pvalue[j] = dtemp;
                    dtemp = z[j - 1];
                    z[j - 1] = z[j];
                    z[j] = dtemp;
                }
            }
        }
        boolean accepted = false;
        for(int i=1;i<Na;i++){
            double d = delta[i]/(Na-i);
            System.out.print(i+" & "+algs[i]+" & "+R[i]+" & "+z[i]+" & "+pvalue[i]+" & "+d+" & ");
            if(pvalue[i]<d || accepted){
                System.out.println("Accepted \\\\");
                accepted = true;//from here, all the following are accepted.
            }
            else{
                System.out.println("Rejected \\\\");
            }
        }
    }
}
