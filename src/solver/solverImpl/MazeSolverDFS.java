package solver.solverImpl;

import models.Cell;
import models.CellState;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverDFS implements MazeSolver {
    @Override
    public String getName() {
        return "DFS (Depth-First Search)";
    }

    @Override
    public List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Cell[][] prev = new Cell[rows][cols];
        Stack<Cell> stack = new Stack<>();
        stack.push(maze[startRow][startCol]);
        visited[startRow][startCol] = true;

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int r = current.getRow();
            int c = current.getCol();

            if (r == endRow && c == endCol) {
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

        // Reconstruir el camino
        List<Cell> path = new ArrayList<>();
        Cell curr = maze[endRow][endCol];
        while (curr != null && !(curr.getRow() == startRow && curr.getCol() == startCol)) {
            path.add(curr);
            curr = prev[curr.getRow()][curr.getCol()];
        }
        if (curr != null) path.add(curr); // Añadir el inicio
        Collections.reverse(path);
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