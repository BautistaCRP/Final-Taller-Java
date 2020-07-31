package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solvers.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TimesBenchmark {

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

        ArrayList<Solver> solvers = new ArrayList<>();
        ArrayList<String> solverNames = new ArrayList<>();

        solvers.add(new SolverBinarySearch());
        solverNames.add("BinarySearch");

        solvers.add(new SolverHashMapIndexes());
        solverNames.add("HashMapIndexes");

        solvers.add(new SolverHashMapIndexes2());
        solverNames.add("HashMapIndexes2");

        solvers.add(new SolverHashMapFrequencies());
        solverNames.add("HashMapFrequencies");

        solvers.add(new SolverFastUtilsMap());
        solverNames.add("FastUtilsMap");

        Benchmarks.timesBenchmark(100, 5, 5, problemSizes, solvers, solverNames);


        System.out.println();
        System.out.println();
        System.out.println();

        long totalTime = System.currentTimeMillis() - totalStart;
        System.out.println("Total time exe: " + totalTime);




    }
}
