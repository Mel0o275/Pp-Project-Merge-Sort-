
package Utils;

import Algorithms.ParallelMergeSort;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[][] testCases = {
                {},
                {5},
                {4, 2, 4, 1, 2},
                {1, 2, 3, 4, 5},
                {9, 7, 5, 3, 1},
                {8, 3, 5, 1, 9, 2, 7, 4}
        };

        ParallelMergeSort sorter =
                new ParallelMergeSort(128);

        try {

            for (int i = 0; i < testCases.length; i++) {

                int[] arr = testCases[i].clone();

                System.out.println("Test " + (i + 1));

                System.out.println(
                        "Before: " + Arrays.toString(arr)
                );

                sorter.sort(arr);

                System.out.println(
                        "After:  " + Arrays.toString(arr)
                );

                System.out.println(
                        "Is Sorted: " + isSorted(arr)
                );

                System.out.println("--------------------");
            }

        } finally {
            sorter.shutdown();
        }
    }

    private static boolean isSorted(int[] array) {

        for (int i = 1; i < array.length; i++) {

            if (array[i - 1] > array[i]) {
                return false;
            }
        }

        return true;
    }
}
