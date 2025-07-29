package views;

import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import models.AlgorithmResult;

public class GraficaTiemposDialog extends JDialog {

    public enum ChartType {
        TIME,
        PATH_LENGTH
    }

    public GraficaTiemposDialog(JFrame parent) {
        super(parent, "Gráfica de resultados por algoritmo", true);

        AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
        List<AlgorithmResult> results = dao.getAllResults();

        ChartType currentChartType;
        long maxTime = results.stream().mapToLong(AlgorithmResult::getExecutionTime).max().orElse(0);

        if (maxTime < 1) {
            currentChartType = ChartType.PATH_LENGTH;
            setTitle("Gráfica de longitud de camino por algoritmo");
        } else {
            currentChartType = ChartType.TIME;
            setTitle("Gráfica de tiempos por algoritmo");
        }

        add(new BarChartPanel(results, currentChartType));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        setSize(dm.getWidth(), dm.getHeight());
        setLocationRelativeTo(null);
    }

    static class BarChartPanel extends JPanel {
        private final List<AlgorithmResult> results;
        private final ChartType chartType;

        public BarChartPanel(List<AlgorithmResult> results, ChartType chartType) {
            this.results = results;
            this.chartType = chartType;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (results == null || results.isEmpty()) {
                g.drawString("No hay datos para mostrar.", 50, 50);
                return;
            }

            int width = getWidth();
            int height = getHeight();
            int margin = 80;
            int barSpacing = 10;

            List<AlgorithmResult> displayResults;
            long maxValue = 1;

            if (chartType == ChartType.TIME) {
                displayResults = results.stream()
                                    .filter(r -> r.getExecutionTime() > 0)
                                    .collect(Collectors.toList());
                maxValue = results.stream().mapToLong(AlgorithmResult::getExecutionTime).max().orElse(1);
            } else {
                displayResults = results.stream()
                                    .filter(r -> r.getPathLength() > 0)
                                    .collect(Collectors.toList());
                maxValue = results.stream().mapToInt(AlgorithmResult::getPathLength).max().orElse(1);
            }
            
            if (displayResults.isEmpty()) {
                 g.drawString("No hay datos válidos para la gráfica de " + 
                              (chartType == ChartType.TIME ? "tiempos." : "longitud de camino."), 50, 50);
                 return;
            }

            int numBars = displayResults.size();
            int availableWidth = width - 2 * margin - (numBars > 1 ? (numBars - 1) * barSpacing : 0);
            int barWidth = Math.max(availableWidth / numBars, 1);

            g.drawLine(margin, height - margin, width - margin, height - margin);
            g.drawLine(margin, margin, margin, height - margin);

            g.drawString("Algoritmo", width / 2 - 30, height - 20);
            
            String yAxisLabel = (chartType == ChartType.TIME) ? "Tiempo (ms)" : "Longitud del camino (celdas)";
            g.drawString(yAxisLabel, 10, margin - 10);

            FontMetrics fm = g.getFontMetrics();

            for (int i = 0; i < numBars; i++) {
                AlgorithmResult r = displayResults.get(i);
                long currentValue;
                String valueLabel;

                if (chartType == ChartType.TIME) {
                    currentValue = r.getExecutionTime();
                    valueLabel = String.valueOf(currentValue);
                } else {
                    currentValue = r.getPathLength();
                    valueLabel = String.valueOf(currentValue);
                }
                
                if (maxValue == 0) maxValue = 1; 

                int barHeight = (int) ((currentValue * 1.0 / maxValue) * (height - 2 * margin - 20));
                
                if (currentValue > 0 && barHeight == 0) barHeight = 1;

                int x = margin + i * (barWidth + barSpacing);
                int y = height - margin - barHeight;

                g.setColor(new Color(100, 150, 255));
                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);

                String label = r.getAlgorithmName().replaceAll("\\s*\\(.*?\\)", "").trim();
                int labelWidth = fm.stringWidth(label);
                g.drawString(label, x + (barWidth - labelWidth) / 2, height - margin + 15);

                int valueLabelWidth = fm.stringWidth(valueLabel);
                g.drawString(valueLabel, x + (barWidth - valueLabelWidth) / 2, y - 5);
            }
        }
    }
}