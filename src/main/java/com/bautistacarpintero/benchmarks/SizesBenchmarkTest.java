package com.bautistacarpintero.benchmarks;

import java.util.Scanner;

public class SizesBenchmarkTest {

    public static void main(String[] args) {

        Scanner pauser = new Scanner (System.in);

        System.out.println("Press Any Key To Continue...");
        pauser.nextLine();


        long totalStart = System.currentTimeMillis();

        int[] problemSizes = new int[]{
                50_000,
                100_000,
                250_000,
                500_000,
                1_000_000,
                5_000_000
        };


        Benchmarks.sizesBenchmark(100, 5, 5, problemSizes);


        System.out.println();
        System.out.println();
        System.out.println();

        long totalTime = System.currentTimeMillis() - totalStart;
        System.out.println("Total time exe: " + totalTime);




    }
}
