
public class ParallelQuickSorter implements IParallelSorter {

	@Override
	public <T extends Comparable<T>> void parallelSort(T[] a, int NUM_THREADS) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Comparable<T>> void serialSort(T[] a) {
		quickSort(a);

	}
	
	private <T extends Comparable<T>> Thread quickSortThread(T[] a, int NUM_THREADS){
		return new Thread() {
			@Override
			public void run() {
				parallelQuickSort(a, NUM_THREADS / 2);
			}
		};
	}

	private <T extends Comparable<T>> void parallelQuickSort(T[] a, int left, int right, int NUM_THREADS){
		if(NUM_THREADS <= 1){
			quickSort(a, left, right);
			return;
		}
		
		
	}

	private <T extends Comparable<T>> void quickSort(T[] a, int left, int right) {
		
	}
	
	private <T extends Comparable<T>> int partition(T[] a, int left, int right){
		T pivot = a[left];
		
		while(left <= right){
			while(a[left].compareTo(pivot) < 0) right++;
			while(a[left].compareTo(pivot) > 0) right++;
			if(left <= right){
				swap(a, left, right);
				left++;
				right--;
			}
		}
		
		return left;
	}

	private <T extends Comparable<T>> void swap(T[] a, int l, int r){
		T tmp = a[r];
		a[r] = a[l];
		a[l] = tmp;
	}
}
