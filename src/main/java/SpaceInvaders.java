import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceInvaders {
    SpaceInvaders() {
        Validations.validateGameConfigurations();
        Game game = new Game();

        JFrame frame = new JFrame("Space Invaders");
        frame.setSize(1000, 750);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_ESCAPE -> {
                        if (game.isGameRunning()) {
                            game.stopGame();
                        } else {
                            game.resumeGame();
                        }
                    }
                    case KeyEvent.VK_SPACE -> game.playerFired();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT -> game.changePlayerMovement(1);
                    case KeyEvent.VK_LEFT -> game.changePlayerMovement(-1);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> game.changePlayerMovement(0);
                }
            }
        });

        game.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpaceInvaders::new);
    }
}
