package solver.solverImpl;

import models.Cell;
import models.CellState;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverRecursivo implements MazeSolver {
    @Override
    public String getName() {
        return "Recursivo";
    }

    @Override
    public List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol) {
        List<Cell> path = new ArrayList<>();
        boolean found = findPath(maze, startRow, startCol, endRow, endCol, path, new boolean[maze.length][maze[0].length]);
        if (found) {
            return path;
        } else {
            return Collections.emptyList();
        }
    }

    private boolean findPath(Cell[][] maze, int r, int c, int endRow, int endCol, List<Cell> path, boolean[][] visited) {
        int rows = maze.length;
        int cols = maze[0].length;
        if (r < 0 || c < 0 || r >= rows || c >= cols) return false;
        if (visited[r][c] || maze[r][c].getState() == CellState.WALL) return false;

        path.add(maze[r][c]);
        visited[r][c] = true;

        if (r == endRow && c == endCol) return true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (findPath(maze, nr, nc, endRow, endCol, path, visited)) {
                return true;
            }
        }

        path.remove(path.size() - 1); // backtrack
        return false;
    }
}