package com.bautistacarpintero.solutions;

import java.util.ArrayList;
import java.util.List;



public class SolutionNaive implements IProblemSolver {

    private long lastTime = 0;
    public long getLastTime() {
        return lastTime;
    }

	public List<Pair> isSumIn(int[] data, int target) {

        long start = System.currentTimeMillis();

		List<Pair> pairs = new ArrayList<>();
		
        for (int i = 0; i < data.length; i++) 
            for (int j = i + 1; j < data.length; j++) 
                if ((data[i] + data[j]) == target)
                    pairs.add(new Pair(data[i],data[j]));

        this.lastTime = System.currentTimeMillis() - start;
		return pairs;
	}

}
