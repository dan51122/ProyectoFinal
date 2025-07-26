
package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

public class MazeFrame extends JFrame {

    private MazePanel mazePanel;
    private JComboBox<String> algorithmComboBox;

    public MazeFrame(int numRows, int numCols) {
        super("Maze Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initComponents(numRows, numCols);
    }

    private void initComponents(int numRows, int numCols) {
        // Panel superior con botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton setStartButton = new JButton("Set Start");
        JButton setEndButton = new JButton("Set End");
        JButton toggleWallButton = new JButton("Toggle Wall");
        topPanel.add(setStartButton);
        topPanel.add(setEndButton);
        topPanel.add(toggleWallButton);
        add(topPanel, BorderLayout.NORTH);

        mazePanel = new MazePanel(numRows, numCols);
        add(new JScrollPane(mazePanel), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(new JLabel("Algoritmo:"));
        String[] algorithms = { "Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS",
                "Backtracking" };
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setSelectedItem("Recursivo");
        bottomPanel.add(algorithmComboBox);

        JButton solveButton = new JButton("Resolver");
        JButton stepByStepButton = new JButton("Paso a paso");
        JButton clearButton = new JButton("Limpiar");
        bottomPanel.add(solveButton);
        bottomPanel.add(stepByStepButton);
        bottomPanel.add(clearButton);
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
            mazePanel.setInteractionMode(MazePanel.Interaction_Mode.SET_START);
            System.out.println("Modo: Establecer Inicio");
        });

        setEndButton.addActionListener(e -> {
            mazePanel.setInteractionMode(MazePanel.Interaction_Mode.SET_END);
            System.out.println("Modo: Establecer Fin");
        });

        toggleWallButton.addActionListener(e -> {
            mazePanel.setInteractionMode(MazePanel.Interaction_Mode.TOGGLE_WALL);
            System.out.println("Modo: Alternar Pared");
        });

        solveButton.addActionListener(e -> {
            System.out.println("Resolver clicked");
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

            JOptionPane.showMessageDialog(this, "Resolviendo con " + selectedAlgorithm, "Resolver",
                    JOptionPane.INFORMATION_MESSAGE);

        });

        stepByStepButton.addActionListener(e -> {
            System.out.println("Paso a paso clicked");

        });

        clearButton.addActionListener(e -> {
            System.out.println("Limpiar clicked");
            mazePanel.clearMaze();

        });

        algorithmComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            System.out.println("Algoritmo seleccionado: " + selectedAlgorithm);
        });
    }

}