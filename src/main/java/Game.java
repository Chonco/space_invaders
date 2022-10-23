import assets.Assets;
import assets.aliens.AbstractAlien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel {

    private static class ReferencePoints {
        int ALIENS_LEFT = GameConstants.GAP_SCREEN_X;
        int ALIENS_RIGHT = GameConstants.GAP_SCREEN_X +
                ((GameConstants.ALIEN_SIZE_X + GameConstants.GAP_ALIEN_X) * GameConstants.ALIENS_NUM_COLUMNS) -
                GameConstants.GAP_ALIEN_X;
        int ALIENS_TOP = GameConstants.GAP_SCREEN_Y;

        int N_ITERATION_ALIENS_MOVES = 10;

        int PLAYER_X = GameConstants.GAP_SCREEN_X;
    }

    private final Assets assets;
    private final AbstractAlien[][] aliens;
    private final ReferencePoints referencePoints;

    private final Timer timer;

    private int alienDirection = 1;
    private int playerDirection = 0;

    private int iteration = 0;

    public Game() {
        this.assets = new Assets();
        this.aliens = new AbstractAlien[GameConstants.ALIENS_NUM_ROWS][GameConstants.ALIENS_NUM_COLUMNS];
        this.referencePoints = new ReferencePoints();

        initAliensList();

        timer = new Timer(GameConstants.UPDATE_INTERVAL, e -> {
            updateGameState();
            repaint();
        });
    }

    private void initAliensList() {
        for (int i = 0; i < aliens.length; i++) {
            for (int j = 0; j < aliens[i].length; j++) {
                if (i % 3 == 0)
                    aliens[i][j] = assets.getInsect();
                else if (i % 2 == 0)
                    aliens[i][j] = assets.getOctopus();
                else
                    aliens[i][j] = assets.getBigHead();
            }
        }
    }

    public boolean isGameRunning() {
        return timer.isRunning();
    }

    public void startGame() {
        referencePoints.PLAYER_X = getWidth() / 2 - 100;
        timer.start();
    }

    public void stopGame() {
        timer.stop();
    }

    public void resumeGame() {
        timer.restart();
    }

    public void processPlayerInput(int input) {
        playerDirection = input;
    }

    private void updateGameState() {
        if (iteration++ % referencePoints.N_ITERATION_ALIENS_MOVES == 0) {
            referencePoints.ALIENS_LEFT += GameConstants.ALIEN_STEP * alienDirection;
            referencePoints.ALIENS_RIGHT += GameConstants.ALIEN_STEP * alienDirection;

            if (referencePoints.ALIENS_LEFT <= GameConstants.GAP_SCREEN_X) {
                alienDirection = 1;
                referencePoints.ALIENS_TOP += GameConstants.GAP_ALIEN_Y;
            } else if (referencePoints.ALIENS_RIGHT >= getWidth() - GameConstants.GAP_SCREEN_X) {
                alienDirection = -1;
                referencePoints.ALIENS_TOP += GameConstants.GAP_ALIEN_Y;
            }
        }

        int playerMovement = GameConstants.PLAYER_MOVEMENT * playerDirection;

        if (referencePoints.PLAYER_X + playerMovement > GameConstants.GAP_SCREEN_X &&
                referencePoints.PLAYER_X + assets.getPlayer().getBounds().width + playerMovement < getWidth() - GameConstants.GAP_SCREEN_X
        )
            referencePoints.PLAYER_X += playerMovement;
    }

    private void updateAliensLeftReferencePoint() {
        for (int i = 0; i < aliens[0].length; i++) {
            for (int j = 0; j < aliens.length; j++) {
                if (aliens[j][i] != null) {
                    return;
                }
            }

            referencePoints.ALIENS_LEFT += GameConstants.ALIEN_SIZE_X + GameConstants.GAP_ALIEN_X;
        }

        stopGame();
    }

    private void updateAliensRightReferencePoint() {
        for (int i = aliens[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < aliens.length; j++) {
                if (aliens[j][i] != null) {
                    return;
                }
            }

            referencePoints.ALIENS_RIGHT -= GameConstants.ALIEN_SIZE_X + GameConstants.GAP_ALIEN_X;
        }

        stopGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(assets.getBackgroundScreen().getAsset(this.getWidth(), this.getHeight()), 0, 0, null);

        int referenceX;
        int referenceY = referencePoints.ALIENS_TOP;

        for (AbstractAlien[] alienRow : aliens) {
            referenceX = referencePoints.ALIENS_LEFT;
            for (AbstractAlien alien : alienRow) {
                g.drawImage(alien.getFirstAlienAsset(), referenceX, referenceY, null);
                referenceX += GameConstants.ALIEN_SIZE_X + GameConstants.GAP_ALIEN_X;
            }
            referenceY += GameConstants.ALIEN_SIZE_Y + GameConstants.GAP_ALIEN_Y;
        }

        g.drawImage(assets.getPlayer().getAsset(), referencePoints.PLAYER_X, getHeight() - GameConstants.GAP_SCREEN_Y, null);
    }
}
