import java.io.*;

public class SortFile {

    public static void main(String[] args) {
        // Define the file paths
        String inputFilePath = "RandomNames7000.csv";
        String outputFilePathBubbleSort = "SortedData_BubbleSort.csv";
        String outputFilePathInsertionSort = "SortedData_InsertionSort.csv";
        String outputFilePathSelectionSort = "SortedData_SelectionSort.csv";
        String outputFilePathQuickSort = "SortedData_QuickSort.csv";
        String outputFilePathMergeSort = "SortedData_MergeSort.csv";

        // Read data from CSV file and store the data as array
        int[] idsBubbleSort = readIdsFromCsv(inputFilePath);
        int[] idsInsertionSort = readIdsFromCsv(inputFilePath);
        int[] idsSelectionSort = readIdsFromCsv(inputFilePath);
        int[] idsQuickSort = readIdsFromCsv(inputFilePath);
        int[] idsMergeSort = readIdsFromCsv(inputFilePath);

        // Perform Bubble Sort
        bubbleSort(idsBubbleSort);
        // Write sorted data to CSV file for Bubble Sort
        writeSortedDataToCsv(outputFilePathBubbleSort, idsBubbleSort);

        // Perform Insertion Sort
        insertionSort(idsInsertionSort);
        // Write sorted data to CSV file for Insertion Sort
        writeSortedDataToCsv(outputFilePathInsertionSort, idsInsertionSort);

        // Perform Selection Sort
        selectionSort(idsSelectionSort);
        // Write sorted data to CSV file for Selection Sort
        writeSortedDataToCsv(outputFilePathSelectionSort, idsSelectionSort);

        // Perform Quick Sort
        quickSort(idsQuickSort);
        // Write sorted data to CSV file for Quick Sort
        writeSortedDataToCsv(outputFilePathQuickSort, idsQuickSort);

        // Perform Merge Sort
        mergeSort(idsMergeSort);
        // Write sorted data to CSV file for Merge Sort
        writeSortedDataToCsv(outputFilePathMergeSort, idsMergeSort);
    }

    // function to read the id values from the csv file
    private static int[] readIdsFromCsv(String filePath) {
        int[] ids = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;

            // Count the number of lines in the file
            int numberOfLines = (int) br.lines().count();

            // Initialize the array with the number of lines
            ids = new int[numberOfLines];

            // Reset BufferedReader to read from the start
            try (BufferedReader br2 = new BufferedReader(new FileReader(filePath))) {
                // Read and parse each line
                while ((line = br2.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    // store the read values as integers and populate the array
                    ids[index++] = id;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ids;
    }

    // function to perform Bubble Sort on an array of integers
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (arr[i - 1] > arr[i]) {
                    // Swap arr[i-1] and arr[i]
                    int temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    swapped = true;
                }
            }
        } while (swapped); // stop once the array is sorted
    }

    // function to perform Insertion Sort on an array of integers
    static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than key to one position ahead of their current position
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // function to perform Selection Sort on an array of integers
    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // Swap arr[i] and arr[minIdx]
            int temp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = temp;
        }
    }

    // function to perform Quick Sort on an array of integers
    static void quickSort(int[] arr) {
        quickSortRecurse(arr, 0, arr.length - 1);
    }

    private static void quickSortRecurse(int[] arr, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int pivotIdx = doPartitioning(arr, leftIdx, rightIdx);
            quickSortRecurse(arr, leftIdx, pivotIdx - 1);
            quickSortRecurse(arr, pivotIdx + 1, rightIdx);
        }
    }

    private static int doPartitioning(int[] arr, int leftIdx, int rightIdx) {
        int pivot = arr[rightIdx];
        int i = leftIdx - 1;

        for (int j = leftIdx; j < rightIdx; j++) {
            if (arr[j] < pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[rightIdx] to place the pivot in its correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[rightIdx];
        arr[rightIdx] = temp;

        return i + 1;
    }

    // function to perform Merge Sort on an array of integers
    static void mergeSort(int[] arr) {
        mergeSortRecurse(arr, 0, arr.length - 1);
    }

    private static void mergeSortRecurse(int[] arr, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int midIdx = (leftIdx + rightIdx) / 2;
            mergeSortRecurse(arr, leftIdx, midIdx);
            mergeSortRecurse(arr, midIdx + 1, rightIdx);
            merge(arr, leftIdx, midIdx, rightIdx);
        }
    }

    private static void merge(int[] arr, int leftIdx, int midIdx, int rightIdx) {
        int n1 = midIdx - leftIdx + 1;
        int n2 = rightIdx - midIdx;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[leftIdx + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[midIdx + 1 + j];
        }

        int i = 0, j = 0;
        int k = leftIdx;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // function to write sorted data to a CSV file
    private static void writeSortedDataToCsv(String filePath, int[] ids) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            for (int id : ids) {
                writer.println(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
