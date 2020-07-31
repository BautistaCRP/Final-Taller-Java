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
import java.util.List;

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

        List<String> colNames = mediansTable.columnNames();
        colNames.remove("Problem Size");

        for (Row row : mediansTable) {

            for(String colName : colNames){
                String solverName = colName.replace("Median [","");
                solverName = solverName.replace("]","");
                dataset.addValue(
                        row.getDouble(colName),
                        solverName,
                        new Integer(row.getInt("Problem Size")));
            }

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
//            Table timesBenchmarkTable = Table.read().csv(filePath);
            Table timesBenchmarkTable = Table.read().csv(RESOURCES_PATH + "timesBenchmark.csv");

            List<String> solverNames = timesBenchmarkTable.columnNames();
            solverNames.remove("Problem Size");
            solverNames.remove("Solutions");

            Table mediansTable = timesBenchmarkTable.summarize(
                    solverNames,
                    median)
                    .by("Problem Size");

            System.out.println(mediansTable);


            TimesChart chart = new TimesChart(
                    "Juan Bautista Carpintero - Final Taller Java",
                    "Medianas de los tiempos, agrupados por el tamaño de los problemas",
                    mediansTable,
                    true);

            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
