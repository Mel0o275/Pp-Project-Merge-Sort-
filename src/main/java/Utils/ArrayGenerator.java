
package Utils;

import java.util.Random;

public class ArrayGenerator {

    public static int[] generateRandomArray(int size) {

        int[] array = new int[size];

        Random random = new Random(42);

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1_000_000);
        }

        return array;
    }

    public static int[] generateReverseSortedArray(int size) {

        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }

        return array;
    }
}
