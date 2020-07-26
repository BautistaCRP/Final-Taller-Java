package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solutions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class SizesBenchmark {

    public static void main(String[] args) {

        Scanner pauser = new Scanner (System.in);

        System.out.println("Press Any Key To Continue...");
        pauser.nextLine();


        long totalStart = System.currentTimeMillis();

        int[] problemSizes = new int[]{
                50_000
        };

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

        Benchmarks.sizesBenchmark(100, 5, 5, problemSizes, solvers, solverNames);


        System.out.println();
        System.out.println();
        System.out.println();

        long totalTime = System.currentTimeMillis() - totalStart;
        System.out.println("Total time exe: " + totalTime);




    }
}
