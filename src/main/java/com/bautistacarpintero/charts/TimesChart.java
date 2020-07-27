package com.bautistacarpintero.charts;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.IOException;

import static tech.tablesaw.aggregate.AggregateFunctions.median;


public class TimesChart extends ApplicationFrame {

    private Table mediansTable;

    public TimesChart(String applicationTitle, String chartTitle, Table mediansTable, boolean logScale) {
        super(applicationTitle);
        this.mediansTable = mediansTable;

        String valueAxisLabel = "Time in ms";

        if (logScale)
            valueAxisLabel = "Time in ms (logarithmic scale)";

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Problem Size's", valueAxisLabel,
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);


        ChartStyle.makeItLookGoodSizeChart(lineChart, logScale);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Row row : mediansTable) {

            dataset.addValue(
                    row.getDouble("Median [HashMapIndexes]"),
                    "HashMapIndexes",
                    new Integer(row.getInt("Problem Size")));


            dataset.addValue(
                    row.getDouble("Median [HashMapIndexes2]"),
                    "HashMapIndexes2",
                    new Integer(row.getInt("Problem Size")));

            dataset.addValue(
                    row.getDouble("Median [HashMapFrequencies]"),
                    "HashMapFrequencies",
                    new Integer(row.getInt("Problem Size")));

            dataset.addValue(
                    row.getDouble("Median [FastUtilsMap]"),
                    "FastUtilsMap",
                    new Integer(row.getInt("Problem Size")));

        }

        return dataset;
    }

    private static final String RESOURCES_PATH = "src/main/resources/";


    public static void main(String[] args) {

        String filePath = "timesBenchmark.csv";
        if (args.length > 0) {
            String path = args[0];
            File file = new File(path);
            if (file.exists()) {
                filePath = path;
            }
        }

        try {
//            Table sizesBenchmarkTable = Table.read().csv(filePath);
            Table sizesBenchmarkTable = Table.read().csv(RESOURCES_PATH + "timesBenchmark.csv");

            Table mediansTable = sizesBenchmarkTable.summarize(
                    "HashMapIndexes",
                    "HashMapIndexes2",
                    "HashMapFrequencies",
                    "FastUtilsMap",
                    median)
                    .by("Problem Size");

            System.out.println(mediansTable);


            TimesChart chart = new TimesChart(
                    "Juan Bautista Carpintero - Final Taller Java",
                    "Medianas de los tiempos, agrupados por el tamaño de los problemas",
                    mediansTable,
                    false);

            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
