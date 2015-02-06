import java.util.Random;

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

//	private static void gsonBenchmark(IParallelSorter p) {
//		Gson gson = new Gson();
//		String filename = "data/jsonData";
//		int LENGTH = Integer.MAX_VALUE;
//		Integer[] a = generateArray(LENGTH / 1024);
//		long start, stop;
//		long[] data = new long[10];
//
//		for (int i = 1; i <= 128; i += i) {
//			long totalTime = 0;
//			for (int k = 0; k < 10; k++) {
//				a = generateArray(LENGTH / 1024);
//
//				start = System.currentTimeMillis();
//				p.parallelSort(a, i);
//				stop = System.currentTimeMillis();
//
//				totalTime += (stop - start);
//				data[k] = (stop - start);
//			}
//			
//			String s = gson.toJson(data);
//			FileOutputStream outputStream;
//			
//			try {
//				outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//				outputStream.write(s.getBytes());
//				outputStream.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	private static void benchmark(IParallelSorter p) {
		int LENGTH = Integer.MAX_VALUE;
		Integer[] a = generateArray(LENGTH / 1024);

		// sequential, probably not good benchmarking
		long start = System.currentTimeMillis();
		p.serialSort(a);
		long stop = System.currentTimeMillis();
		System.out.println("Serial Sort Run Time: " + (stop - start) + ".");

		for (int i = 1; i <= 128; i += i) {
			long totalTime = 0;
			System.out.println("Parallel Sort Run Times with " + i
					+ " Threads: ");
			for (int k = 0; k < 10; k++) {
				a = generateArray(LENGTH / 1024);
				start = System.currentTimeMillis();
				p.parallelSort(a, i);
				stop = System.currentTimeMillis();
				totalTime += (stop - start);
				System.out.print((stop - start) + ". ");
			}
			System.out.println("\nAverage Parallel Sort Run Time with " + i
					+ " Threads: " + (totalTime / 10) + ".\n");
		}
	}
	
	private static void testSorter(IParallelSorter p){
		Integer[] a = generateArray(10);
		printArray(a);
		p.serialSort(a);
		printArray(a);
	}

	public static void main(String[] args) {
		benchmark(new ParallelQuickSorter());
		
	}
}
