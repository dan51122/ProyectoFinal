package controllers;

import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import models.AlgorithmResult;
import models.Cell;
import models.CellState;
import solver.MazeSolver;
import solver.solverImpl.MazeSolverBFS;
import solver.solverImpl.MazeSolverDFS;
import solver.solverImpl.MazeSolverRecursivo;
import solver.solverImpl.MazeSolverRecursivoCompleto;
import solver.solverImpl.MazeSolverRecursivoCompletoBT;
import views.GraficaTiemposDialog;
import views.MazeFrame;
import views.MazePanel;
import views.ResultadosDialog;

public class MazeController {

    private MazeFrame mazeFrame;
    private MazePanel mazePanel;

    private Timer stepTimer;
    private List<Cell> stepPath;
    private int currentStep;
    private boolean isStepping = false;
    private long stepStartTime;

    public MazeController(MazeFrame mazeFrame, MazePanel mazePanel) {
        this.mazeFrame = mazeFrame;
        this.mazePanel = mazePanel;
    }

    public void setInteractionMode(MazePanel.Interaction_Mode mode) {
        mazePanel.setInteractionMode(mode);
        clearCurrentPath();
    }

    public void showResults() {
        new ResultadosDialog(mazeFrame).setVisible(true);
    }

    public void showGraph() {
        new GraficaTiemposDialog(mazeFrame).setVisible(true);
    }

    public void solveMaze() {
        clearCurrentPath();
        Cell[][] mazeData = mazePanel.getMazeData();
        int startRow = -1, startCol = -1, endRow = -1, endCol = -1;

        
        for (int r = 0; r < mazeData.length; r++) {
            for (int c = 0; c < mazeData[0].length; c++) {
                if (mazeData[r][c].getState() == CellState.START) {
                    startRow = r;
                    startCol = c;
                }
                if (mazeData[r][c].getState() == CellState.END) {
                    endRow = r;
                    endCol = c;
                }
            }
        }

        if (startRow == -1 || endRow == -1) {
            mazeFrame.showMessage("Debes establecer inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MazeSolver solver = getSelectedSolver();
        long startTime = System.nanoTime();
        List<Cell> path = solver.solve(mazeData, startRow, startCol, endRow, endCol);
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        if (path != null && !path.isEmpty()) {
            mazeFrame.drawPathOnPanel(path);
            AlgorithmResult result = new AlgorithmResult(solver.getName(), path.size(), durationMs);
            mazeFrame.showMessage(result.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
            AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
            dao.saveResult(result);
        } else {
            AlgorithmResult result = new AlgorithmResult(solver.getName(), 0, durationMs);
            mazeFrame.showMessage("No se encontró camino.\n" + result.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
            AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
            dao.saveResult(result);
        }
    }

    public void startStepByStep() {
        stopStepping(); 
        clearCurrentPath();

        Cell[][] mazeData = mazePanel.getMazeData();
        int startRow = -1, startCol = -1, endRow = -1, endCol = -1;

        for (int r = 0; r < mazeData.length; r++) {
            for (int c = 0; c < mazeData[0].length; c++) {
                if (mazeData[r][c].getState() == CellState.START) {
                    startRow = r;
                    startCol = c;
                }
                if (mazeData[r][c].getState() == CellState.END) {
                    endRow = r;
                    endCol = c;
                }
            }
        }

        if (startRow == -1 || endRow == -1) {
            mazeFrame.showMessage("Debes establecer inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MazeSolver solver = getSelectedSolver();
        stepStartTime = System.nanoTime();
        stepPath = solver.solve(mazeData, startRow, startCol, endRow, endCol);
        currentStep = 0;

        if (stepPath == null || stepPath.isEmpty()) {
            mazeFrame.showMessage("No se encontró camino para el modo paso a paso.", "Camino no encontrado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        isStepping = true;
        stepTimer = new Timer(200, evt -> {
            if (stepPath != null && currentStep < stepPath.size() && isStepping) {
                Cell cell = stepPath.get(currentStep);
                if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                    cell.setState(CellState.PATH);
                }
                mazeFrame.repaintMazePanel();
                currentStep++;
            } else {
                stopStepping();
                if (currentStep == stepPath.size()) {
                    long stepEndTime = System.nanoTime();
                    long durationMs = (stepEndTime - stepStartTime) / 1_000_000;
                    AlgorithmResult result = new AlgorithmResult(
                            getSelectedSolver().getName(),
                            stepPath.size(),
                            durationMs
                    );
                    mazeFrame.showMessage("Camino terminado.\n" + result.toString(), "Paso a paso", JOptionPane.INFORMATION_MESSAGE);
                    AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
                    dao.saveResult(result);
                    stepPath = null;
                    currentStep = 0;
                }
            }
        });
        stepTimer.start();
    }

    public void stepForward() {
        stopStepping(); 
        if (stepPath != null && currentStep < stepPath.size()) {
            Cell cell = stepPath.get(currentStep);
            if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                cell.setState(CellState.PATH);
            }
            mazeFrame.repaintMazePanel();
            currentStep++;
            if (currentStep == stepPath.size()) {
                long stepEndTime = System.nanoTime();
                long durationMs = (stepEndTime - stepStartTime) / 1_000_000;
                AlgorithmResult result = new AlgorithmResult(
                        getSelectedSolver().getName(),
                        stepPath.size(),
                        durationMs
                );
                mazeFrame.showMessage("Camino terminado.\n" + result.toString(), "Paso a paso", JOptionPane.INFORMATION_MESSAGE);
                AlgorithmResultDAO dao = new AlgorithmResultDAOFile();
                dao.saveResult(result);
                stepPath = null;
                currentStep = 0;
            }
        } else if (stepPath != null && currentStep == stepPath.size()){
            mazeFrame.showMessage("El camino ya ha sido completado.", "Paso a paso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void stepBack() {
        stopStepping();
        if (stepPath != null && currentStep > 0) {
            currentStep--;
            Cell cell = stepPath.get(currentStep);
            if (cell.getState() == CellState.PATH) {
                cell.setState(CellState.EMPTY);
            }
            mazeFrame.repaintMazePanel();
        }
    }

    public void stopStepping() {
        if (stepTimer != null && stepTimer.isRunning()) {
            stepTimer.stop();
        }
        isStepping = false;
    }

    public void clearMaze() {
        stopStepping();
        clearCurrentPath();
        mazePanel.clearMaze();
    }

    public void algorithmSelected(String selectedAlgorithm) {
        clearCurrentPath();

    }

    private void clearCurrentPath() {
        stopStepping();
        stepPath = null;
        currentStep = 0;

        Cell[][] mazeData = mazePanel.getMazeData();
        for (int r = 0; r < mazeData.length; r++) {
            for (int c = 0; c < mazeData[0].length; c++) {
                Cell cell = mazeData[r][c];

                if (cell.getState() != CellState.START && cell.getState() != CellState.END && cell.getState() != CellState.WALL) {
                    cell.setState(CellState.EMPTY);
                }
            }
        }
        mazeFrame.repaintMazePanel();
    }

    private MazeSolver getSelectedSolver() {
        String selected = mazeFrame.getSelectedAlgorithm();
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