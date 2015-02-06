

public class ParallelQuickSorter implements IParallelSorter {
	private static final int DEFAULT_THREAD_NUMBER = 2;

	@Override
	public <T extends Comparable<T>> void parallelSort(T[] a, int NUM_THREADS) {
		parallelQuickSort(a, 0, a.length - 1, NUM_THREADS);
	}

	@Override
	public <T extends Comparable<T>> void serialSort(T[] a) {
		quickSort(a, 0, a.length - 1);

	}
	
	private <T extends Comparable<T>> Thread quickSortThread(T[] a, int left, int right, int NUM_THREADS){
		return new Thread() {
			@Override
			public void run() {
				parallelQuickSort(a, left, right, NUM_THREADS / 2);
			}
		};
	}

	private <T extends Comparable<T>> void parallelQuickSort(T[] a, int left, int right, int NUM_THREADS){
		if(NUM_THREADS <= 1){
			quickSort(a, left, right);
		}
		
		//Divide data to be sorted in two separate threads
		else{
			int mid = partition(a, left, right);
			Thread leftSorter = quickSortThread(a, left, mid - 1, NUM_THREADS);
			Thread rightSorter = quickSortThread(a, mid, right, NUM_THREADS);
			
			leftSorter.start();
			rightSorter.start();
			
			try {
				leftSorter.join();
				rightSorter.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	private <T extends Comparable<T>> void quickSort(T[] a, int left, int right) {
		if(right - left < 2) return;
		int mid = partition(a, left, right);
		quickSort(a, left, mid - 1);
		quickSort(a, mid, right);
	}
	
	private <T extends Comparable<T>> int partition(T[] a, int left, int right){
		T pivot = a[left];
		
		while(left <= right){
			while(a[left].compareTo(pivot) < 0) left ++;
			while(a[right].compareTo(pivot) > 0) right --;
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
