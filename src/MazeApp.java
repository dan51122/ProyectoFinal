import views.MazeFrame;

public class MazeApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MazeFrame frame = new MazeFrame(15, 20); // Puedes cambiar el tamaño del laberinto
            frame.setVisible(true);
        });
    }
}