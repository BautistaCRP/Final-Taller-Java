package com.bautistacarpintero.charts;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChartStyle {

    public static void makeItLookGood(JFreeChart chart) {

        applyChartFont(chart);

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

        chart.setBackgroundPaint(new Color(bgColor, bgColor, bgColor));
        chart.getPlot().setBackgroundPaint(new Color(bgPlotColor, bgPlotColor, bgPlotColor));

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        int seriesCount = categoryPlot.getCategories().size();
        for (int i = 0; i < seriesCount; i++) {
            categoryPlot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
            categoryPlot.getRenderer().setSeriesPaint(i, colors.get(i % colors.size()));
        }

        categoryPlot.setDomainGridlinesVisible(true);
        categoryPlot.setRangeGridlinesVisible(true);
        categoryPlot.setRangeGridlinePaint(Color.gray);
        categoryPlot.setDomainGridlinePaint(Color.gray);

        addMargin(chart);
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


}
