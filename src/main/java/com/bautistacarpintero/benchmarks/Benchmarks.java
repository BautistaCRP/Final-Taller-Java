package com.bautistacarpintero.benchmarks;

import com.bautistacarpintero.solvers.IProblemSolver;
import com.bautistacarpintero.solvers.Solver;
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

    public static void timesBenchmark(int problems, int warmup, int executions, int[] problemSizes, List<Solver> solvers, List<String> solversNames) {

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(RESOURCES_PATH + "timesBenchmark.csv"));


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
                            Solver solver = solvers.get(solverIndex);
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



    public static void memBenchmark(int problems, int[] bounds, int size, int executions, List<Solver> solvers, List<String> solversNames) {
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

    private static long getUsedMemory(){
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

}
