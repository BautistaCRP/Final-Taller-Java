package com.bautistacarpintero.solvers;

import com.bautistacarpintero.solvers.IProblemSolver.Pair;
import com.bautistacarpintero.utilities.ProblemGen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MainVerifySolutions {

    public static boolean verifySolution(List<Pair> solutionNaive, List<Pair> solutionToVerify) {

        if (solutionNaive.size() != solutionToVerify.size()) {
            System.out.println("Solution invalid! - Different sizes");
            return false;
        }


        List<Pair> solution1 = new ArrayList<>(solutionNaive);
        List<Pair> solution2 = new ArrayList<>(solutionToVerify);

        while (!solution1.isEmpty() && !solution2.isEmpty()) {
            Pair pair = solution1.get(0);
            Pair symmetricPair = new Pair(pair.getJ(), pair.getI());

            Predicate<Pair> predicate = new Predicate<Pair>() {
                @Override
                public boolean test(Pair p) {
                    return (p.equals(pair) || p.equals(symmetricPair));
                }
            };

            solution1.removeIf(predicate);
            solution2.removeIf(predicate);

            if (solution1.size() != solution2.size()) {
                System.out.println("Solution invalid! - After remove " + pair + " and " + symmetricPair);
                return false;
            }
        }

        if (solution1.size() != solution2.size()) {
            System.out.println("Solution invalid! - Different sizes after clean");
            return false;
        } else if (solution1.isEmpty() && solution2.isEmpty()) {
            System.out.println("Solution VALID!");
            return true;
        }


        System.out.println("Solution invalid! - At the end");
        return false;
    }


    public static void main(String[] args) {

        Solver solverNaive = new SolverNaive();

        List<Solver> solvers = new ArrayList<>();
        solvers.add(new SolverHashMapIndexes());
        solvers.add(new SolverHashMapIndexes2());
        solvers.add(new SolverHashMapFrequencies());
        solvers.add(new SolverFastUtilsMap());
        solvers.add(new SolverBinarySearch());


        boolean solutionsOk = true;

        for (int i = 0; i < 100; i++) {
            System.out.println("Problem: " + i);
            System.out.println();
            ProblemGen problemGenerator = new ProblemGen();
            problemGenerator.genRandomBoundedProblem(100_000, 500);
            int target = problemGenerator.getTarget();
            int[] data = problemGenerator.getData();

            List<Pair> validPairs = solverNaive.isSumIn(data, target);

            System.out.println();
            System.out.println("Target: " + target);
            System.out.println("Solutions: " + validPairs.size());
            System.out.println();

            boolean printPairs = false;

            for (Solver solver : solvers) {
                List<Pair> pairs = solver.isSumIn(data, target);
                System.out.println("Solver: " + solver.getClass().getSimpleName());
                if (printPairs) {
                    pairs.sort(Pair::compareTo);
                    System.out.println("Pairs:  " + pairs);
                }
                solutionsOk = verifySolution(validPairs, pairs);
                System.out.println();
            }

            if (!solutionsOk)
                break;

            System.out.println("----------------------------------------------------------------------------------------");

        }


        System.out.println();
        System.out.println("Finish!!!");
        if(solutionsOk)
            System.out.println("Solutions are valid!");
        else
            System.out.println("Oops something went wrong D:");

//        target = 6;
//        data = new int[]{-1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 7};
//
//        SolverNaive solverNaive = new SolverNaive();
//        SolverBinarySearch solverBinary = new SolverBinarySearch();
//
//        List<Pair> naivePairs = solverNaive.isSumIn(data, target);
//        List<Pair> binaryPairs = solverBinary.isSumIn(data, target);
//
//        System.out.println("naivePairs: " + naivePairs);
//        System.out.println("binaryPairs: " + binaryPairs);
//
//        System.out.println();
//
//        verifySolution(naivePairs, binaryPairs);


    }


}
