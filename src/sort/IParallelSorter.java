package sort;

public interface IParallelSorter {
	<T extends Comparable<T>> void parallelSort(T[] a, int NUM_THREADS);
	
	<T extends Comparable<T>> void serialSort(T[] a);
}
