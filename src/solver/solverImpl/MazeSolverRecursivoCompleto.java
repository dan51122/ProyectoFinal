package solver.solverImpl;

import java.util.*;
import javax.swing.JOptionPane;
import models.Cell;
import models.CellState;
import solver.MazeSolver;

public class MazeSolverRecursivoCompleto implements MazeSolver {

    @Override
    public String getName() {
        return "Recursivo (4 direcciones)";
    }

    @Override
    public List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol) {
        int rows = maze.length;
        int cols = maze[0].length;

        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols ||
                endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
            JOptionPane.showMessageDialog(null, "Las coordenadas de inicio o fin están fuera de los límites del laberinto.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }

        if (maze[startRow][startCol].getState() == CellState.WALL ||
                maze[endRow][endCol].getState() == CellState.WALL) {
            JOptionPane.showMessageDialog(null, "La celda de inicio o fin es una pared.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }

        if (startRow == endRow && startCol == endCol) {
            List<Cell> path = new ArrayList<>();
            path.add(maze[startRow][startCol]);
            return path;
        }

        List<Cell> path = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];

        if (dfs(maze, startRow, startCol, endRow, endCol, path, visited)) {
            return path;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un camino en el laberinto.", "Camino No Encontrado", JOptionPane.INFORMATION_MESSAGE);
            return Collections.emptyList();
        }
    }

    private boolean dfs(Cell[][] maze, int r, int c, int endRow, int endCol, List<Cell> path, boolean[][] visited) {
        int rows = maze.length;
        int cols = maze[0].length;

        if (r < 0 || c < 0 || r >= rows || c >= cols) {
            return false;
        }
        if (visited[r][c] || maze[r][c].getState() == CellState.WALL) {
            return false;
        }

        visited[r][c] = true;
        path.add(maze[r][c]);

        if (r == endRow && c == endCol) {
            return true;
        }

        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (dfs(maze, nr, nc, endRow, endCol, path, visited)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}