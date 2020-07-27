package com.bautistacarpintero.charts;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChartStyle {

    public static void makeItLookGoodSizeChart(JFreeChart sizeChart) {

//        fixAxis(sizeChart);
        applyChartFont(sizeChart);
        addMargin(sizeChart);

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(54, 120, 210));
        colors.add(new Color(253, 133, 20));
        colors.add(new Color(57, 173, 82));
        colors.add(new Color(231, 32, 36));
        colors.add(new Color(157, 84, 206));
        colors.add(new Color(38, 229, 183));
        colors.add(new Color(148, 139, 61));

        int bgColor = 242;
        int bgPlotColor = 252;

        sizeChart.setBackgroundPaint(new Color(bgColor, bgColor, bgColor));
        sizeChart.getPlot().setBackgroundPaint(new Color(bgPlotColor, bgPlotColor, bgPlotColor));

        CategoryPlot categoryPlot = sizeChart.getCategoryPlot();
        int seriesCount = categoryPlot.getCategories().size();
        for (int i = 0; i < seriesCount; i++) {
            categoryPlot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
            categoryPlot.getRenderer().setSeriesPaint(i, colors.get(i % colors.size()));
        }

        categoryPlot.setDomainGridlinesVisible(true);
        categoryPlot.setRangeGridlinesVisible(true);
        categoryPlot.setRangeGridlinePaint(Color.gray);
        categoryPlot.setDomainGridlinePaint(Color.gray);

    }


    public static void makeItLookGoodMemoryChart(JFreeChart sizeChart) {

        applyChartFont(sizeChart);
        addMargin(sizeChart);

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(54, 120, 210));
        colors.add(new Color(253, 133, 20));
        colors.add(new Color(57, 173, 82));
        colors.add(new Color(231, 32, 36));
        colors.add(new Color(157, 84, 206));
        colors.add(new Color(38, 229, 183));
        colors.add(new Color(148, 139, 61));

        int bgColor = 242;
        int bgPlotColor = 252;

        sizeChart.setBackgroundPaint(new Color(bgColor, bgColor, bgColor));
        sizeChart.getPlot().setBackgroundPaint(new Color(bgPlotColor, bgPlotColor, bgPlotColor));

        XYPlot xyPlot = sizeChart.getXYPlot();
        int seriesCount = xyPlot.getSeriesCount();
        for (int i = 0; i < seriesCount; i++) {
            xyPlot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
            xyPlot.getRenderer().setSeriesPaint(i, colors.get(i % colors.size()));
        }

        xyPlot.setDomainGridlinesVisible(true);
        xyPlot.setRangeGridlinesVisible(true);
        xyPlot.setRangeGridlinePaint(Color.gray);
        xyPlot.setDomainGridlinePaint(Color.gray);

    }

    private static void addMargin(JFreeChart chart) {
        RectangleInsets chartRectangle = new RectangleInsets(16f, 16f, 16f, 16f);
        //RectangleInsets chartRectangle = new RectangleInsets(TOP,LEFT,BOTTOM,RIGHT);
        chart.setPadding(chartRectangle);
    }

    private static void applyChartFont(JFreeChart chart) {
        final StandardChartTheme chartTheme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();

        final Font oldExtraLargeFont = chartTheme.getExtraLargeFont();
        final Font oldLargeFont = chartTheme.getLargeFont();
        final Font oldRegularFont = chartTheme.getRegularFont();
        final Font oldSmallFont = chartTheme.getSmallFont();


        String fontName = "Arial";
        final Font extraLargeFont = new Font(fontName, oldExtraLargeFont.getStyle(), 16);
        final Font largeFont = new Font(fontName, oldLargeFont.getStyle(), oldLargeFont.getSize());
        final Font regularFont = new Font(fontName, oldRegularFont.getStyle(), oldRegularFont.getSize());
        final Font smallFont = new Font(fontName, oldSmallFont.getStyle(), oldSmallFont.getSize());

        chartTheme.setExtraLargeFont(extraLargeFont);
        chartTheme.setLargeFont(largeFont);
        chartTheme.setRegularFont(regularFont);
        chartTheme.setSmallFont(smallFont);


        chartTheme.apply(chart);
    }


    private static void fixAxis(JFreeChart chart){
        CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis oldAxis = plot.getRangeAxis();
        LogAxis yAxis = new LogAxis(oldAxis.getLabel());
        yAxis.setAutoTickUnitSelection(false);
        yAxis.setMinorTickCount(9);  // changing the integer argument has no effect on chart
        yAxis.setBase(10);
        yAxis.setTickMarksVisible(true);
        yAxis.setAxisLineVisible(true);
        yAxis.setAutoRange(true);

        plot.setRangeAxis(yAxis);
    }


}
