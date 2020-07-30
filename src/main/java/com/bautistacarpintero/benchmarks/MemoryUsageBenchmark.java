package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solvers.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MemoryUsageBenchmark {

    public static void main(String[] args) {

        Scanner pauser = new Scanner(System.in);

        System.out.println("Press Any Key To Continue...");
        pauser.nextLine();


        long totalStart = System.currentTimeMillis();

        ArrayList<Solver> solvers = new ArrayList<>();
        ArrayList<String> solverNames = new ArrayList<>();

        solvers.add(new SolverHashMapIndexes());
        solverNames.add("HashMapIndexes");

        solvers.add(new SolverHashMapIndexes2());
        solverNames.add("HashMapIndexes2");

        solvers.add(new SolverHashMapFrequencies());
        solverNames.add("HashMapFrequencies");

        solvers.add(new SolverFastUtilsMap());
        solverNames.add("FastUtilsMap");


        int[] bounds = new int[]{1_000_000, 500_000, 250_000, 100_000, 50_000, 10_000};

        Benchmarks.memBenchmark(100, bounds, 1_000_000, 5, solvers, solverNames);


        System.out.println();
        System.out.println();
        System.out.println();

        long totalTime = System.currentTimeMillis() - totalStart;
        System.out.println("Total time exe: " + totalTime);
    }
}
