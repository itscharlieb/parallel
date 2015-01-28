
public interface IParallelSorter {
	<T extends Comparable<T>> void parallelSort(T[] a, int numThreads);
	
	<T extends Comparable<T>> void serialSort(T[] a);
}
