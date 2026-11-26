
package Utils;

import Algorithms.ParallelMergeSort;
import Algorithms.SequentialMergeSort;

import java.util.Arrays;

public class SortBenchmark {

    private static final int RUNS = 10;
    private static final int WARMUP_RUNS = 3;
    private static final int THRESHOLD = 128;

    private static final int[] SIZES = {
            10_000,
            100_000,
            1_000_000
    };

    public static void main(String[] args) {

        SequentialMergeSort sequential =
                new SequentialMergeSort();

        ParallelMergeSort parallel =
                new ParallelMergeSort(THRESHOLD);

        try {

            for (int size : SIZES) {

                benchmarkPattern(
                        "Random",
                        ArrayGenerator.generateRandomArray(size),
                        sequential,
                        parallel
                );

                benchmarkPattern(
                        "Reverse Sorted",
                        ArrayGenerator.generateReverseSortedArray(size),
                        sequential,
                        parallel
                );
            }

        } finally {
            parallel.shutdown();
        }
    }

    private static void benchmarkPattern(
            String pattern,
            int[] originalArray,
            SequentialMergeSort sequential,
            ParallelMergeSort parallel
    ) {

        System.out.println("\n================================");
        System.out.println("Pattern: " + pattern);
        System.out.println("Array Size: " + originalArray.length);
        System.out.println("================================");

        // Warm-up
        for (int i = 0; i < WARMUP_RUNS; i++) {

            int[] arr1 = originalArray.clone();
            int[] arr2 = originalArray.clone();
            int[] arr3 = originalArray.clone();

            sequential.sort(arr1);
            parallel.sort(arr2);
            Arrays.sort(arr3);
        }

        long sequentialTotal = 0;
        long parallelTotal = 0;
        long builtInTotal = 0;

        // Benchmark 3l4an n avoid lw el cpu fayd el alw b3d kda et4a8al f b run kol mara wa7ed mnhm el awel 3l4an tkon fair
        for (int i = 0; i < RUNS; i++) {

            int order = i % 3;

            if (order == 0) {

                sequentialTotal += measureSequential(
                        originalArray, sequential
                );

                parallelTotal += measureParallel(
                        originalArray, parallel
                );

                builtInTotal += measureBuiltIn(
                        originalArray
                );

            } else if (order == 1) {

                parallelTotal += measureParallel(
                        originalArray, parallel
                );

                builtInTotal += measureBuiltIn(
                        originalArray
                );

                sequentialTotal += measureSequential(
                        originalArray, sequential
                );

            } else {

                builtInTotal += measureBuiltIn(
                        originalArray
                );

                sequentialTotal += measureSequential(
                        originalArray, sequential
                );

                parallelTotal += measureParallel(
                        originalArray, parallel
                );
            }
        }

        double sequentialAverage =
                sequentialTotal / (RUNS * 1_000_000.0); // to convert to milliSecond from nano , ya habib alby ya nanoo :D

        double parallelAverage =
                parallelTotal / (RUNS * 1_000_000.0);

        double builtInAverage =
                builtInTotal / (RUNS * 1_000_000.0);

        double speedup =
                sequentialAverage / parallelAverage;

        System.out.printf(
                "Sequential Merge Sort: %.3f ms%n",
                sequentialAverage
        );

        System.out.printf(
                "Parallel Merge Sort:   %.3f ms%n",
                parallelAverage
        );

        System.out.printf(
                "Arrays.sort():         %.3f ms%n",
                builtInAverage
        );

        System.out.printf(
                "Parallel Speedup:      %.2fx%n",
                speedup
        );
    }

    private static long measureSequential(
            int[] originalArray,
            SequentialMergeSort algorithm
    ) {

        int[] arr = originalArray.clone();

        long start = System.nanoTime();

        algorithm.sort(arr);

        long end = System.nanoTime();

        validateResult(arr, originalArray);

        return end - start;
    }

    private static long measureParallel(
            int[] originalArray,
            ParallelMergeSort algorithm
    ) {

        int[] arr = originalArray.clone();

        long start = System.nanoTime();

        algorithm.sort(arr);

        long end = System.nanoTime();

        validateResult(arr, originalArray);

        return end - start;
    }

    private static long measureBuiltIn(
            int[] originalArray
    ) {

        int[] arr = originalArray.clone();

        long start = System.nanoTime();

        Arrays.sort(arr);

        long end = System.nanoTime();

        validateResult(arr, originalArray);

        return end - start;
    }

    private static void validateResult(
            int[] result,
            int[] originalArray
    ) {

        int[] expected = originalArray.clone();

        Arrays.sort(expected);

        if (!Arrays.equals(result, expected)) {
            throw new IllegalStateException(
                    "Sorting result is incorrect!"
            );
        }
    }
}
