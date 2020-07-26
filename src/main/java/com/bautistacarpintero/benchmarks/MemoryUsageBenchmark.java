package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solutions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MemoryUsageBenchmark {

    public static void main(String[] args) {

        Scanner pauser = new Scanner(System.in);

        System.out.println("Press Any Key To Continue...");
        pauser.nextLine();


        long totalStart = System.currentTimeMillis();

        ArrayList<IProblemSolver> solvers = new ArrayList<>();
        ArrayList<String> solverNames = new ArrayList<>();

        solvers.add(new SolutionHashMapIndexes());
        solverNames.add("HashMapIndexes");

        solvers.add(new SolutionHashMapIndexes2());
        solverNames.add("HashMapIndexes2");

        solvers.add(new SolutionHashMapFrequencies());
        solverNames.add("HashMapFrequencies");

        solvers.add(new SolutionFastUtilsMapFrequencies());
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
