import java.util.Random;

public class Sorts {

    public static void bubbleSort(int[] A) {
        int n = A.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (A[i - 1] > A[i]) {
                    // Swap A[i-1] and A[i]
                    int temp = A[i - 1];
                    A[i - 1] = A[i];
                    A[i] = temp;
                    swapped = true;
                }
            }
        } while (swapped); // stop once the array is sorted
    }

    public static void selectionSort(int[] A) {
        int n = A.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (A[j] < A[minIdx]) {
                    minIdx = j;
                }
            }
            // Swap A[i] and A[minIdx] we got before
            int temp = A[i];
            A[i] = A[minIdx];
            A[minIdx] = temp;
        }
    }

    public static void insertionSort(int[] A) {
        int n = A.length;
        for (int i = 1; i < n; ++i) {
            int key = A[i];
            int j = i - 1;

            // Move elements of A[0..i-1] that are greater than key to one position ahead of their current position
            while (j >= 0 && A[j] > key) {
                A[j + 1] = A[j];
                j = j - 1;
            }
            A[j + 1] = key;
        }
    }

    public static void mergeSort(int[] A) {
        mergeSortRec(A, 0, A.length - 1);
    }

    private static void mergeSortRec(int[] A, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int mid = (leftIdx + rightIdx) / 2;
            mergeSortRec(A, leftIdx, mid);
            mergeSortRec(A, mid + 1, rightIdx);
            merge(A, leftIdx, mid, rightIdx);
        }
    }

    private static void merge(int[] A, int leftIdx, int mid, int rightIdx) {
        int n1 = mid - leftIdx + 1;
        int n2 = rightIdx - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = A[leftIdx + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = A[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = leftIdx;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            A[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            A[k] = R[j];
            j++;
            k++;
        }
    }

    public static void quickSort(int[] A) {
        quickSortRec(A, 0, A.length - 1);
    }

    private static void quickSortRec(int[] A, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int pivotIdx = partition(A, leftIdx, rightIdx);
            quickSortRec(A, leftIdx, pivotIdx - 1);
            quickSortRec(A, pivotIdx + 1, rightIdx);
        }
    }

    private static int partition(int[] A, int leftIdx, int rightIdx) {
        int pivot = A[leftIdx]; // Choose leftmost element as pivot (not recommended)

        int i = leftIdx + 1; // index to traverse from left to right
        int j = rightIdx; // index to traverse from right to left

        while (i <= j) {
            while (i <= rightIdx && A[i] <= pivot) {
                i++;
            }
            while (j >= leftIdx + 1 && A[j] > pivot) {
                j--;
            }
            if (i < j) {
                // Swap A[i] and A[j]
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }

        // Swap pivot with A[j] (the correct position for pivot)
        int temp = A[leftIdx];
        A[leftIdx] = A[j];
        A[j] = temp;

        return j; // return the index of pivot after partitioning
    }

    public static void quickSortMedian3(int[] A) {
        quickSortMedian3Rec(A, 0, A.length - 1);
    }

    private static void quickSortMedian3Rec(int[] A, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int pivotIdx = medianOfThree(A, leftIdx, rightIdx);
            int newPivotIdx = partition(A, leftIdx, rightIdx);
            quickSortMedian3Rec(A, leftIdx, newPivotIdx - 1);
            quickSortMedian3Rec(A, newPivotIdx + 1, rightIdx);
        }
    }

    private static int medianOfThree(int[] A, int leftIdx, int rightIdx) {
        int midIdx = (leftIdx + rightIdx) / 2;

        // Sort left, mid, and right elements
        if (A[leftIdx] > A[midIdx])
            swap(A, leftIdx, midIdx);
        if (A[leftIdx] > A[rightIdx])
            swap(A, leftIdx, rightIdx);
        if (A[midIdx] > A[rightIdx])
            swap(A, midIdx, rightIdx);

        // Place the median element at the leftIdx position
        swap(A, leftIdx, midIdx);

        return midIdx; // return the index of the median element
    }

    public static void quickSortRandom(int[] A) {
        quickSortRandomRec(A, 0, A.length - 1);
    }

    private static void quickSortRandomRec(int[] A, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int pivotIdx = randomPartition(A, leftIdx, rightIdx);
            quickSortRandomRec(A, leftIdx, pivotIdx - 1);
            quickSortRandomRec(A, pivotIdx + 1, rightIdx);
        }
    }

    private static int randomPartition(int[] A, int leftIdx, int rightIdx) {
        Random rand = new Random();
        int pivotIdx = rand.nextInt(rightIdx - leftIdx + 1) + leftIdx;
        // Swap pivot with the leftmost element
        swap(A, leftIdx, pivotIdx);
        return partition(A, leftIdx, rightIdx);
    }

    private static void swap(int[] A, int idx1, int idx2) {
        int temp = A[idx1];
        A[idx1] = A[idx2];
        A[idx2] = temp;
    }
}
