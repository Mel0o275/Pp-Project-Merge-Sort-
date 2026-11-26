package Algorithms;

import Intrfaces.SortAlgorithm;

// arr = [8, 3, 5, 1]
public class SequentialMergeSort implements SortAlgorithm {

    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        mergeSort(array, 0, array.length - 1);
        // mergeSort(arr, left:0, right:3)
    }

    // ------------------------------------------------------------------
    // Optional hooks used by the visualizer. They do nothing by default,
    // so the algorithm behaves exactly the same when they are not overridden.
    // ------------------------------------------------------------------

    protected void onSplit(int[] array, int left, int mid, int right) {}

    protected void onWrite(
            int[] array, int left, int mid, int right, int k
    ) {}

    protected void onMerged(int[] array, int left, int mid, int right) {}

    protected void mergeSort(int[] array, int left, int right) { // left = 0, right = 3

        if (left == right) return; // (arr, 0, 0), (arr, 1, 1), (arr, 2, 2), (arr, 3, 3)
        // mergeSort(arr, 0, 0) -> return
        // mergeSort(arr, 1, 1) -> return
        // mergeSort(arr, 2, 2) -> return
        // mergeSort(arr, 3, 3) -> return

        int mid = left + (right - left) / 2; // mid = 1, mid = 0, mid = 2 + 0 = 2
        // mid = 0 + (3-0)/2 = 1
        // mid = 0 + (1-0)/2 = 0
        // mid = 2 + (3-2)/2 = 2

        onSplit(array, left, mid, right);

        mergeSort(array, left, mid); // (arr, 0, 1), (arr, 0, 0), (arr, 2, 2)
        // mergeSort(arr, left:0, right:1)
        // mergeSort(arr, left:0, right:0) -> return
        // mergeSort(arr, left:2, right:2) -> return

        mergeSort(array, mid + 1, right); // (arr, 1, 1), (arr, 2, 3), (arr, 3, 3)
        // mergeSort(arr, left:1, right:1) -> return
        // mergeSort(arr, left:3, right:3) -> return

        merge(array, left, mid, right); // merge(0, 0, 1), merge(2, 3, 3), merge(0, 1, 3)
        // merge(arr, 0, 0, 1)
        // merge(arr, 2, 2, 3)
        // merge(arr, 0, 1, 3)
    }

    protected void merge(int[] array, int left, int mid, int right) {

        // First Merge:
        // arr = [8, 3, 5, 1]
        // left = 0, mid = 0, right = 1

        // Second Merge:
        // arr = [3, 8, 5, 1]
        // left = 2, mid = 2, right = 3

        // Final Merge:
        // arr = [3, 8, 1, 5]
        // left = 0, mid = 1, right = 3

        int n1 = mid - left + 1;
        // n1 = 0 - 0 + 1 = 1
        // n1 = 2 - 2 + 1 = 1
        // n1 = 0 - 0 + 1 = 2

        int n2 = right - mid;
        // n2 = 1 - 0 = 1
        // n2 = 3 - 2 = 1
        // n2 = 3 - 1 = 2

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        // leftArray = [8]
        // leftArray = [5]
        // leftArray = [3, 8]

        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }
        // rightArray = [3]
        // rightArray = [1]
        // rightArray = [1, 5]

        int i = 0;
        int j = 0;
        int k = left;

        // First Merge:
        // i = 0, j = 0, k = 0
        // 8 <= 3 ? false -> array[0] = 3
        // j = 1, k = 1
        // array[1] = 8
        // i = 1, k = 2
        // Result: [3, 8, 5, 1]

        // Second Merge:
        // i = 0, j = 0, k = 2
        // 5 <= 1 ? false -> array[2] = 1
        // j = 1, k = 3
        // array[3] = 5
        // i = 1, k = 4
        // Result: [3, 8, 1, 5]

        // Final Merge:
        // i = 0, j = 0, k = 0
        // 3 <= 1 ? false -> array[0] = 1
        // j = 1, k = 1
        // 3 <= 5 ? true -> array[1] = 3
        // i = 1, k = 2
        // 8 <= 5 ? false -> array[2] = 5
        // j = 2, k = 3
        // array[3] = 8
        // i = 2, k = 4
        // Final Result: [1, 3, 5, 8]

        while (i < n1 && j < n2) {

            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }

            onWrite(array, left, mid, right, k);
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            onWrite(array, left, mid, right, k);
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            onWrite(array, left, mid, right, k);
            k++;
        }

        onMerged(array, left, mid, right);
    }
}