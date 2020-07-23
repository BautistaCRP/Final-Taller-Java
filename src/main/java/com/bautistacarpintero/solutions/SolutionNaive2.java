package com.bautistacarpintero.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class SolutionNaive2 implements IProblemSolver {

    private long lastTime = 0;

    public long getLastTime() {
        return lastTime;
    }

    public List<Pair> isSumIn(int[] data, int target) {

        long start = System.currentTimeMillis();

        List<Pair> pairs = new ArrayList<>();

        IntStream.range(0, data.length)
                .forEach(i -> IntStream.range(i + 1, data.length)
                        .filter(j -> i != j && data[i] + data[j] == target)
                        .forEach(j -> pairs.add(new Pair(data[i], data[j])))
                );

        this.lastTime = System.currentTimeMillis() - start;
        return pairs;
    }

}