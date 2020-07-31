package com.bautistacarpintero.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolverBinarySearch extends Solver {


    @Override
    protected List<Pair> solve(int[] data, int target) {
        List<Pair> pairs = new ArrayList<>();
        Arrays.sort(data);
        for (int i = 0; i < data.length; i++) {
            int diff = target - data[i];
            Pair intervalPair = binarySearchInterval(data, diff);

            if (intervalPair != null)
                // Solo voy a tener en cuenta las diferencias encontradas en posiciones
                // anteriores a la actual, para descartar los pares simetricos

                if (intervalPair.getI() < i) {
                    int infLimit = intervalPair.getI();
                    int supLimit = intervalPair.getJ();
                    if (supLimit >= i)
                        supLimit = i - 1;

                    Pair pair = new Pair(data[i], diff);
                    for (int index = infLimit; index <= supLimit; index++) {
                        pairs.add(pair);
                    }
                }
        }
        return pairs;
    }


    public static Pair binarySearchInterval(int[] numbers, int target) {

        if (numbers == null)
            return null;

        int low = 0, high = numbers.length - 1;
        // busqueda binaria para encontrar el startIndex
        int startIndex = -1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;

            if (numbers[mid] > target) {
                high = mid - 1;
            } else if (numbers[mid] == target) {
                startIndex = mid;
                high = mid - 1;
            } else
                low = mid + 1;
        }

        // busqueda binaria para encontrar el endIndex
        int endIndex = -1;
        low = 0;
        high = numbers.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;

            if (numbers[mid] > target) {
                high = mid - 1;
            } else if (numbers[mid] == target) {
                endIndex = mid;
                low = mid + 1;
            } else
                low = mid + 1;
        }

        if (startIndex != -1 && endIndex != -1) {
            Pair out = new Pair(startIndex, endIndex);
            return out;
        }

        return null;
    }


}
