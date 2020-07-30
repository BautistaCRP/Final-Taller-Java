package com.bautistacarpintero.charts;

import com.bautistacarpintero.solvers.*;
import com.bautistacarpintero.solvers.IProblemSolver.Pair;
import com.bautistacarpintero.utilities.ProblemGen;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean checkSolution(List<Pair> solutionPairs, List<Pair> toCheckList) {

        List<Pair> solution = new ArrayList<>(solutionPairs);

        List<Pair> toCheckSymmetric = new ArrayList<>();

        for (Pair pair : toCheckList) {
            boolean removed = solution.remove(pair);
            if (!removed)
                toCheckSymmetric.add(pair);
        }

        if (solution.isEmpty()) {
            System.out.println("Solution valid! - Same pairs");
            return true;
        }

        for(Pair pair : toCheckSymmetric) {
            Pair symmetricPair = new Pair(pair.getJ(), pair.getI());
            boolean removed = solution.remove(symmetricPair);
//            if(!removed){
//                System.out.println("symmetricPair "+symmetricPair+" was not removed!!!");
//            }
        }

        if (solution.isEmpty()) {
            System.out.println("Solution valid! - Symmetric pairs were found");
            return true;
        }


        System.out.println("Solution invalid!");
        return false;
    }


    public static void main(String[] args) {

        int target = 8;
        int[] data = new int[]{4, 6, 1, 6, 1, 4, 8, 6, 2, 4, 6, 2, 4, 5, 2, 4, 1, 5, 2, 4, 5, 5, 1, 7, 9, 0, 1, 4, 8, 9, 9, 0};

        ProblemGen problemGenerator = new ProblemGen();
        problemGenerator.genRandomBoundedProblem(10_000, 500);
        target =  problemGenerator.getTarget();
        data =  problemGenerator.getData();

        IProblemSolver solverNaive1 = new SolverNaive();
        IProblemSolver solverNaive2 = new SolverNaive2();
        IProblemSolver solver1 = new SolverHashMapIndexes();
        IProblemSolver solver2 = new SolverHashMapIndexes2();
        IProblemSolver solver3 = new SolverHashMapFrequencies();
        IProblemSolver solver4 = new SolverFastUtilsMap();

        List<Pair> naivePairs1 = solverNaive1.isSumIn(data, target);
        List<Pair> naivePairs2 = solverNaive2.isSumIn(data, target);
        List<Pair> pairs1 = solver1.isSumIn(data, target);
        List<Pair> pairs2 = solver2.isSumIn(data, target);
        List<Pair> pairs3 = solver3.isSumIn(data, target);
        List<Pair> pairs4 = solver4.isSumIn(data, target);

//        naivePairs1.sort(IProblemSolver.Pair::compareTo);
//        naivePairs2.sort(IProblemSolver.Pair::compareTo);
//        pairs1.sort(IProblemSolver.Pair::compareTo);
//        pairs2.sort(IProblemSolver.Pair::compareTo);
//        pairs3.sort(IProblemSolver.Pair::compareTo);
//        pairs4.sort(IProblemSolver.Pair::compareTo);
//
//        System.out.println("naive1:  " + naivePairs1);
//        System.out.println("naive2:  " + naivePairs2);
//        System.out.println("solver1: " + pairs1);
//        System.out.println("solver2: " + pairs2);
//        System.out.println("solver3: " + pairs3);
//        System.out.println("solver4: " + pairs4);

        System.out.println();
        System.out.println("Target: "+target);
        System.out.println("Solutions: "+naivePairs1.size());
        System.out.println();


        checkSolution(naivePairs1,naivePairs2);
        checkSolution(naivePairs1,pairs1);
        checkSolution(naivePairs1,pairs2);
        checkSolution(naivePairs1,pairs3);
        checkSolution(naivePairs1,pairs4);


    }
}
