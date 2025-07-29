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
        setSize(600, 400);
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
            int barWidth = (width - 2 * margin) / results.size();
            long maxTime = results.stream().mapToLong(AlgorithmResult::getExecutionTime).max().orElse(1);

            g.drawLine(margin, height - margin, width - margin, height - margin); // eje X
            g.drawLine(margin, margin, margin, height - margin); // eje Y

            // Etiquetas de ejes
            g.drawString("Algoritmo", width / 2 - 30, height - 20);
            g.drawString("Tiempo (ms)", 10, margin - 10);

            for (int i = 0; i < results.size(); i++) {
                AlgorithmResult r = results.get(i);
                int barHeight = (int) ((r.getExecutionTime() * 1.0 / maxTime) * (height - 2 * margin - 20));
                int x = margin + i * barWidth + 10;
                int y = height - margin - barHeight;

                g.setColor(new Color(100, 150, 255));
                g.fillRect(x, y, barWidth - 20, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth - 20, barHeight);

                String label = r.getAlgorithmName();
                g.drawString(label, x, height - margin + 15);

                g.drawString(String.valueOf(r.getExecutionTime()), x, y - 5);
            }
        }
    }
}