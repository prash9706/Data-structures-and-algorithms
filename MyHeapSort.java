
public class MyHeapSort {
   
	//Driver Method
	public static void main(String[] args){
		//arr[0] contains numbers of element in the array.
		int[] arr = {20,8,9,7,3,4,6,10,5,2,14,
				     11,1,12,20,16,17,13,15,19,18};
		MyHeapSort myHeapSort = new MyHeapSort();
		System.out.print("Inital Array - ");
		myHeapSort.printArray(arr);
		myHeapSort.buildMaxHeap(arr);
		System.out.print("Heapify Array- ");
		myHeapSort.printArray(arr);
		myHeapSort.heapSort(arr);
		System.out.print("Sorted Array- ");
		myHeapSort.printArray(arr);
	}
	
	//Method to print Array
	private void printArray(int[] arr){
		for (int i=1;i<arr.length;i++) {System.out.print(arr[i]+" ");}
		System.out.println();
	}
	
	//Build heap from given array.
	private void buildMaxHeap(int[] arr){
		int heapSize = arr[0];
		for(int i=heapSize/2;i>=1;i--){
			int j=i;
			while(j<heapSize){
				int left = 2*j;
				int right = left+1;
				int largestIdx = j;
				if(left<=heapSize && arr[left]>arr[j]){
					largestIdx = left;
				}
				if(right<=heapSize && arr[right]>arr[largestIdx]){
					largestIdx = right;
				}
				if(j==largestIdx){break;}
				int temp = arr[largestIdx];
				arr[largestIdx] = arr[j];
				arr[j] = temp;
				j = largestIdx;
			}
		}
	}
	
	//Perform heap sort on build heap.
	private void heapSort(int[] arr){
		for(int i=1;i<arr.length;i++){
			int heapSize = arr[0];
			int temp = arr[heapSize];
			arr[heapSize] = arr[1];
			arr[1] = temp;
			int j=1;
			while(j<heapSize){
				int left = 2*j;
				int right = left+1;
				int largestIdx = j;
				if(left<heapSize && arr[left]>arr[j]){
					largestIdx = left;
				}
				if(right<heapSize && arr[right]>arr[largestIdx]){
					largestIdx = right;
				}
				if(j==largestIdx){break;}
				temp = arr[largestIdx];
				arr[largestIdx] = arr[j];
				arr[j] = temp;
				j = largestIdx;
			}
			arr[0]--;
		}
	}
}
