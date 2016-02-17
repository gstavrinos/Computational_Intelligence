/** @file Misc.java
 * MISCELLANEOUS
 *  @author Fabio Caraffini
*/
package utils.algorithms;


import static utils.MatLab.max;
import static utils.MatLab.min;
import utils.MatLab;
import utils.random.RandUtils;

import java.util.Random;

/**
 * This class contains useful miscellaneous methods.
*/	
public class Misc
{

	/**
	 * Saturation on bounds of the search space.
	 * 
	 * @param x solution to be saturated.
	 * @param bounds search space boudaries.
	 * @return x_tor corrected solution.
	 */
	public static double[] saturate(double[] x, double[][] bounds)
	{
		int n = x.length;
		double[] x_sat = new double[n];
		for (int i = 0; i < n; i++)
			x_sat[i] = min(max(x[i], bounds[i][0]), bounds[i][1]);
		return x_sat;
	}
	
	/**
	 * Clone a solution.
	 * 
	 * @param x solution to be duplicated.
	 * @return xc cloned solution.
	 */
	public static double[] clone(double[] x)
	{
		int n=x.length;
		double[] xc = new double[n];
		for (int i = 0; i < n; i++)
			xc[i] = x[i];
		return xc;
	}
	/**
	 * Rounds x to the nearest integer towards zero.
	 */
	public static int fix(double x)
	{
		return (int) ((x >= 0) ? Math.floor(x) : Math.ceil(x));  
	}
	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (general case).
	 * @param n problem dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[][] bounds, int n)
	{
		double[] r = new double[n];
		double min = bounds[0][0];
		double max = bounds[0][1];
		Random random = new Random();
		for(int i=0;i<n;i++){
			r[i] = random.nextDouble()*((max-min)+1)+min;
		}
		return r;
	}
	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @param n problam dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[] bounds, int n)
	{
		double[] r = new double[n];
		double min = bounds[0];
		double max = bounds[1];
		Random random = new Random();
		for(int i=0;i<n;i++){
			r[i] = random.nextDouble()*((max-min)+1)+min;
		}
		return r;
	}
	/**
	 * Toroidal correction within the search space
	 * 
	 * @param x solution to be corrected.
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[] bounds)
	{
		double[] x_tor = new double[x.length];
		for(int i=0;i<x.length;i++){
			x_tor[i] = (x[i] - bounds[0]) / (bounds[1] - bounds[0]);
			if(x_tor[i] > 0){
				x_tor[i] = x_tor[i] - fix(x_tor[i]);
			}
			else if(x_tor[i] < 0){
				x_tor[i] = 1 - Math.abs(x_tor[i] - fix(x_tor[i]));
			}
			x_tor[i] = bounds[0] + x_tor[i] * (bounds[1] - bounds[0]);
		}
		return x_tor;
	}
	/**
	 * Toroidal correction within search space
	 * 
	 * @param x solution to be corrected.
	 * @param bounds search space boundaries (general case).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[][] bounds)
	{
		double[] x_tor = new double[x.length];
		for(int i=0;i<x.length;i++){
			x_tor[i] = (x[i] - bounds[i][0]) / (bounds[i][1] - bounds[i][0]);
			if(x_tor[i] > 0){
				x_tor[i] = x_tor[i] - fix(x_tor[i]);
			}
			else if(x_tor[i] < 0){
				x_tor[i] = 1 - Math.abs(x_tor[i] - fix(x_tor[i]));
			}
			x_tor[i] = bounds[i][0] + x_tor[i] * (bounds[i][1] - bounds[i][0]);
		}
		return x_tor;
	}
}
		
	


