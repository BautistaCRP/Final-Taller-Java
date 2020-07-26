package com.bautistacarpintero.charts;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

import java.io.IOException;

public class TestMemLoadCSV {


    private static final String RESOURCES_PATH = "src/main/resources/";

    public static void main(String[] args) {


        try {
            Table memBenchmarkTable = Table.read().csv(RESOURCES_PATH + "memBenchmark.csv");
//            System.out.println(memBenchmarkTable);

            DoubleColumn col = memBenchmarkTable.doubleColumn("HashMapIndexes");

            System.out.println(col.print());
            System.out.println("max: "+col.max());
            System.out.println("min: "+col.min());
            System.out.println("mean: "+col.mean());
            System.out.println("median: "+col.median());
            System.out.println("standardDeviation: "+col.standardDeviation());




        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
