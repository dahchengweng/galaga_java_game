import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame =
                    new JFrame("Java Galaga");

            GamePanel game =
                    new GamePanel();

            frame.add(game);

            frame.pack();

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.setLocationRelativeTo(null);

            frame.setResizable(false);

            frame.setVisible(true);
        });
    }
}