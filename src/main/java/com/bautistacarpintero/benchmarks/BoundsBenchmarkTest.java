package com.bautistacarpintero.benchmarks;

public class BoundsBenchmarkTest {
    public static void main(String[] args) {

        long totalStart = System.currentTimeMillis();

        int[] bounds = new int[]{1_000_000,500_000,250_000,100_000,50_000,10_000};
        int[] bounds2 = new int[]{1000}; //TODO ver si no se rompe la pc
        Benchmarks.boundsBenchmark(10, 5, 5, bounds2);


        System.out.println();
        System.out.println();
        System.out.println();

        long totalTime = System.currentTimeMillis() - totalStart;
        System.out.println("Total time exe: " + totalTime);

    }
}
