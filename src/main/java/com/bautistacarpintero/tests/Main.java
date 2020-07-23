package com.bautistacarpintero.tests;


import com.bautistacarpintero.solutions.IProblemSolver;
import com.bautistacarpintero.solutions.IProblemSolver.Pair;
import com.bautistacarpintero.solutions.SolutionHashMapIndexes;
import com.bautistacarpintero.solutions.SolutionHashMapIndexes2;
import com.bautistacarpintero.utilities.ProblemGen;

import java.util.List;

public class Main {

    /*
        Dado un arreglo de enteros y un número target, encontrar los
        pares de enteros en el arreglo, cuya suma es igual a target.
     */


    public static void main(String[] args) {


        int target = 11;
        int[] data = new int[] {10, 12, 10, 15, -1, 7, 6, 5, 4, 2, 1, 1, 1};

        /*
        Solution
            [(10,1), (10,1), (10,1),
            (12,-1), (10,1), (10,1),
            (10,1), (7,4), (6,5)]
         */

        ProblemGen problemGen = new ProblemGen();
        problemGen.genRandomBoundedProblem(200_000, 10000);
        target = problemGen.getTarget();
        data = problemGen.getData();

        IProblemSolver problemSolver;
        List<Pair> pairs;


        double indexesScore = 0;
        double indexes2Score = 0;
//        double freqScore = 0;

        SolutionHashMapIndexes solutionHashMapIndexes = new SolutionHashMapIndexes();
        SolutionHashMapIndexes2 solutionHashMapIndexes2 = new SolutionHashMapIndexes2();
//        SolutionHashMapFrequencies solutionHashMapFrequencies = new SolutionHashMapFrequencies();


        double diffs = 0;
        int warmup = 10;
        int problems = 10;
        for (int i = 0; i < problems; i++) {
            problemGen.genRandomBoundedProblem(200_000, 1000);
            target = problemGen.getTarget();
            data = problemGen.getData();

            for (int j = 0; j < warmup; j++) {

                pairs = solutionHashMapIndexes.isSumIn(data, target);
                long indexesTime = solutionHashMapIndexes.getLastTime();
                System.out.println("solutionHashMapIndexes : "+indexesTime);

                System.gc();


                pairs = solutionHashMapIndexes2.isSumIn(data, target);
                long indexes2Time = solutionHashMapIndexes2.getLastTime();
                System.out.println("solutionHashMapIndexes2: "+indexes2Time);

                System.gc();

                if(indexesTime < indexes2Score)
                    indexesScore++;
                else
                    indexes2Score++;

                double diff = indexesTime - indexes2Time;
                if(diff<0)
                    diff = - diff;
                diffs += diff;
                System.out.println("diff: "+diff);
                System.out.println();

//                pairs = solutionHashMapFrequencies.isSumIn(data, target);
//                long freqTime = solutionHashMapFrequencies.getLastTime();
//                System.out.println("SolutionHashMapFrequencies:");
//                System.out.println("Length: " + pairs.size());
//                System.out.println("Time milis: " + freqTime);
//                System.out.println();

//                long min = Math.min(indexesTime, indexes2Time);
//                min = Math.min(min, freqTime);
//
//                long max = Math.max(indexesTime, indexes2Time);
//                max = Math.min(max, freqTime);
//
//
//                if (freqTime == max)
//                    freqScore++;
//                else if (indexes2Time == max)
//                        indexes2Score++;
//                    else
//                        indexesScore++;
//
//                if (freqTime != max && freqTime != min)
//                    freqScore += 0.5d;
//
//                if (indexes2Time != max && indexes2Time != min)
//                    indexes2Score += 0.5d;
//
//                if (indexesTime != max && indexesTime != min)
//                    indexesScore += 0.5d;


                System.out.println("---");
            }
        }


        System.out.println("------------------------------------------------------------");
        System.out.println("indexesScore: " + indexesScore);
        System.out.println("indexes2Score: " + indexes2Score);

        double avgDiffs = diffs/(warmup*problems);
        System.out.println("AVG diff: " + avgDiffs);
//        System.out.println("freqScore: " + freqScore);


    }
}
