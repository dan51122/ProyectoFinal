import views.MazeFrame;
import javax.swing.*;

public class MazeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int filas = 15, columnas = 20;

            try {
                String filasStr = JOptionPane.showInputDialog("¿Cuántas filas para el laberinto?");
                String columnasStr = JOptionPane.showInputDialog("¿Cuántas columnas para el laberinto?");
                
                filas = Integer.parseInt(filasStr);
                columnas = Integer.parseInt(columnasStr);

                if (filas < 4 || columnas < 4) {
                    throw new IllegalArgumentException("No se puede ingresar un laberinto menor a 4x4");
                }

            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + "\nUsando tamaño por defecto 15x20.");
                filas = 15;
                columnas = 20;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Valores inválidos, usando 15x20 por defecto.");
                filas = 15;
                columnas = 20;
            }

            MazeFrame frame = new MazeFrame(filas, columnas);
            frame.setVisible(true);
        });
    }
}
