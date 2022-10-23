import assets.Assets;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Game extends JPanel {

    private static class ReferencePoints {
        int[] ALIENS_BOTTOM = new int[GameConstants.ALIENS_NUM_COLUMNS];

        int N_ITERATION_ALIENS_MOVES = 10;

        int PLAYER_X = GameConstants.GAP_SCREEN_X;
    }

    private final Assets assets;
    private final List<AlienDisplayed> aliens;
    private final ReferencePoints referencePoints;
    private final List<Point> playerProjectiles;
    private final List<AlienDestroyed> aliensDestroyed;

    private final Timer timer;

    private int alienDirectionX = 1;
    private int alienIncrementY = 0;
    private boolean alienUsesFirstAsset = true;
    private int playerDirection = 0;

    private int iteration = 0;

    public Game() {
        this.assets = new Assets();
        this.aliens = new ArrayList<>();
        this.referencePoints = new ReferencePoints();
        this.playerProjectiles = new ArrayList<>();
        this.aliensDestroyed = new LinkedList<>();

        initAliensList();
        initializeAliensBottoms();

        timer = new Timer(GameConstants.UPDATE_INTERVAL, e -> {
            updateGameState();
            repaint();
        });
    }

    private void initAliensList() {
        for (int i = 0; i < GameConstants.ALIENS_NUM_ROWS; i++) {
            for (int j = 0; j < GameConstants.ALIENS_NUM_COLUMNS; j++) {
                int x = GameConstants.GAP_SCREEN_X + (GameConstants.ALIEN_SIZE_X + GameConstants.GAP_ALIEN_X) * j;
                int y = GameConstants.GAP_SCREEN_Y + (GameConstants.ALIEN_SIZE_Y + GameConstants.GAP_ALIEN_Y) * i;

                if (i % 3 == 0)
                    this.aliens.add(new AlienDisplayed(new Point(x, y), assets.getInsect()));
                else if (i % 2 == 0)
                    this.aliens.add(new AlienDisplayed(new Point(x, y), assets.getOctopus()));
                else
                    this.aliens.add(new AlienDisplayed(new Point(x, y), assets.getBigHead()));
            }
        }
    }

    private void initializeAliensBottoms() {
        Arrays.fill(referencePoints.ALIENS_BOTTOM,
                GameConstants.GAP_SCREEN_Y +
                        ((GameConstants.ALIEN_SIZE_Y + GameConstants.GAP_ALIEN_Y) * GameConstants.ALIENS_NUM_ROWS));
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

    public void changePlayerMovement(int input) {
        playerDirection = input;
    }

    public void playerFired() {
        System.out.println("Player fired");
        this.playerProjectiles.add(new Point(
                referencePoints.PLAYER_X + assets.getPlayer().getBounds().width / 2,
                getHeight() - GameConstants.GAP_SCREEN_Y -
                        assets.getPlayer().getBounds().height - assets.getProjectile().getBounds().height - 5));
    }

    private void updateGameState() {
        if (iteration++ % referencePoints.N_ITERATION_ALIENS_MOVES == 0) {
            int maxX = 0;
            int minX = getWidth();

            for (AlienDisplayed alienDisplayed : aliens) {
                Point point = alienDisplayed.getPoint();
                int x = point.x + GameConstants.ALIEN_STEP * alienDirectionX;
                int y = point.y + GameConstants.GAP_ALIEN_Y * alienIncrementY;
                alienDisplayed.setPoint(new Point(x, y));

                if (x > maxX)
                    maxX = x;
                if (x < minX)
                    minX = x;
            }

            if (maxX + GameConstants.ALIEN_SIZE_X >= getWidth() - GameConstants.GAP_SCREEN_X) {
                alienDirectionX = -1;
                alienIncrementY = 1;
            } else if (minX <= GameConstants.GAP_SCREEN_X) {
                alienDirectionX = 1;
                alienIncrementY = 1;
            } else {
                alienIncrementY = 0;
            }


            alienUsesFirstAsset = !alienUsesFirstAsset;
        }

        aliensDestroyed.removeIf(alienDestroyed -> iteration - alienDestroyed.getIteration() >= referencePoints.N_ITERATION_ALIENS_MOVES);

        List<Point> projectilesToRemove = new ArrayList<>();
        for (Point projectile : playerProjectiles) {
            int y = projectile.y - GameConstants.PROJECTILE_MOVEMENT;

            if (y <= 0)
                projectilesToRemove.add(projectile);
            else
                projectile.setLocation(projectile.x, y);
        }

        playerProjectiles.removeAll(projectilesToRemove);

        for (AlienDisplayed alienDisplayed : aliens) {
            if (alienDisplayed.isAlive()) {
                Point projectileToBeRemoved = null;
                for (Point projectile : playerProjectiles) {
                    if (contextualRectangleContainsPoint(
                            alienDisplayed.getPoint(),
                            alienDisplayed.getAlien().getBounds(),
                            projectile)
                    ) {
                        alienDisplayed.setAlive(false);
                        this.aliensDestroyed.add(new AlienDestroyed(alienDisplayed.getPoint(), iteration));
                        projectileToBeRemoved = projectile;
                        break;
                    }
                }

                if (projectileToBeRemoved != null)
                    playerProjectiles.remove(projectileToBeRemoved);
            }
        }

        int playerMovement = GameConstants.PLAYER_MOVEMENT * playerDirection;
        if (referencePoints.PLAYER_X + playerMovement > GameConstants.GAP_SCREEN_X &&
                referencePoints.PLAYER_X + assets.getPlayer().getBounds().width + playerMovement < getWidth() - GameConstants.GAP_SCREEN_X
        )
            referencePoints.PLAYER_X += playerMovement;
    }

    private boolean contextualRectangleContainsPoint(Point initPoint, Rectangle rectangle, Point target) {
        Rectangle zone = new Rectangle(initPoint.x, initPoint.y, rectangle.width, rectangle.height);
        return zone.contains(target);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(assets.getBackgroundScreen().getAsset(this.getWidth(), this.getHeight()), 0, 0, null);

        for (AlienDisplayed alienDisplayed : aliens) {
            if (!alienDisplayed.isAlive()) {
                continue;
            }

            BufferedImage alienAsset;
            if (this.alienUsesFirstAsset) {
                alienAsset = alienDisplayed.getAlien().getFirstAlienAsset();
            } else {
                alienAsset = alienDisplayed.getAlien().getSecondAlienAsset();
            }

            g.drawImage(
                    alienAsset,
                    alienDisplayed.getPoint().x,
                    alienDisplayed.getPoint().y,
                    null);
        }

        for (AlienDestroyed alienDestroyed : aliensDestroyed) {
            g.drawImage(assets.getDestroyed().getAsset(), alienDestroyed.getPoint().x, alienDestroyed.getPoint().y, null);
        }

        for (Point point : playerProjectiles) {
            g.drawImage(assets.getProjectile().getAsset(), point.x, point.y, null);
        }

        g.drawImage(assets.getPlayer().getAsset(), referencePoints.PLAYER_X, getHeight() - GameConstants.GAP_SCREEN_Y, null);
    }
}
