package com.bautistacarpintero.solvers;

import java.util.List;

public abstract class Solver implements IProblemSolver {

    private long lastTime = 0;

    public long getLastTime() {
        return lastTime;
    }

    public List<Pair> isSumIn(int[] data, int target){

        long start = System.currentTimeMillis();
        List<Pair> pairs = this.solve(data,target);
        this.lastTime = System.currentTimeMillis() - start;

        return pairs;
    }

    protected abstract List<Pair> solve(int[] data, int target);
}
