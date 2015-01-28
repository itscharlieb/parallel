import java.util.Arrays;
import java.util.Random;

public class ParallelMergeSorter implements ParallelSorter {
	public <T extends Comparable<T>> void parallelSort(T[] a) {
		parallelMergeSort(a, 2);
	}

	public <T extends Comparable<T>> void serialSort(T[] a) {
		mergeSort(a);
	}

	private <T extends Comparable<T>> void parallelMergeSort(T[] a,
			int NUM_THREADS) {
		if (NUM_THREADS <= 1) {
			mergeSort(a);
			return;
		}

		int mid = a.length / 2;

		T[] left = Arrays.copyOfRange(a, 0, mid);
		T[] right = Arrays.copyOfRange(a, mid, a.length);

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

	private <T extends Comparable<T>> Thread mergeSortThread(T[] a,
			int NUM_THREADS) {
		return new Thread() {
			@Override
			public void run() {
				parallelMergeSort(a, NUM_THREADS / 2);
			}
		};
	}

	private <T extends Comparable<T>> void mergeSort(T[] a) {
		if (a.length <= 1)
			return;

		int mid = a.length / 2;

		T[] left = Arrays.copyOfRange(a, 0, mid);
		T[] right = Arrays.copyOfRange(a, mid, a.length);

		mergeSort(left);
		mergeSort(right);

		merge(left, right, a);
	}

	private <T extends Comparable<T>> void merge(T[] a, T[] b, T[] r) {
		int i = 0, j = 0, k = 0;
		while (i < a.length && j < b.length) {
			if (a[i].compareTo(b[j]) < 0)
				r[k++] = a[i++];
			else
				r[k++] = b[j++];
		}

		while (i < a.length)
			r[k++] = a[i++];

		while (j < b.length)
			r[k++] = b[j++];
	}

	private void printArray(Object[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

	private Integer[] generateArray(int length) {
		int BOUND = 1024;
		Integer[] s = new Integer[length];
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			s[i] = r.nextInt(BOUND);
		}

		return s;
	}

	private void sortBenchmark() {
		int LENGTH = Integer.MAX_VALUE;
		Integer[] a = generateArray(LENGTH / 1024);

		// sequential
		long start = System.currentTimeMillis();
		mergeSort(a);
		long stop = System.currentTimeMillis();
		System.out.println("Serial Sort Run Time: " + (stop - start) + ".");
		
		for (int i = 1; i <= 128; i += i) {
			long totalTime = 0;
			System.out.println("Individual Parallel Sort Run Time with " + i + " Threads: ");
			for(int k = 0; k < 10; k++) {
				a = generateArray(LENGTH / 1024);
				start = System.currentTimeMillis();
				parallelMergeSort(a, i);
				stop = System.currentTimeMillis();
				totalTime += (stop - start);
				System.out.print((stop - start) + ". " );
			}
			System.out.println("\nAverage Parallel Sort Run Time with " + i
					+ " Threads: " + (totalTime / 10) + ".\n");
		}
	}

	public static void main(String[] args) {
		ParallelMergeSorter p = new ParallelMergeSorter();
		p.sortBenchmark();
		
//		Integer[] a = generateArray(25);
//		printArray(a);
//		parallelSort(a);
//		printArray(a);
	}
}
