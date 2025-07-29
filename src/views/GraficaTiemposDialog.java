package views;

import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import models.AlgorithmResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraficaTiemposDialog extends JDialog {
    public GraficaTiemposDialog(JFrame parent) {
        super(parent, "Gráfica de tiempos por algoritmo", true);

        AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
        List<AlgorithmResult> results = dao.getAllResults();

        add(new BarChartPanel(results));
        setSize(1920, 1080);
        setLocationRelativeTo(parent);
    }

    static class BarChartPanel extends JPanel {
        private final List<AlgorithmResult> results;

        public BarChartPanel(List<AlgorithmResult> results) {
            this.results = results;
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
            int margin = 60;
            int barSpacing = 10;
            int numBars = results.size();
            int availableWidth = width - 2 * margin - (numBars - 1) * barSpacing;
            int barWidth = Math.max(availableWidth / numBars, 1);
            long maxTime = results.stream().mapToLong(AlgorithmResult::getExecutionTime).max().orElse(1);

            g.drawLine(margin, height - margin, width - margin, height - margin); 
            g.drawLine(margin, margin, margin, height - margin);

            g.drawString("Algoritmo", width / 2 - 30, height - 20);
            g.drawString("Tiempo (ms)", 10, margin - 10);

            FontMetrics fm = g.getFontMetrics();

            for (int i = 0; i < numBars; i++) {
                AlgorithmResult r = results.get(i);
                int barHeight = (int) ((r.getExecutionTime() * 1.0 / maxTime) * (height - 2 * margin - 20));
                int x = margin + i * (barWidth + barSpacing);
                int y = height - margin - barHeight;

                g.setColor(new Color(100, 150, 255));
                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);

                String label = r.getAlgorithmName();
                int labelWidth = fm.stringWidth(label);
                g.drawString(label, x + (barWidth - labelWidth) / 2, height - margin + 15);

                String timeStr = String.valueOf(r.getExecutionTime());
                int timeWidth = fm.stringWidth(timeStr);
                g.drawString(timeStr, x + (barWidth - timeWidth) / 2, y - 5);
            }
        }

    }
}