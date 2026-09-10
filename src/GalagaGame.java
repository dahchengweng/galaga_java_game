import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GalagaGame extends JFrame {

    public GalagaGame() {
        setTitle("Java Galaga 大蜜蜂 (OOP 多型版)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GalagaPanel panel = new GalagaPanel();
        add(panel);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GalagaGame());
    }
}
