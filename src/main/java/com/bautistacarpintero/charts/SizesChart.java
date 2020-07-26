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

import java.io.IOException;

import static tech.tablesaw.aggregate.AggregateFunctions.median;


public class SizesChart extends ApplicationFrame {

    private Table mediansTable;

    public SizesChart(String applicationTitle, String chartTitle, Table mediansTable) {
        super(applicationTitle);
        this.mediansTable = mediansTable;

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Sizes", "Time in ms",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);


        ChartStyle.makeItLookGoodSizeChart(lineChart);
        ChartPanel chartPanel = new ChartPanel(lineChart);
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


    // TODO Ver si puedo poner dos graficos con las dos escalas
    public static void main(String[] args) {
        try {
            Table sizesBenchmarkTable = Table.read().csv(RESOURCES_PATH + "sizesBenchmark.csv");

            Table mediansTable = sizesBenchmarkTable.summarize(
                    "HashMapIndexes",
                    "HashMapIndexes2",
                    "HashMapFrequencies",
                    "FastUtilsMap",
                    median)
                    .by("Problem Size");

            System.out.println(mediansTable);


            SizesChart chart = new SizesChart(
                    "Juan Bautista Carpintero - Final Taller Java",
                    "Medianas de los tiempos agrupados por size",
                    mediansTable);

            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
