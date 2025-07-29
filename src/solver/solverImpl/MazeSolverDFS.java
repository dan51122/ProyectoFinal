package solver.solverImpl;

import java.util.*;
import models.Cell;
import models.CellState;
import solver.MazeSolver;

public class MazeSolverDFS implements MazeSolver {
    @Override
    public String getName() {
        return "DFS (Depth-First Search)";
    }

    @Override
    public List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol) {
        int rows = maze.length;
        int cols = maze[0].length;

        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols ||
                endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
            System.err.println("Error: Coordenadas de inicio o fin fuera de los límites del laberinto.");
            return Collections.emptyList();
        }

        if (maze[startRow][startCol].getState() == CellState.WALL ||
                maze[endRow][endCol].getState() == CellState.WALL) {
            System.err.println("Error: La celda de inicio o fin es una pared.");
            return Collections.emptyList();
        }

        if (startRow == endRow && startCol == endCol) {
            List<Cell> path = new ArrayList<>();
            path.add(maze[startRow][startCol]);
            return path;
        }

        boolean[][] visited = new boolean[rows][cols];
        Cell[][] prev = new Cell[rows][cols];
        Stack<Cell> stack = new Stack<>();

        Cell startCell = maze[startRow][startCol];
        stack.push(startCell);
        visited[startRow][startCol] = true;

        boolean foundPath = false;

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int r = current.getRow();
            int c = current.getCol();

            if (r == endRow && c == endCol) {
                foundPath = true;
                break;
            }

            for (Cell neighbor : getNeighbors(maze, current)) {
                int nr = neighbor.getRow();
                int nc = neighbor.getCol();
                if (!visited[nr][nc] && neighbor.getState() != CellState.WALL) {
                    stack.push(neighbor);
                    visited[nr][nc] = true;
                    prev[nr][nc] = current;
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        if (foundPath) {
            Cell curr = maze[endRow][endCol];
            while (curr != null && !(curr.getRow() == startRow && curr.getCol() == startCol)) {
                path.add(curr);
                curr = prev[curr.getRow()][curr.getCol()];
            }
            if (curr != null) {
                path.add(curr);
            }
            Collections.reverse(path);
        }
        return path;
    }

    private List<Cell> getNeighbors(Cell[][] maze, Cell cell) {
        int rows = maze.length;
        int cols = maze[0].length;
        int r = cell.getRow();
        int c = cell.getCol();
        List<Cell> neighbors = new ArrayList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                neighbors.add(maze[nr][nc]);
            }
        }
        return neighbors;
    }
}