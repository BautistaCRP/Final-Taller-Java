package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solutions.IProblemSolver;
import com.bautistacarpintero.solutions.SolutionFastUtilsMapFrequencies;
import com.bautistacarpintero.solutions.SolutionHashMapFrequencies;
import com.bautistacarpintero.utilities.ProblemGen;
import com.bautistacarpintero.utilities.Ranking;
import com.bautistacarpintero.utilities.RankingElem;

import java.util.List;

public class MainTestFastUitils {

    public static void main(String[] args) {


        ProblemGen problemGen = new ProblemGen();
        problemGen.genRandomBoundedProblem(200_000, 10000);
        int target = problemGen.getTarget();
        int[] data = problemGen.getData();
        List<IProblemSolver.Pair> pairs;

        RankingElem hashMapRank = new RankingElem(  "  HashMap Solution");
        RankingElem fastUtilsRank = new RankingElem("FastUtils Solution");

        Ranking ranking = new Ranking();
        ranking.addElem(hashMapRank);
        ranking.addElem(fastUtilsRank);

        SolutionHashMapFrequencies hashMapFrequencies = new SolutionHashMapFrequencies();
        SolutionFastUtilsMapFrequencies fastUtilsMapFrequencies = new SolutionFastUtilsMapFrequencies();

        int warmup = 20;
        int problems = 100;
        for (int i = 0; i < problems; i++) {
            problemGen.genRandomBoundedProblem(200_000, 1000);
            target = problemGen.getTarget();
            data = problemGen.getData();

            for (int j = 0; j < warmup; j++) {

                System.out.println("Warm Up: "+(j+1)+"/"+warmup+" - Problem: "+(i+1)+"/"+problems+"\n");

                pairs = hashMapFrequencies.isSumIn(data, target);
                long hashMapFrequenciesLastTime = hashMapFrequencies.getLastTime();
                hashMapRank.setTime(hashMapFrequenciesLastTime);
                System.out.println("HashMap      - Solution size: "+pairs.size());
                pairs = null;
                System.gc();


                pairs = fastUtilsMapFrequencies.isSumIn(data, target);
                long fastUtilsMapFrequenciesLastTime = fastUtilsMapFrequencies.getLastTime();
                fastUtilsRank.setTime(fastUtilsMapFrequenciesLastTime);
                System.out.println("FastUtilsMap - Solution size: "+pairs.size());
                pairs = null;
                System.gc();



                System.out.println();
                System.out.println(ranking.getTimes());
                System.out.println(ranking.getScores());
                System.out.println("-----------------------------------");

            }

        }

        System.out.println();
        System.out.println("Finish!!");
        System.out.println();
        System.out.println(ranking.getScores());
    }
}
