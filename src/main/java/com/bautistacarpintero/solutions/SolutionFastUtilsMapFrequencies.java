package com.bautistacarpintero.solutions;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import java.util.ArrayList;
import java.util.List;

public class SolutionFastUtilsMapFrequencies implements IProblemSolver {

    private long lastTime = 0;

    @Override
    public List<Pair> isSumIn(int[] data, int target) {

        long start = System.currentTimeMillis();

        List<Pair> pairs = new ArrayList<>();
        Int2IntMap frequencies = new Int2IntOpenHashMap();


        for (int i = 0; i < data.length; i++) {
            Integer freq = frequencies.get(data[i]);

            if (freq == null)
                frequencies.put(data[i], 1);
            else
                frequencies.put(data[i], freq + 1);

            int diff = target - data[i];

            if (frequencies.containsKey(diff)) {
                int diffFreq = frequencies.get(diff);

                Pair pair = new Pair(data[i], diff);
                while (diffFreq > 0) {
                    pairs.add(pair);
                    diffFreq--;
                }

            }
        }

        this.lastTime = System.currentTimeMillis() - start;
        return pairs;
    }

    @Override
    public long getLastTime() {
        return this.lastTime;
    }

}