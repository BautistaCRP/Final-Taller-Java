package com.bautistacarpintero.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoryChart extends ApplicationFrame {

    private Table memoryTable;


    public MemoryChart(String applicationTitle, String chartTitle, Table memoryTable) {
        super(applicationTitle);

        this.memoryTable = memoryTable;

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Solutions", "Memory in bytes",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);


        ChartStyle.makeItLookGoodMemoryChart(lineChart);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
        setContentPane(chartPanel);

    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        List<String> seriesNames = memoryTable.columnNames();

        seriesNames.remove("Bound");
        seriesNames.remove("Solutions");

        List<XYSeries> seriesList = new ArrayList<>();
        for (String name : seriesNames) {
            XYSeries series = new XYSeries(name);
            seriesList.add(series);
            dataset.addSeries(series);
        }

        for (Row row : memoryTable) {
            for (int seriesIndex = 0; seriesIndex < seriesList.size(); seriesIndex++) {
                seriesList.get(seriesIndex).add(row.getInt("Solutions"), row.getDouble(seriesNames.get(seriesIndex)));

            }
        }

        return dataset;
    }


    // Main ----------------------------------------------------------------------------------------------


    private static final String RESOURCES_PATH = "src/main/resources/";

    public static void main(String[] args) {

        String filePath = "memBenchmark.csv";
        if (args.length > 0) {
            String path = args[0];
            File file = new File(path);
            if (file.exists()) {
                filePath = path;
            }
        }

        try {
//            Table memoryTable = Table.read().csv(filePath);
            Table memoryTable = Table.read().csv(RESOURCES_PATH + "memBenchmark.csv");
            memoryTable = memoryTable.sortAscendingOn("Solutions");


            MemoryChart chart = new MemoryChart(
                    "Juan Bautista Carpintero - Final Taller Java",
                    "Consumo de memoria en funcion de la cantidad de soluciones",
                    memoryTable);

            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
