package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.Cell;
import models.CellState;
import models.AlgorithmResult;
import solver.MazeSolver;
import solver.solverImpl.MazeSolverBFS;
import solver.solverImpl.MazeSolverDFS;
import solver.solverImpl.MazeSolverRecursivo;
import solver.solverImpl.MazeSolverRecursivoCompleto;
import solver.solverImpl.MazeSolverRecursivoCompletoBT;

public class MazeFrame extends JFrame {

    private MazePanel mazePanel;
    private JComboBox<String> algorithmComboBox;

    private List<Cell> stepPath;
    private int currentStep;
    private long stepStartTime = 0; 

    public MazeFrame(int numRows, int numCols) {
        super("Maze Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initComponents(numRows, numCols);
    }

    private void initComponents(int numRows, int numCols) {
        // Panel superior con botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton setStartButton = new JButton("Set Start");
        JButton setEndButton = new JButton("Set End");
        JButton toggleWallButton = new JButton("Toggle Wall");
        topPanel.add(setStartButton);
        topPanel.add(setEndButton);
        topPanel.add(toggleWallButton);
        add(topPanel, BorderLayout.NORTH);

        mazePanel = new MazePanel(numRows, numCols);
        add(new JScrollPane(mazePanel), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(new JLabel("Algoritmo:"));
        String[] algorithms = { "Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS" };
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setSelectedItem("Recursivo");
        bottomPanel.add(algorithmComboBox);

        JButton solveButton = new JButton("Resolver");
        JButton stepByStepButton = new JButton("Paso a paso");
        JButton clearButton = new JButton("Limpiar");
        bottomPanel.add(solveButton);
        bottomPanel.add(stepByStepButton);
        bottomPanel.add(clearButton);
        add(bottomPanel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(salirItem);
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem acercaDeItem = new JMenuItem("Acerca de...");
        acercaDeItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Creador de Laberintos v1.0",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        ayudaMenu.add(acercaDeItem);
        menuBar.add(archivoMenu);
        menuBar.add(ayudaMenu);
        setJMenuBar(menuBar);

        setStartButton.addActionListener(e -> {
            mazePanel.setInteractionMode(MazePanel.Interaction_Mode.SET_START);
        });

        setEndButton.addActionListener(e -> {
            mazePanel.setInteractionMode(MazePanel.Interaction_Mode.SET_END);
        });

        toggleWallButton.addActionListener(e -> {
            mazePanel.setInteractionMode(MazePanel.Interaction_Mode.TOGGLE_WALL);
        });

        solveButton.addActionListener(e -> {
            Cell[][] mazeData = mazePanel.getMazeData();
            int startRow = -1, startCol = -1, endRow = -1, endCol = -1;
            for (int r = 0; r < mazeData.length; r++) {
                for (int c = 0; c < mazeData[0].length; c++) {
                    if (mazeData[r][c].getState() == CellState.START) {
                        startRow = r; startCol = c;
                    }
                    if (mazeData[r][c].getState() == CellState.END) {
                        endRow = r; endCol = c;
                    }
                }
            }
            if (startRow == -1 || endRow == -1) {
                JOptionPane.showMessageDialog(this, "Debes establecer inicio y fin.");
                return;
            }
            MazeSolver solver = getSelectedSolver();
            long startTime = System.currentTimeMillis();
            List<Cell> path = solver.solve(mazeData, startRow, startCol, endRow, endCol);
            long endTime = System.currentTimeMillis();
            mazePanel.drawPath(path);

            AlgorithmResult result;
            if (path == null || path.isEmpty()) {
                result = new AlgorithmResult(solver.getName(), 0, endTime - startTime);
                JOptionPane.showMessageDialog(this, "No se encontró camino.\n" + result.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                result = new AlgorithmResult(solver.getName(), path.size(), endTime - startTime);
                JOptionPane.showMessageDialog(this, result.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        stepByStepButton.addActionListener(e -> {
            Cell[][] mazeData = mazePanel.getMazeData();
            int startRow = -1, startCol = -1, endRow = -1, endCol = -1;
            for (int r = 0; r < mazeData.length; r++) {
                for (int c = 0; c < mazeData[0].length; c++) {
                    if (mazeData[r][c].getState() == CellState.START) {
                        startRow = r; startCol = c;
                    }
                    if (mazeData[r][c].getState() == CellState.END) {
                        endRow = r; endCol = c;
                    }
                }
            }
            if (startRow == -1 || endRow == -1) {
                JOptionPane.showMessageDialog(this, "Debes establecer inicio y fin.");
                return;
            }
            if (stepPath == null) {
                MazeSolver solver = getSelectedSolver();
                stepStartTime = System.currentTimeMillis();
                stepPath = solver.solve(mazeData, startRow, startCol, endRow, endCol);
                currentStep = 0;
                for (Cell cell : stepPath) {
                    if (cell.getState() == CellState.PATH) {
                        cell.setState(CellState.EMPTY);
                    }
                }
                mazeData[startRow][startCol].setState(CellState.START);
                mazeData[endRow][endCol].setState(CellState.END);
            }
            if (stepPath != null && currentStep < stepPath.size()) {
                Cell cell = stepPath.get(currentStep);
                if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                    cell.setState(CellState.PATH);
                }
                mazePanel.repaint();
                currentStep++;
                if (currentStep == stepPath.size()) {
                    long stepEndTime = System.currentTimeMillis();
                    AlgorithmResult result = new AlgorithmResult(
                        getSelectedSolver().getName(),
                        stepPath.size(),
                        stepEndTime - stepStartTime
                    );
                    JOptionPane.showMessageDialog(this, "Camino terminado.\n" + result.toString(), "Paso a paso", JOptionPane.INFORMATION_MESSAGE);
                    stepPath = null;
                    currentStep = 0;
                }
            }
        });

        clearButton.addActionListener(e -> {
            mazePanel.clearMaze();
            stepPath = null;
            currentStep = 0;
        });

        algorithmComboBox.addActionListener(e -> {
            stepPath = null;
            currentStep = 0;
        });
    }

    private MazeSolver getSelectedSolver() {
        String selected = (String) algorithmComboBox.getSelectedItem();
        switch (selected) {
            case "Recursivo":
                return new MazeSolverRecursivo();
            case "Recursivo Completo":
                return new MazeSolverRecursivoCompleto();
            case "Recursivo Completo BT":
                return new MazeSolverRecursivoCompletoBT();
            case "BFS":
                return new MazeSolverBFS();
            case "DFS":
                return new MazeSolverDFS();
            default:
                return new MazeSolverRecursivo();
        }
    }
}