package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solutions.*;
import com.bautistacarpintero.utilities.ProblemGen;
import com.bautistacarpintero.utilities.Ranking;
import com.bautistacarpintero.utilities.RankingElem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Benchmarks {

    private static final String RESOURCES_PATH = "src/main/resources/";

    public static void sizesBenchmark(int problems, int warmup, int executions, int[] problemSizes, List<IProblemSolver> solvers, List<String> solversNames) {

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(RESOURCES_PATH + "sizesBenchmark.csv"));


            String header = "Problem Size, Solutions";
            for (String solverName : solversNames) {
                header = header+", "+solverName;
            }
            header += "\n";
            writer.write(header);


            // Ranking - Es para ver en tiempo de exe un ranking de como van las ejecuciones (ganan puntaje en funcion de los tiempos)

            Ranking ranking = new Ranking();
            ArrayList<RankingElem> rankingElems = new ArrayList<>();

            for(String solverName : solversNames){
                RankingElem elem = new RankingElem(solverName);
                ranking.addElem(elem);
                rankingElems.add(elem);
            }

            ProblemGen problemGen = new ProblemGen();

            for (int problemSize : problemSizes) {

                int target;
                int[] data;


                for (int i = 0; i < problems; i++) {
                    problemGen.genRandomBoundedProblem(problemSize, problemSize * 2);
                    target = problemGen.getTarget();
                    data = problemGen.getData();
                    long solutions = -1;

                    System.out.println("Problem Size: " + problemSize + " - Problem: " + (i + 1) + "/" + problems);

                    // Primera etapa - Warm up

                    System.out.println("Warm Up Stage");
                    for (int j = 0; j < warmup; j++) {

                        // Se ejecuntan las soluciones warmup cantidad de veces
                        for (IProblemSolver solver : solvers){
                            if(solutions == -1) {
                                List<IProblemSolver.Pair> pairs = solver.isSumIn(data, target);
                                solutions = pairs.size();
                            } else
                                solver.isSumIn(data, target);
                            System.gc();
                        }
                    }


                    // Segunda etapa - Ejecucion

                    double[] avgs = new double[solvers.size()];
                    for (int j = 0; j < avgs.length; j++) {
                        avgs[j] = 0.0d;
                    }

                    System.out.println("Execution Stage \n");
                    for (int j = 0; j < executions; j++) {

                        System.out.println("Execution: " + (j + 1) + "/" + executions + " - Problem: " + (i + 1) + "/" + problems);
                        System.out.println("Problem Size: " + problemSize);
                        System.out.println("Solutions: " + solutions);

                        for (int solverIndex = 0; solverIndex < solvers.size(); solverIndex++) {
                            IProblemSolver solver = solvers.get(solverIndex);
                            solver.isSumIn(data,target);
                            System.gc();

                            long lastTime = solver.getLastTime();
                            rankingElems.get(solverIndex).setTime(lastTime);
                            avgs[solverIndex] += lastTime;
                        }

                        System.out.println();
                        System.out.println(ranking.getTimes());
                        System.out.println(ranking.getScores());
                        System.out.println("-----------------------------------");

                    }

                    // Tercer etapa - Calculo de promedios de la exe y guardado

                    for (int j = 0; j < avgs.length; j++) {
                        avgs[j] = avgs[j] / (double) executions;
                    }

                    StringBuilder builder = new StringBuilder();
                    builder
                            .append(problemSize)
                            .append(",")
                            .append(solutions);

                    for (int solverIndex = 0; solverIndex < solvers.size(); solverIndex++) {
                        builder.append(",")
                                .append(avgs[solverIndex]);
                    }
                    builder.append("\n");

                    writer.write(builder.toString());
                    System.out.println("Record Saved!");
                    System.out.println("\t -> Problem Size: " + problemSize);
                    System.out.println("\t -> Solutions: " + solutions);
                    for (int solverIndex = 0; solverIndex < solvers.size(); solverIndex++) {
                        System.out.println("\t -> "+solversNames.get(solverIndex)+": " + avgs[solverIndex]);

                    }

                    System.out.println();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println();
                }

                writer.flush();
            }


            writer.flush();
            writer.close();


            System.out.println();
            System.out.println("Finish!!");
            System.out.println();
            System.out.println(ranking.getScores());


        } catch (IOException e) {
            e.printStackTrace();
            /*
                java.io.IOException – if the named file exists but is a directory rather
                than a regular file, does not exist but cannot be created, or cannot be
                opened for any other reason
             */
        }

    }



    public static void memBenchmark(int problems, int[] bounds, int size, int executions, List<IProblemSolver> solvers, List<String> solversNames) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(RESOURCES_PATH + "memBenchmark.csv"));


            String header = "Bound, Solutions";
            for (String solverName : solversNames) {
                header = header+", "+solverName;
            }
            header += "\n";
            writer.write(header);


            ProblemGen problemGen = new ProblemGen();
            int[] data;
            int target;

            double[] memTotal = new double[solvers.size()];
            for (int i = 0; i < memTotal.length; i++) {
                memTotal[i] = 0;
            }

            for(int bound : bounds){
                for (int problem = 0; problem < problems; problem++) {
                    problemGen.genRandomBoundedProblem(size,bound);
                    data = problemGen.getData();
                    target = problemGen.getTarget();

                    System.out.println("------------------------------------------");
                    System.out.println();
                    System.out.println("Problem: "+problem+"/"+problems+" - Bound: "+bound);

                    double[] memoryUsages = new double[solvers.size()];
                    int solutions = -1;

                    for (int solverIndex = 0; solverIndex < solvers.size(); solverIndex++) {

                        IProblemSolver solver = solvers.get(solverIndex);
                        double memoryUsageAVG = 0;

                        for (int execution = 0; execution < executions; execution++) {

                            long memoryBefore = getUsedMemory();
                            List<IProblemSolver.Pair> pairs = solver.isSumIn(data,target);
                            long memoryUsage = getUsedMemory() - memoryBefore;

                            if(solutions == -1) {
                                solutions = pairs.size();
                                System.out.println("Solutions: "+solutions);
                                System.out.println("Memory Usage: ");
                            }

                            if(memoryUsage < 0){
                                // Paso el gc y termine con menos uso de mem con el que arranque
                                // descarto esta ejecucion y la realizo denuevo
                                execution--;

                            } else {
                                memoryUsageAVG += memoryUsage;
                            }

                            System.runFinalization();
                            System.gc();

                        } // for execution

                        memoryUsageAVG = memoryUsageAVG / (double) executions;
                        memoryUsages[solverIndex] = memoryUsageAVG;
                        System.out.println(solversNames.get(solverIndex)+": "+memoryUsageAVG);


                    } // for solver


                    StringBuilder builder = new StringBuilder();
                    builder.append(bound)
                            .append(",")
                            .append(solutions);

                    for(double memoryUsage : memoryUsages){
                        builder.append(",")
                                .append(memoryUsage);
                    }
                    builder.append("\n");

                    writer.write(builder.toString());
                    System.out.println();

                    for (int i = 0; i < memoryUsages.length; i++) {
                        memTotal[i] += memoryUsages[i];
                    }

                } // for problem
                writer.flush();

            } // for bound

            writer.flush();
            writer.close();


            System.out.println("---------------------------------------------------------------");
            System.out.println();
            System.out.println();
            System.out.println("Finish!");

            for (int solverIndex = 0; solverIndex < solvers.size(); solverIndex++) {
                System.out.println(solversNames.get(solverIndex)+": "+memTotal[solverIndex]);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO eliminar estooo ⬇⬇⬇
    public static void boundsBenchmark(int problems, int warmup, int executions, int[] bounds) {

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(RESOURCES_PATH + "boundsBenchmark.csv"));


            String header = "Bound, Solutions, HashMapIndexes, HashMapIndexes2, HashMapFrequencies, FastUtilsMap\n";
            writer.write(header);

            // Solutions
            SolutionHashMapIndexes hashMapIndexes = new SolutionHashMapIndexes();
            SolutionHashMapIndexes2 hashMapIndexes2 = new SolutionHashMapIndexes2();
            SolutionHashMapFrequencies hashMapFrequencies = new SolutionHashMapFrequencies();
            SolutionFastUtilsMapFrequencies fastUtilsMapFrequencies = new SolutionFastUtilsMapFrequencies();


            // Ranking - Es para ver en tiempo de exe un ranking de como van las ejecuciones (ganan puntaje en funcion de los tiempos)

            RankingElem hashMapIndexesRank = new RankingElem("    HashMapIndexes Solution");
            RankingElem hashMapIndexes2Rank = new RankingElem("   HashMapIndexes2 Solution");
            RankingElem hashMapFrequenciesRank = new RankingElem("HashMapFrequencies Solution");
            RankingElem fastUtilsMapRank = new RankingElem("      FastUtilsMap Solution");

            Ranking ranking = new Ranking();
            ranking.addElem(hashMapIndexesRank);
            ranking.addElem(hashMapIndexes2Rank);
            ranking.addElem(hashMapFrequenciesRank);
            ranking.addElem(fastUtilsMapRank);

            ProblemGen problemGen = new ProblemGen();

            for (int bound : bounds) {

                int target;
                int[] data;

                for (int i = 0; i < problems; i++) {
                    problemGen.genRandomBoundedProblem(1_000_000, bound);
                    target = problemGen.getTarget();
                    data = problemGen.getData();
                    long solutions = -1;

                    System.out.println("Bound : " + bound + " - Problem: " + (i + 1) + "/" + problems);

                    // Primera etapa - Warm up


                    System.out.println("Warm Up Stage");
                    for (int j = 0; j < warmup; j++) {

                        // Se ejecuntan las soluciones warmup cantidad de veces
                        hashMapIndexes.isSumIn(data, target);
                        System.gc();

                        hashMapIndexes2.isSumIn(data, target);
                        System.gc();

                        hashMapFrequencies.isSumIn(data, target);
                        System.gc();

                        if (j != 0)
                            fastUtilsMapFrequencies.isSumIn(data, target);
                        else {
                            List<IProblemSolver.Pair> pairs;
                            pairs = fastUtilsMapFrequencies.isSumIn(data, target);
                            solutions = pairs.size();
                        }
                        System.gc();
                    }


                    // Segunda etapa - Ejecucion

                    double hashMapIndexesAvg = 0.0d;
                    double hashMapIndexes2Avg = 0.0d;
                    double hashMapFrequenciesAvg = 0.0d;
                    double fastUtilsMapFrequenciesAvg = 0.0d;

                    System.out.println("Execution Stage \n");
                    for (int j = 0; j < executions; j++) {

                        System.out.println("Execution: " + (j + 1) + "/" + executions + " - Problem: " + (i + 1) + "/" + problems);
                        System.out.println("Bound: " + bound);
                        System.out.println("Solutions: " + solutions);

                        hashMapIndexes.isSumIn(data, target);
                        System.gc();

                        hashMapIndexes2.isSumIn(data, target);
                        System.gc();

                        hashMapFrequencies.isSumIn(data, target);
                        System.gc();

                        fastUtilsMapFrequencies.isSumIn(data, target);
                        System.gc();

                        long hashMapIndexesLastTime = hashMapIndexes.getLastTime();
                        hashMapIndexesRank.setTime(hashMapIndexesLastTime);

                        long hashMapIndexes2LastTime = hashMapIndexes2.getLastTime();
                        hashMapIndexes2Rank.setTime(hashMapIndexes2LastTime);

                        long hashMapFrequenciesLastTime = hashMapFrequencies.getLastTime();
                        hashMapFrequenciesRank.setTime(hashMapFrequenciesLastTime);

                        long fastUtilsMapFrequenciesLastTime = fastUtilsMapFrequencies.getLastTime();
                        fastUtilsMapRank.setTime(fastUtilsMapFrequenciesLastTime);

                        hashMapIndexesAvg += hashMapIndexesLastTime;
                        hashMapIndexes2Avg += hashMapIndexes2LastTime;
                        hashMapFrequenciesAvg += hashMapFrequenciesLastTime;
                        fastUtilsMapFrequenciesAvg += fastUtilsMapFrequenciesLastTime;

                        System.out.println();
                        System.out.println(ranking.getTimes());
                        System.out.println(ranking.getScores());
                        System.out.println("-----------------------------------");

                    }

                    // Tercer etapa - Calculo de promedios de la exe y guardado
                    hashMapIndexesAvg = hashMapIndexesAvg / (double) executions;
                    hashMapIndexes2Avg = hashMapIndexes2Avg / (double) executions;
                    hashMapFrequenciesAvg = hashMapFrequenciesAvg / (double) executions;
                    fastUtilsMapFrequenciesAvg = fastUtilsMapFrequenciesAvg / (double) executions;

                    StringBuilder builder = new StringBuilder();
                    builder
                            .append(bound)
                            .append(",")
                            .append(solutions)
                            .append(",")
                            .append(hashMapIndexesAvg)
                            .append(",")
                            .append(hashMapIndexes2Avg)
                            .append(",")
                            .append(hashMapFrequenciesAvg)
                            .append(",")
                            .append(fastUtilsMapFrequenciesAvg)
                            .append("\n");

                    writer.write(builder.toString());
                    System.out.println("Record Saved!");
                    System.out.println("\t -> Bound: " + bound);
                    System.out.println("\t -> Solutions: " + solutions);
                    System.out.println("\t -> hashMapIndexesAvg: " + hashMapIndexesAvg);
                    System.out.println("\t -> hashMapIndexes2Avg: " + hashMapIndexes2Avg);
                    System.out.println("\t -> hashMapFrequenciesAvg: " + hashMapFrequenciesAvg);
                    System.out.println("\t -> fastUtilsMapFrequenciesAvg: " + fastUtilsMapFrequenciesAvg);

                    System.out.println();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println();

                }

                writer.flush();
            }


            writer.flush();
            writer.close();


            System.out.println();
            System.out.println("Finish!!");
            System.out.println();
            System.out.println(ranking.getScores());


        } catch (IOException e) {
            e.printStackTrace();
            /*
                java.io.IOException – if the named file exists but is a directory rather
                than a regular file, does not exist but cannot be created, or cannot be
                opened for any other reason
             */
        }

    }

    private static long getUsedMemory(){
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

}
