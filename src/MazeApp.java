import views.MazeFrame;
import javax.swing.*;

public class MazeApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            String filasStr = JOptionPane.showInputDialog("¿Cuántas filas para el laberinto?");
            String columnasStr = JOptionPane.showInputDialog("¿Cuántas columnas para el laberinto?");
            int filas = 15, columnas = 20;
            try {
                filas = Integer.parseInt(filasStr);
                columnas = Integer.parseInt(columnasStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valores inválidos, usando 15x20 por defecto.");
            }
            MazeFrame frame = new MazeFrame(filas, columnas);
            frame.setVisible(true);
        });
    }
}