package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import models.Cell;
import models.CellState;

public class MazePanel extends JPanel {

    private int numRows;
    private int numCols;
    private Cell[][] mazeData;

    public enum Interaction_Mode {
        NONE, SET_START, SET_END, TOGGLE_WALL, BORRAR_WALL
    }

    private Interaction_Mode currentMode = Interaction_Mode.NONE;
    private boolean mouseDown = false;

    private Image wallTexture = new ImageIcon("assets/brick.jpg").getImage();
    private Image mouseTexture = new ImageIcon("assets/mouse.png").getImage();
    private Image cheeseTexture = new ImageIcon("assets/cheese.png").getImage();
    private Image backgroundImage = new ImageIcon("assets/background.png").getImage();

    public MazePanel(int rows, int cols) {
        this.numRows = rows;
        this.numCols = cols;
        mazeData = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                mazeData[r][c] = new Cell(r, c, CellState.EMPTY);
            }
        }
        setPreferredSize(new Dimension(cols * 30, rows * 30));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDown = true;
                int cellWidth = getWidth() / numCols;
                int cellHeight = getHeight() / numRows;
                int col = e.getX() / cellWidth;
                int row = e.getY() / cellHeight;
                handleCellClick(row, col);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDown = false;
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseDown && currentMode == Interaction_Mode.TOGGLE_WALL) {
                    int cellWidth = getWidth() / numCols;
                    int cellHeight = getHeight() / numRows;
                    int col = e.getX() / cellWidth;
                    int row = e.getY() / cellHeight;
                    handleCellClick(row, col);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen de fondo escalada al tamaño del panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        int cellWidth = getWidth() / numCols;
        int cellHeight = getHeight() / numRows;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell cell = mazeData[r][c];
                switch (cell.getState()) {
                    case WALL:
                        g.drawImage(wallTexture, c * cellWidth, r * cellHeight, cellWidth, cellHeight, this);
                        break;
                    case START:
                        g.drawImage(mouseTexture, c * cellWidth, r * cellHeight, cellWidth, cellHeight, this);
                        break;
                    case END:
                        g.drawImage(cheeseTexture, c * cellWidth, r * cellHeight, cellWidth, cellHeight, this);
                        break;
                    case PATH:
                        g.setColor(Color.YELLOW);
                        g.fillRect(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                        break;
                    default:
                        // Para las celdas vacías, solo se verá el fondo
                        break;
                }
                g.setColor(Color.GRAY);
                g.drawRect(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    private void handleCellClick(int row, int col) {
        if (row < 0 || col < 0 || row >= numRows || col >= numCols) return;
        Cell cell = mazeData[row][col];
        switch (currentMode) {
            case SET_START:
                clearPreviousState(CellState.START);
                cell.setState(CellState.START);
                break;
            case SET_END:
                clearPreviousState(CellState.END);
                cell.setState(CellState.END);
                break;
            case TOGGLE_WALL:
                if (cell.getState() == CellState.EMPTY) {
                    cell.setState(CellState.WALL);
                }
                break;
            case BORRAR_WALL:
                if (cell.getState() == CellState.WALL) {
                    cell.setState(CellState.EMPTY);
                }
                break;
            default:
                break;
        }
        repaint();
    }

    private void clearPreviousState(CellState stateToClear) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (mazeData[r][c].getState() == stateToClear) {
                    mazeData[r][c].setState(CellState.EMPTY);
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

    public Cell[][] getMazeData() {
        return mazeData;
    }

    public void drawPath(java.util.List<Cell> path) {
        for (Cell cell : path) {
            if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                cell.setState(CellState.PATH);
            }
        }
        repaint();
    }
    
}