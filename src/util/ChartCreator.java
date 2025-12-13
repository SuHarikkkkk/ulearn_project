package util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class ChartCreator {

    //создаёт столбчатую диаграмму: Ось X: Фискальные годы, Ось Y: Сумма грантов
    public static void createGrantAmountByYearChart(
            java.util.Map<Integer, Double> data,
            String outputPath
    ) {
        // данные для диаграммы
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (var entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Сумма грантов", entry.getKey());
        }

        // создаем диаграмму
        JFreeChart chart = ChartFactory.createBarChart(
                "Сумма грантов по годам",
                "Фискальный год",
                "Сумма грантов ($)",
                dataset
        );
    }
}
