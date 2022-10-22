import javax.swing.*;
import java.awt.*;

public class SpaceInvaders {
    SpaceInvaders() {
        JFrame frame = new JFrame("Space Invaders");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.add(new Game(), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpaceInvaders::new);
    }
}
