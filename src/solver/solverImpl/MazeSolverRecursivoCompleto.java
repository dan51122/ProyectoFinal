package solver.solverImpl;

import models.Cell;
import models.CellState;
import solver.MazeSolver;
import java.util.*;

public class MazeSolverRecursivoCompleto implements MazeSolver {
    private List<Cell> path = new ArrayList<>();
    private int rows, cols;

    @Override
    public String getName() {
        return "Recursivo (4 direcciones)";
    }

    @Override
    public List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol) {
        rows = maze.length;
        cols = maze[0].length;
        path.clear();
        boolean[][] visited = new boolean[rows][cols];
        if (dfs(maze, startRow, startCol, endRow, endCol, visited)) return path;
        return new ArrayList<>();
    }

    private boolean dfs(Cell[][] maze, int r, int c, int endRow, int endCol, boolean[][] visited) {
        if (r < 0 || c < 0 || r >= rows || c >= cols) return false;
        if (maze[r][c].getState() == CellState.WALL || visited[r][c]) return false;

        visited[r][c] = true;
        path.add(maze[r][c]);

        if (r == endRow && c == endCol) return true;

        if (dfs(maze, r + 1, c, endRow, endCol, visited)) return true;
        if (dfs(maze, r - 1, c, endRow, endCol, visited)) return true;
        if (dfs(maze, r, c + 1, endRow, endCol, visited)) return true;
        if (dfs(maze, r, c - 1, endRow, endCol, visited)) return true;

        path.remove(path.size() - 1);
        return false;
    }
}
