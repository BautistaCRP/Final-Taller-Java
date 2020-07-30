package com.bautistacarpintero.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class SolverNaive2 extends Solver {

    public List<Pair> solve(int[] data, int target) {

        List<Pair> pairs = new ArrayList<>();

        IntStream.range(0, data.length)
                .forEach(i -> IntStream.range(i + 1, data.length)
                        .filter(j -> i != j && data[i] + data[j] == target)
                        .forEach(j -> pairs.add(new Pair(data[i], data[j])))
                );

        return pairs;
    }

}