package sort;

public class SortData {
	private int aNumThreads;
	private long[] aSortTimes;
	private long aAvgSortTime;
	
	public SortData(int pNumThreads, long[] pSortTimes, long pAvgSortTime){
		aNumThreads = pNumThreads;
		aSortTimes = pSortTimes;
		aAvgSortTime = pAvgSortTime;
	}
}
