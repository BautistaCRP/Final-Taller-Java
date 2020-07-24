package com.bautistacarpintero.benchmarks;


public class Main {

    public static void main(String[] args) {

        int[] problemSizes =
                new int[]{5_000_000, 1_000_000, 500_000, 250_000, 100_000, 50_000};


        Benchmarks.sizesBenchmark(100, 5, 5, problemSizes);

//                int[] bounds =
//                new int[]{500_000};
//
//        Benchmarks.boundsBenchmark(10, 5, 5, bounds);


    }
}
