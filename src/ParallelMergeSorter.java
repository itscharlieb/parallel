import java.util.Arrays;

public class ParallelMergeSorter implements ParallelSorter {
	
	@Override
	public <T extends Comparable<T>> void parallelSort(T[] a, int numThreads) {
		parallelMergeSort(a, numThreads);
	}

	@Override
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
}
