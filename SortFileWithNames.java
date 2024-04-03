import java.io.*;


// The original file contains two values, so a single int[] is impossible if we want to output the sorted data as the original format
// It is possible to use two d array but it makes the problem more complex
// We will create array of objects instead
class Record {
    int id;
    String name;

    Record(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class SortFileWithNames {

    public static void main(String[] args) {
        // Define the file paths
        String inputFilePath = "RandomNames7000.csv";
        String outputFilePathBubbleSort = "SortedDataWithName_BubbleSort.csv";
        String outputFilePathInsertionSort = "SortedDataWithName_InsertionSort.csv";
        String outputFilePathSelectionSort = "SortedDataWithName_SelectionSort.csv";
        String outputFilePathQuickSort = "SortedDataWithName_QuickSort.csv";
        String outputFilePathMergeSort = "SortedDataWithName_MergeSort.csv";

        // Read data from CSV file
        Record[] recordsBubbleSort = readRecordsFromCsv(inputFilePath);
        Record[] recordsInsertionSort = readRecordsFromCsv(inputFilePath);
        Record[] recordsSelectionSort = readRecordsFromCsv(inputFilePath);
        Record[] recordsQuickSort = readRecordsFromCsv(inputFilePath);
        Record[] recordsMergeSort = readRecordsFromCsv(inputFilePath);

        // For every sorting algo, we will dynamically pass which field of the Record[] to srot

        // Perform Bubble Sort
        bubbleSort(recordsBubbleSort, "id");
        // Write sorted data to CSV file for Bubble Sort
        writeSortedDataToCsv(outputFilePathBubbleSort, recordsBubbleSort);

        // Perform Insertion Sort
        insertionSort(recordsInsertionSort, "id");
        // Write sorted data to CSV file for Insertion Sort
        writeSortedDataToCsv(outputFilePathInsertionSort, recordsInsertionSort);

        // Perform Selection Sort
        selectionSort(recordsSelectionSort, "id");
        // Write sorted data to CSV file for Selection Sort
        writeSortedDataToCsv(outputFilePathSelectionSort, recordsSelectionSort);

        // Perform Quick Sort
        quickSort(recordsQuickSort, "id");
        // Write sorted data to CSV file for Quick Sort
        writeSortedDataToCsv(outputFilePathQuickSort, recordsQuickSort);

        // Perform Merge Sort
        mergeSort(recordsMergeSort, "id");
        // Write sorted data to CSV file for Merge Sort
        writeSortedDataToCsv(outputFilePathMergeSort, recordsMergeSort);
    }

    // function to read the records from the csv file
    private static Record[] readRecordsFromCsv(String filePath) {
        Record[] records = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;

            // Count the number of lines in the file
            int numberOfLines = (int) br.lines().count();

            // Initialize the array with the number of lines
            records = new Record[numberOfLines];

            // Reset BufferedReader to read from the start
            try (BufferedReader br2 = new BufferedReader(new FileReader(filePath))) {
                // Read and parse each line
                while ((line = br2.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    // store the read values as Record objects and populate the array
                    records[index++] = new Record(id, name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    // function to perform Bubble Sort on an array of records
    static void bubbleSort(Record[] arr, String sortBy) {
        int n = arr.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (sortBy.equals("id") && arr[i - 1].id > arr[i].id) {
                    // Swap arr[i-1] and arr[i]
                    Record temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;
                    swapped = true;
                }
            }
        } while (swapped); // stop once the array is sorted
    }

    // function to perform Insertion Sort on an array of records
    static void insertionSort(Record[] arr, String sortBy) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            Record key = arr[i];
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than key to one position ahead of their current position
            while (j >= 0 && sortBy.equals("id") && arr[j].id > key.id) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // function to perform Selection Sort on an array of records
    static void selectionSort(Record[] arr, String sortBy) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (sortBy.equals("id") && arr[j].id < arr[minIdx].id) {
                    minIdx = j;
                }
            }
            // Swap arr[i] and arr[minIdx]
            Record temp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = temp;
        }
    }

    // function to perform Quick Sort on an array of records
    static void quickSort(Record[] arr, String sortBy) {
        quickSortRecurse(arr, 0, arr.length - 1, sortBy);
    }

    private static void quickSortRecurse(Record[] arr, int leftIdx, int rightIdx, String sortBy) {
        if (leftIdx < rightIdx) {
            int pivotIdx = doPartitioning(arr, leftIdx, rightIdx, sortBy);
            quickSortRecurse(arr, leftIdx, pivotIdx - 1, sortBy);
            quickSortRecurse(arr, pivotIdx + 1, rightIdx, sortBy);
        }
    }

    private static int doPartitioning(Record[] arr, int leftIdx, int rightIdx, String sortBy) {
        int pivotValue = getValue(arr[rightIdx], sortBy);
        int i = leftIdx - 1;

        for (int j = leftIdx; j < rightIdx; j++) {
            if (getValue(arr[j], sortBy) < pivotValue) {
                i++;
                // Swap arr[i] and arr[j]
                Record temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[rightIdx] to place the pivot in its correct position
        Record temp = arr[i + 1];
        arr[i + 1] = arr[rightIdx];
        arr[rightIdx] = temp;

        return i + 1;
    }

    private static int getValue(Record record, String sortBy) {
        return sortBy.equals("id") ? record.id : 0;
    }

    // function to perform Merge Sort on an array of records
    static void mergeSort(Record[] arr, String sortBy) {
        mergeSortRecurse(arr, 0, arr.length - 1, sortBy);
    }

    private static void mergeSortRecurse(Record[] arr, int leftIdx, int rightIdx, String sortBy) {
        if (leftIdx < rightIdx) {
            int midIdx = (leftIdx + rightIdx) / 2;
            mergeSortRecurse(arr, leftIdx, midIdx, sortBy);
            mergeSortRecurse(arr, midIdx + 1, rightIdx, sortBy);
            merge(arr, leftIdx, midIdx, rightIdx, sortBy);
        }
    }

    private static void merge(Record[] arr, int leftIdx, int midIdx, int rightIdx, String sortBy) {
        int n1 = midIdx - leftIdx + 1;
        int n2 = rightIdx - midIdx;

        Record[] L = new Record[n1];
        Record[] R = new Record[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[leftIdx + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[midIdx + 1 + j];
        }

        int i = 0, j = 0;
        int k = leftIdx;
        while (i < n1 && j < n2) {
            if (getValue(L[i], sortBy) <= getValue(R[j], sortBy)) {
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
    // This will create a new file if the file doesn't exist yet
    private static void writeSortedDataToCsv(String filePath, Record[] records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Record record : records) {
                writer.write(record.id + "," + record.name);
                writer.newLine();
            }
            // catch any error for better tracing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
