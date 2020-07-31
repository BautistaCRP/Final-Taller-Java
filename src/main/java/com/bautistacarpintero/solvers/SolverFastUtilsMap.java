package com.bautistacarpintero.solvers;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import java.util.ArrayList;
import java.util.List;

public class SolverFastUtilsMap extends Solver {


    @Override
    public List<Pair> solve(int[] data, int target) {

        List<Pair> pairs = new ArrayList<>();
        Int2IntMap frequencies = new Int2IntOpenHashMap();

        for (int i = 0; i < data.length; i++) {

            int diff = target - data[i];

            if (frequencies.containsKey(diff)) {
                int diffFreq = frequencies.get(diff);

                Pair pair = new Pair(data[i], diff);
                while (diffFreq > 0) {
                    pairs.add(pair);
                    diffFreq--;
                }
            }

            int freq = frequencies.get(data[i]);

            if (freq == 0)
                frequencies.put(data[i], 1);
            else
                frequencies.put(data[i], freq + 1);
        }

        return pairs;
    }

}