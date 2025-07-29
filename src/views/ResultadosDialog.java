package views;

import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import models.AlgorithmResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ResultadosDialog extends JDialog {
    public ResultadosDialog(JFrame parent) {
        super(parent, "Resultados históricos", true);

        AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
        List<AlgorithmResult> results = dao.getAllResults();

        String[] columnNames = {"Algoritmo", "Longitud", "Tiempo (ms)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (AlgorithmResult r : results) {
            Object[] row = {r.getAlgorithmName(), r.getPathLength(), r.getExecutionTime()};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton clearButton = new JButton("Limpiar resultados");
        clearButton.addActionListener(e -> {
            dao.clearResults();
            model.setRowCount(0);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(480, 360);
        setLocationRelativeTo(parent);
    }
}