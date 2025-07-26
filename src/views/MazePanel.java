package views;

import javax.swing.*;

import models.Cell;
import models.CellState;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MazePanel extends JPanel {

    private int numRows;
    private int numCols;
    private Cell[][] mazeData;

    public enum Interaction_Mode {
        NONE, SET_START, SET_END, TOGGLE_WALL
    }

    private Interaction_Mode currentMode = Interaction_Mode.NONE;

    public MazePanel(int rows, int cols) {
        this.numRows = rows;
        this.numCols = cols;
        setPreferredSize(new Dimension(cols * 30, rows * 30));
        setBackground(Color.WHITE);

        this.mazeData = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                mazeData[r][c] = new Cell(r, c, CellState.EMPTY);
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellWidth = getWidth() / numCols;
                int cellHeight = getHeight() / numRows;

                int clickedCol = e.getX() / cellWidth;
                int clickedRow = e.getY() / cellHeight;

                if (clickedRow >= 0 && clickedRow < numRows && clickedCol >= 0 && clickedCol < numCols) {
                    handleCellClick(clickedRow, clickedCol);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellWidth = getWidth() / numCols;
        int cellHeight = getHeight() / numRows;

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                int x = c * cellWidth;
                int y = r * cellHeight;

                Color cellColor;
                CellState state = mazeData[r][c].getState();
                switch (state) {
                    case WALL:
                        cellColor = Color.BLACK;
                        break;
                    case START:
                        cellColor = Color.GREEN;
                        break;
                    case END:
                        cellColor = Color.RED;
                        break;
                    case VISITED:
                        cellColor = Color.LIGHT_GRAY;
                        break;
                    case PATH:
                        cellColor = Color.BLUE;
                        break;
                    default:
                        cellColor = Color.WHITE;
                        break;
                }
                g.setColor(cellColor);
                g.fillRect(x, y, cellWidth, cellHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }

    private void handleCellClick(int row, int col) {
        Cell clickedCell = mazeData[row][col];

        switch (currentMode) {
            case SET_START:

                clearPreviousState(CellState.START);
                clickedCell.setState(CellState.START);
                break;
            case SET_END:
                clearPreviousState(CellState.END);
                clickedCell.setState(CellState.END);
                break;
            case TOGGLE_WALL:
                if (clickedCell.getState() == CellState.WALL) {
                    clickedCell.setState(CellState.EMPTY);
                } else if (clickedCell.getState() == CellState.EMPTY) {
                    clickedCell.setState(CellState.WALL);
                }
                break;
            case NONE:
            default:
                System.out.println("Modo de interacción no seleccionado.");
                break;
        }
        currentMode = Interaction_Mode.NONE;
    }

    private void clearPreviousState(CellState stateToClear) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (mazeData[r][c].getState() == stateToClear) {
                    mazeData[r][c].setState(CellState.EMPTY);
                    return;
                }
            }
        }
    }

    public void setInteractionMode(Interaction_Mode mode) {
        this.currentMode = mode;
    }

    public void clearMaze() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                mazeData[r][c].setState(CellState.EMPTY);
            }
        }
        repaint();
    }

    public void drawPath(List<Cell> path) {
        if (path == null)
            return;
        for (Cell cell : path) {
            if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                cell.setState(CellState.PATH);
            }
        }
        repaint();
    }

    public Cell[][] getMazeData() {
        return mazeData;
    }
}