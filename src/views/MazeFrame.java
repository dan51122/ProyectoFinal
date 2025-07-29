package views;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.Timer;

import controllers.MazeController;
import models.Cell;
import models.CellState;

public class MazeFrame extends JFrame {

    private MazePanel mazePanel;
    private JComboBox<String> algorithmComboBox;
    private JButton setStartButton;
    private JButton setEndButton;
    private JButton toggleWallButton;
    private JButton eraseWallButton;
    private JButton resultadosButton;
    private JButton graficaButton;
    private JButton solveButton;
    private JButton stepByStepButton;
    private JButton clearButton;
    private JButton stepBackButton;
    private JButton stepForwardButton;
    private JButton stepStopButton;

    private MazeController controller; 

    public MazeFrame(int numRows, int numCols) {
        super("Maze Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mazePanel = new MazePanel(numRows, numCols);
        this.controller = new MazeController(this, mazePanel); 
        initComponents(numRows, numCols);
    }

    private void initComponents(int numRows, int numCols) {
       
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setStartButton = new JButton("Set Start");
        setEndButton = new JButton("Set End");
        toggleWallButton = new JButton("Toggle Wall");
        eraseWallButton = new JButton("Borrar muro");
        resultadosButton = new JButton("Ver resultados");
        graficaButton = new JButton("Ver gráfica");

        topPanel.add(setStartButton);
        topPanel.add(setEndButton);
        topPanel.add(toggleWallButton);
        topPanel.add(eraseWallButton);
        topPanel.add(resultadosButton);
        topPanel.add(graficaButton);
        add(topPanel, BorderLayout.NORTH);

     
        add(new JScrollPane(mazePanel), BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(new JLabel("Algoritmo:"));
        String[] algorithms = { "Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS" };
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setSelectedItem("Recursivo");
        bottomPanel.add(algorithmComboBox);

        solveButton = new JButton("Resolver");
        stepByStepButton = new JButton("Paso a paso");
        clearButton = new JButton("Limpiar");
        stepBackButton = new JButton("<");
        stepForwardButton = new JButton(">");
        stepStopButton = new JButton("⏹");

        bottomPanel.add(solveButton);
        bottomPanel.add(stepByStepButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(stepBackButton);
        bottomPanel.add(stepForwardButton);
        bottomPanel.add(stepStopButton);
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
            controller.setInteractionMode(MazePanel.Interaction_Mode.SET_START);
            setActiveButton(setStartButton, setEndButton, toggleWallButton, eraseWallButton);
        });
        setEndButton.addActionListener(e -> {
            controller.setInteractionMode(MazePanel.Interaction_Mode.SET_END);
            setActiveButton(setEndButton, setStartButton, toggleWallButton, eraseWallButton);
        });
        toggleWallButton.addActionListener(e -> {
            controller.setInteractionMode(MazePanel.Interaction_Mode.TOGGLE_WALL);
            setActiveButton(toggleWallButton, setStartButton, setEndButton, eraseWallButton);
        });
        eraseWallButton.addActionListener(e -> {
            controller.setInteractionMode(MazePanel.Interaction_Mode.BORRAR_WALL);
            setActiveButton(eraseWallButton, setStartButton, setEndButton, toggleWallButton);
        });

        resultadosButton.addActionListener(e -> controller.showResults());
        graficaButton.addActionListener(e -> controller.showGraph());
        solveButton.addActionListener(e -> controller.solveMaze());
        stepByStepButton.addActionListener(e -> controller.startStepByStep());
        clearButton.addActionListener(e -> controller.clearMaze());
        stepForwardButton.addActionListener(e -> controller.stepForward());
        stepBackButton.addActionListener(e -> controller.stepBack());
        stepStopButton.addActionListener(e -> controller.stopStepping());
        algorithmComboBox.addActionListener(e -> controller.algorithmSelected((String) algorithmComboBox.getSelectedItem()));
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmComboBox.getSelectedItem();
    }

    public MazePanel getMazePanel() {
        return mazePanel;
    }

    public void setActiveButton(JButton active, JButton... others) {
        active.setBackground(new Color(135, 206, 250));
        for (JButton btn : others) {
            btn.setBackground(null);
        }
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }


    public void drawPathOnPanel(List<Cell> path) {
        mazePanel.drawPath(path);
    }


    public void repaintMazePanel() {
        mazePanel.repaint();
    }
}