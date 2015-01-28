import java.util.Random;

import javafx.scene.chart.Axis;
import javafx.scene.chart.ValueAxis;


public class SorterBenchmarker {
	
	private static void printArray(Object[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

	private static Integer[] generateArray(int length) {
		int BOUND = 1024;
		Integer[] s = new Integer[length];
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			s[i] = r.nextInt(BOUND);
		}

		return s;
	}
	
	private static void benchmark(IParallelSorter p) {
		Axis<Integer> x = new ValueAxis<Integer>(0.0, 140.0);
		Axis<Integer> y = new ValueAxis<Integer>(0.0, 1500.0);
		
		SortLineChart s = new SortLineChart(x, y);
		
		int LENGTH = Integer.MAX_VALUE;
		Integer[] a = generateArray(LENGTH / 1024);

		// sequential, probably not good benchmarking
		long start = System.currentTimeMillis();
		p.serialSort(a);
		long stop = System.currentTimeMillis();
		System.out.println("Serial Sort Run Time: " + (stop - start) + ".");
		
		for (int i = 1; i <= 128; i += i) {
			long totalTime = 0;
			System.out.println("Individual Parallel Sort Run Time with " + i + " Threads: ");
			for(int k = 0; k < 10; k++) {
				a = generateArray(LENGTH / 1024);
				start = System.currentTimeMillis();
				p.parallelSort(a, i);
				stop = System.currentTimeMillis();
				totalTime += (stop - start);
				System.out.print((stop - start) + ". " );
			}
			System.out.println("\nAverage Parallel Sort Run Time with " + i
					+ " Threads: " + (totalTime / 10) + ".\n");
		}
	}
	
	public static void main(String[] args) {
		IParallelSorter p = new ParallelMergeSorter();
		benchmark(p);
	}
}
