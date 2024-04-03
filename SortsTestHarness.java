import java.util.Arrays;

public class SortsTestHarness {

    private static final int REPEATS = 4;
    private static final double NEARLY_PERCENT = 0.10;
    private static final int RANDOM_TIMES = 100;

    private static void usage() {
        System.out.println(" Usage: java TestHarness n xy [xy ...]");
        System.out.println("        where");
        System.out.println("        n is number of integers to sort");
        System.out.println("        x is one of");
        System.out.println("           b - bubblesort");
        System.out.println("           i - insertion sort");
        System.out.println("           s - selection sort");
        System.out.println("           q - quicksort");
        System.out.println("           x - quicksort with median-of-three pivot");
        System.out.println("           r - quicksort with random pivot");
        System.out.println("           m - mergesort");
        System.out.println("        y is one of");
        System.out.println("           a - 1..n ascending");
        System.out.println("           d - 1..n descending");
        System.out.println("           r - 1..n in random order");
        System.out.println("           n - 1..n nearly sorted (10% moved)");
    }

    public static void main(String[] args) {
        int n;
        char sortType, arrayType;
        if (args.length < 2)
            usage();
        else {
            n = Integer.parseInt(args[0]);
            int[] A = new int[n];
            for (int numSorts = 1; numSorts < args.length; numSorts++) {
                sortType = args[numSorts].charAt(0);
                arrayType = args[numSorts].charAt(1);

                double runningTotal = 0;
                for (int repeat = 0; repeat < REPEATS; repeat++) {

                    for (int i = 0; i < n; i++)
                        A[i] = i + 1;

                    switch (arrayType) {
                        case 'a':
                            break; // already ascending!
                        case 'd':
                            for (int i = 0; i < n / 2; i++)
                                swap(A, i, n - i - 1);
                            break;
                        case 'r':
                            for (int i = 0; i < RANDOM_TIMES * n; i++) {
                                int x = (int) Math.floor(Math.random() * (n - 1));
                                int y = (int) Math.floor(Math.random() * (n - 1));
                                swap(A, x, y);
                            }
                            break;
                        case 'n':
                            for (int i = 0; i < n * NEARLY_PERCENT / 2.0; i++) {
                                int x = (int) Math.round(Math.random() * (n - 1));
                                int y = (int) Math.round(Math.random() * (n - 1));
                                swap(A, x, y);
                            }
                            break;
                        default:
                            System.err.println("Unsupported array type " + arrayType);
                    }

                    long startTime = System.nanoTime();
                    switch (sortType) {
                        case 'b':
                            Sorts.bubbleSort(A);
                            break;
                        case 's':
                            Sorts.selectionSort(A);
                            break;
                        case 'q':
                            Sorts.quickSort(A);
                            break;
                        case 'x':
                            Sorts.quickSortMedian3(A);
                            break;
                        case 'r':
                            Sorts.quickSortRandom(A);
                            break;
                        case 'm':
                            Sorts.mergeSort(A);
                            break;
                        case 'i':
                            Sorts.insertionSort(A);
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported sort type " + sortType);
                    }
                    long endTime = System.nanoTime();

                    if (repeat == 0) {
                        for (int i = 1; i < A.length; i++) {
                            if (A[i] < A[i - 1])
                                throw new IllegalStateException("Array is not in sorted order! At element: " + i);
                        }
                    } else {
                        runningTotal += (int) ((double) (endTime - startTime) / 1000.0);
                    }
                }
                System.out.print(args[numSorts] + " " + n);
                System.out.println(" " + (runningTotal / (REPEATS - 1)));
            }
        }
    }

    private static void swap(int[] A, int idx1, int idx2) {
        int temp = A[idx1];
        A[idx1] = A[idx2];
        A[idx2] = temp;
    }
}
