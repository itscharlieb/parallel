import java.util.Arrays;
import java.util.Random;


public class ParallelMergeSort{
	public static void sort(int[] a)
	{
		parallelMergeSort(a, 2);
	}
	
	public static void parallelMergeSort(int[] a, int NUM_THREADS)
	{
		if(NUM_THREADS <= 1)
		{
			mergeSort(a);
			return;
		}
		
		int mid = a.length / 2;
		
		int[] left = Arrays.copyOfRange(a, 0, mid);
		int[] right = Arrays.copyOfRange(a, mid, a.length);
				
		Thread leftSorter = mergeSortThread(left, NUM_THREADS);
		Thread rightSorter = mergeSortThread(right, NUM_THREADS);
		
		leftSorter.start();
		rightSorter.start();
		
		try {
			leftSorter.join();
			rightSorter.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		merge(left, right, a);
	}
	
	private static Thread mergeSortThread(int[] a, int NUM_THREADS)
	{
		return new Thread()
		{
			@Override
			public void run()
			{
				parallelMergeSort(a, NUM_THREADS / 2);
			}
		};
	}
	
	public static void mergeSort(int[] a)
	{
		if(a.length <= 1) return;
		
		int mid = a.length / 2;
		
		int[] left = Arrays.copyOfRange(a, 0, mid);
		int[] right = Arrays.copyOfRange(a, mid, a.length);
		
		mergeSort(left);
		mergeSort(right);
		
		merge(left, right, a);
	}
	
	
	private static void merge(int[] a, int[] b, int[] r)
	{
		int i = 0, j = 0, k = 0;
		while(i < a.length && j < b.length)
		{
			if(a[i] < b[j])
				r[k++] = a[i++];
			else
				r[k++] = b[j++];
		}
		
		while(i < a.length)
			r[k++] = a[i++];
		
		while(j < b.length)
			r[k++] = b[j++];
	}
	
	private static void printArray(int[] a)
	{
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	private static int[] generateArray(int length)
	{
		int BOUND = 1024;
		int[] s = new int[length];
		Random r = new Random();
		for(int i = 0; i < length; i++)
		{
			s[i] = r.nextInt(BOUND);
		}
		
		return s;
	}
	
	private static void sortBenchmark()
	{
		int LENGTH = Integer.MAX_VALUE;
		int[] a = generateArray(LENGTH/64);
		
		//sequential
		long start = System.currentTimeMillis();
		mergeSort(a);
		long stop = System.currentTimeMillis();
		System.out.println("Serial Sort Run Time: " + (stop - start) + ".");
		
		for(int i = 2; i <= 128; i+=i)
		{
			a = generateArray(LENGTH/64);
			start = System.currentTimeMillis();
			parallelMergeSort(a, i);
			stop = System.currentTimeMillis();
			System.out.println("Parallel Sort Run Time with " + i + " Threads: " + (stop - start) + ".");
		}
	}
	
	public static void main(String[] args) {
		sortBenchmark();
		System.out.println("test");
	}
}
