package com.bautistacarpintero.benchmarks;


public class Main {

    public static void main(String[] args) {

        long totalStart = System.currentTimeMillis();

        int[] problemSizes = new int[]{5_000_000, 1_000_000, 500_000, 250_000, 100_000, 50_000};

        Benchmarks.sizesBenchmark(100, 5, 5, problemSizes);


        System.out.println();
        System.out.println();
        System.out.println();

        long totalTime = System.currentTimeMillis() - totalStart;
        System.out.println("Total time exe: "+totalTime);





//        int[] bounds = new int[]{500_000};
//        Benchmarks.boundsBenchmark(10, 5, 5, bounds);


    }
}
