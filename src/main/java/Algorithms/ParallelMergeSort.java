
package Algorithms;

import Intrfaces.SortAlgorithm;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort implements SortAlgorithm {

    private int threshold; // a2l 3dd for elements in array
    private ForkJoinPool pool; //

    public ParallelMergeSort(int threshold) {
        this.threshold = threshold;
        this.pool = new ForkJoinPool();
    }

    @Override
    public void sort(int[] array) {

        if (array == null || array.length <= 1) {
            return;
        }

        MergeSortTask task =
                new MergeSortTask(array, 0, array.length - 1, threshold);

        pool.invoke(task);
    }

    private static class MergeSortTask extends RecursiveAction {

        private int[] array;
        private int left;
        private int right;
        private int threshold;

        public MergeSortTask(
                int[] array,
                int left,
                int right,
                int threshold
        ) {
            this.array = array;
            this.left = left;
            this.right = right;
            this.threshold = threshold;
        }

        @Override
        protected void compute() { // from fork

            if (right - left + 1 <= threshold) { // b4of lw h2sm wla wsl ll threshold w lw wsl 5las h3ml sequential
                sequentialMergeSort(array, left, right);
                return;
            }

            int mid = left + (right - left) / 2;

            MergeSortTask leftTask =
                    new MergeSortTask(array, left, mid, threshold);

            MergeSortTask rightTask =
                    new MergeSortTask(array, mid + 1, right, threshold);

            invokeAll(leftTask, rightTask);

            merge(array, left, mid, right);
        }

        private void sequentialMergeSort(
                int[] array, int left, int right
        ) {
            if (left >= right) return;

            int mid = left + (right - left) / 2;

            sequentialMergeSort(array, left, mid);
            sequentialMergeSort(array, mid + 1, right);

            merge(array, left, mid, right);
        }

        private void merge(
                int[] array, int left, int mid, int right
        ) {
            int n1 = mid - left + 1;
            int n2 = right - mid;

            int[] leftArray = new int[n1];
            int[] rightArray = new int[n2];

            for (int i = 0; i < n1; i++) {
                leftArray[i] = array[left + i];
            }

            for (int j = 0; j < n2; j++) {
                rightArray[j] = array[mid + 1 + j];
            }

            int i = 0, j = 0, k = left;

            while (i < n1 && j < n2) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k++] = leftArray[i++];
                } else {
                    array[k++] = rightArray[j++];
                }
            }

            while (i < n1) {
                array[k++] = leftArray[i++];
            }

            while (j < n2) {
                array[k++] = rightArray[j++];
            }
        }
    }

    public void shutdown() {
        pool.shutdown(); // 3l4an m5ls4 el mawared
    }
}
