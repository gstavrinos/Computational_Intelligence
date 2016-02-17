/** @file BaseFunctions.java
 *  @author Fabio Caraffini
*/
package benchmarks;

import utils.MatLab;
import interfaces.Problem;

/**
 * Benchmark Base Functions.
 */
public class BaseFunctions
{
	/**
	 * Ackley function. 
	 * 
	 * References: 
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page295.htm" > Ref 1 </a>
	 * </br>
	 * <a href="http://www.it.lut.fi/ip/evo/functions/node14.html" > Ref 2 </a>
	 * </br>
	 * <a href="http://www.math.ntu.edu.tw/~wwang/cola_lab/test_problems/multiple_opt/multiopt_prob/Ackley/Ackley.htm" > Ref 3 </a>
	 * </br>
	 * <a href="http://tracer.lcc.uma.es/problems/ackley/ackley.html" > Ref 4 </a></br>
	*/
	public static class Ackley extends Problem
	{
		final double a = 20;
		final double b = 0.2;
		final double c = 2*Math.PI;		
		/**
		* Costructor for the  Ackley function with suggested bounds.
		*/
		public Ackley(int dimension){ super(dimension, new double[] {-1, 1});}
		/**
		* Costructor for the Ackley function with hyper-cube shaped decision space.
		*/
		public Ackley(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the Ackley function with fully customised decision space.
		*/
		public Ackley(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Ackley function.
		* 
		* @param x solution to be avaluated
		*/
		public double f(double[] x)
		{	
			final int n = x.length;
			double y = 0;
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				double square_sum = 0;		
				double cos_sum = 0;

				for (int i = 0; i < n; i++)	
				{
					square_sum += Math.pow(x[i],2);
					cos_sum += Math.cos(c*x[i]);
				}

				y = -a * Math.exp(-b * Math.sqrt(square_sum/n)) - Math.exp(cos_sum/n) + a + Math.exp(1);
			}
			return y;
		}
	}

		
	/**
	 * Alpine function.
	 * 
	 * References:
	 * <a href="http://clerc.maurice.free.fr/pso/Alpine/Alpine_Function.htm" > Ref 1 </a>
	*/
	public static class Alpine extends Problem
	{		
		/**
		* Costructor for the Alpine function with suggested bounds.
		*/
		public Alpine(int dimension){ super(dimension, new double[] {-10, 10});}
		/**
		* Costructor for the Alpine function with hyper-cube shaped decision space.
		*/
		public Alpine(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the Alpine function with fully customised decision space.
		*/
		public Alpine(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Alpine function.
		* 
		* @param x solution to be evaluated
		*/
		public double f(double[] x)
		{	
			final int n = x.length;
			double y = 0;
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n; i++)
					y += Math.abs(x[i]*Math.sin(x[i]) + 0.1*x[i]);
			}
			return y;
		}
	}
	
	/**
	 * Rosenbrock function.
	 * 
	 * References:
	 * <a href="http://en.wikipedia.org/wiki/Rosenbrock_function" > Ref 1 </a>
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2537.htm" > Ref 2 </a>
	 * <a href="http://mathworld.wolfram.com/RosenbrockFunction.html" > Ref 3 </a>
	*/
	public static class Rosenbrock extends Problem
	{
		/**
		* Costructor for  the  sphere function with suggested bounds.
		*/
		public Rosenbrock(int dimension){ super(dimension, new double[] {-100, 100});}
		/**
		* Costructor the  the  sphere function with hyper-cube shaped decision space.
		*/
		public Rosenbrock(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the Rosenbrock function with fully customised decision space.
		*/
		public Rosenbrock(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Rosenbrock function.
		* 
		* @param x solution to be avaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length;
			double y = 0;
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n-1; i++)
				y += Math.pow((1-x[i]),2) + 100*Math.pow((x[i+1]-Math.pow(x[i],2)),2);
			}
			return y;
		 }
	}
	
	/**
	 * Sphere function (DE JONG).
	 * 
	 * References:
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page1113.htm" > Ref 1 </a>
	 * <a href="http://www.it.lut.fi/ip/evo/functions/node2.html" > Ref 2 </a>
	*/
	public static class Sphere extends Problem
	{
		/**
		* Costructor for the Sphere function with suggested bounds.
		*/
		public Sphere(int dimension){ super(dimension, new double[] {-5.12, 5.12});}
		/**
		* Costructor for the Sphere function with hyper-cube shaped decision space.
		*/
		public Sphere(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the Sphere function with fully customised decision space.
		*/
		public Sphere(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Sphere function.
		* 
		* @param x solution to be avaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length;
			double y = 0;
		
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n; i++){
					y += Math.pow(x[i],2);
				}
			}
			return y;
		}
	}
	
	/**
	 * Schwefel function.
	 * 
	 * References:
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2530.htm" > Ref 1 </a>
	 * <a href="http://www.sfu.ca/~ssurjano/schwef.html" > Ref 2 </a>
	 */
	public static class Schwefel extends Problem 
	{
		/**
		* Costructor for the  Schwefel function with suggested bounds.
		*/
		public Schwefel(int dimension) { super(dimension, new double[] {-500, 500}); }
		/**
		* Costructor for the  Schwefel function with hyper-cube shaped decision space.
		*/
		public Schwefel(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the  Schwefel function with fully customised decision space.
		*/
		public Schwefel(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Schwefel function.
		* 
		* @param x solution to be avaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length;
			double sum = 0;
			double y = 0;
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n; i++){
					sum += x[i]*Math.sin(Math.sqrt(Math.abs(x[i])));
				}
				y = 418.9829*this.getDimension();
			}
			return y;
		}
	}
	
	/**
	 * Rastrigin function.
	 * 
	 * References:
	 * <a href="http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page2607.htm" > Ref 1 </a>
	 * <a href="http://en.wikipedia.org/wiki/Rastrigin_function" > Ref 2 </a>
	 * <a href="http://www.mathworks.com/help/toolbox/gads/f14773.html" > Ref 3 </a>
	 */
		public static class Rastigin extends Problem
		{
		/**
		* Costructor for the  Rastigin function with suggested bounds.
		*/
		public Rastigin(int dimension) { super(dimension, new double[] {-5.12, 5.12}); }
		/**
		* Costructor for the  Rastigin function with hyper-cube shaped decision space.
		*/
		public Rastigin(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the  Rastigin function with fully customised decision space.
		*/
		public Rastigin(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Rastigin function.
		* 
		* @param x solution to be avaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length;
			int A = 10;
			double y = A*n;
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n; i++){
					y += Math.pow(x[i], 2)-A*Math.cos(2*Math.PI*x[i]);
				}
			}
			return y;
		}
	}
	
	/**
	 * Michalewicz function.
	 * 
	 * References:
	 * <a href="http://www.geatbx.com/docu/fcnindex-01.html#P204_10395" > Ref 1 </a>
	 * <a href="http://www.pg.gda.pl/~mkwies/dyd/geadocu/fcnfun12.html" > Ref 2 </a>
	*/	
	public static class Michalewicz extends Problem
	{
		/**
		* Costructor for the  Michalewicz function with suggested bounds.
		*/
		public Michalewicz(int dimension) { super(dimension, new double[] {0, Math.PI}); }
		/**
		* Costructor for the  Michalewicz function with hyper-cube shaped decision space.
		*/
		public Michalewicz(int dimension, double[] bounds) { super(dimension, bounds); }
		/**
		* Costructor for the  Michalewicz function with fully customised decision space.
		*/
		public Michalewicz(int dimension, double[][] bounds) { super(dimension, bounds); }
		/**
		* This method implement the Michalewicz function.
		* 
		* @param x solution to be avaluated
		*/
		public double f(double[] x)
		{
			final int n = x.length;
			int m = 10;
			double y = 0;
			if(this.getDimension()!= n)
			{
				y=Double.NaN;
				System.out.println("WARNING: the design variable does not match the dimensionality of the problem!");
			}
			else
			{
				for (int i = 0; i < n; i++){
					y += Math.sin(x[i])*Math.pow(Math.sin(i*Math.pow(x[i], 2)/Math.PI), 2*m);
				}
				y = -y;
			}
			return y;
		}
	}	
	
}
