package solver;

import models.Cell;

import java.util.List;

public interface MazeSolver {
    String getName();
    List<Cell> solve(Cell[][] maze, int startRow, int startCol, int endRow, int endCol);
}