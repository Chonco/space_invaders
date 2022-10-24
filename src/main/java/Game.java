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
    private Point playerProjectile;
    private final List<Point> aliensProjectiles;
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
        this.aliensDestroyed = new LinkedList<>();
        this.aliensProjectiles = new ArrayList<>();

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
        if (this.playerProjectile == null) {
            this.playerProjectile = new Point(
                    referencePoints.PLAYER_X + assets.getPlayer().getBounds().width / 2,
                    getHeight() - GameConstants.GAP_SCREEN_Y -
                            assets.getPlayer().getBounds().height - assets.getProjectile().getBounds().height - 5);
        }
    }

    private void updateGameState() {
        var aliensAlive = aliens.stream()
                .filter(AlienDisplayed::isAlive)
                .toList();

        if (iteration++ % referencePoints.N_ITERATION_ALIENS_MOVES == 0) {
            updateAliensCoordinates(aliensAlive);
        }

        if (iteration % GameConstants.ALIEN_SHOOTS_EVERY == 0) {
            alienShoots();
        }

        if (playerProjectile != null) {
            updatePlayerProjectile();
            checkCollidesAliensProjectile(aliensAlive);
        }

        updateAliensDestroyed();
        updateAlienProjectiles();
        checkCollidesPlayerProjectiles();
        updateAlienSpeed();
        updatePlayer();
    }

    private void updateAliensCoordinates(List<AlienDisplayed> aliensAlive) {
        int maxX = 0;
        int minX = getWidth();

        for (AlienDisplayed alienDisplayed : aliensAlive) {
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

    private void alienShoots() {
        Random random = new Random();
        int alienIndexToShoot = random.nextInt(GameConstants.ALIENS_NUM_COLUMNS) +
                (GameConstants.ALIENS_NUM_COLUMNS * (GameConstants.ALIENS_NUM_ROWS - 1));

        AlienDisplayed alienToShoot;

        while (true) {
            alienToShoot = aliens.get(alienIndexToShoot);
            if (alienToShoot.isAlive())
                break;

            alienIndexToShoot -= GameConstants.ALIENS_NUM_COLUMNS;
            if (alienIndexToShoot < 0)
                break;
        }

        if (!alienToShoot.isAlive()) return;

        aliensProjectiles.add(new Point(
                alienToShoot.getPoint().x + alienToShoot.getAlien().getBounds().width / 2,
                alienToShoot.getPoint().y + alienToShoot.getAlien().getBounds().height + GameConstants.GAP_ALIEN_Y
        ));
    }

    private void updateAliensDestroyed() {
        aliensDestroyed.removeIf(alienDestroyed ->
                iteration - alienDestroyed.getIteration() >= referencePoints.N_ITERATION_ALIENS_MOVES);
    }

    private void updatePlayerProjectile() {
        int y = playerProjectile.y - GameConstants.PROJECTILE_MOVEMENT;

        if (y <= 0)
            playerProjectile = null;
        else
            playerProjectile.setLocation(playerProjectile.x, y);
    }

    private void updateAlienProjectiles() {
        List<Point> toDelete = new ArrayList<>();

        for (Point projectile : aliensProjectiles) {
            int y = projectile.y + GameConstants.PROJECTILE_MOVEMENT;

            if (y >= getHeight())
                toDelete.add(projectile);
            else
                projectile.setLocation(projectile.x, y);
        }

        aliensProjectiles.removeAll(toDelete);
    }

    private void checkCollidesAliensProjectile(List<AlienDisplayed> aliensAlive) {
        for (AlienDisplayed alienDisplayed : aliensAlive) {
            if (contextualRectangleContainsPoint(
                    alienDisplayed.getPoint(),
                    alienDisplayed.getAlien().getBounds(),
                    playerProjectile)
            ) {
                alienDisplayed.setAlive(false);
                this.aliensDestroyed.add(new AlienDestroyed(alienDisplayed.getPoint(), iteration));
                playerProjectile = null;
                break;
            }
        }
    }

    private void checkCollidesPlayerProjectiles() {
        for (Point projectile : aliensProjectiles) {
            if (contextualRectangleContainsPoint(
                    new Point(referencePoints.PLAYER_X, getHeight() - GameConstants.GAP_SCREEN_Y),
                    assets.getPlayer().getBounds(),
                    projectile
            )) {
                stopGame();
                JOptionPane.showMessageDialog(this, "Game Finished Player Loses :(");
                System.exit(0);
            }
        }
    }

    private void updateAlienSpeed() {
        int aliensAliveCount = (int) this.aliens.stream().filter(AlienDisplayed::isAlive).count();

        switch (GameConstants.ALIENS_TOTAL_NUM - aliensAliveCount) {
            case 4 -> referencePoints.N_ITERATION_ALIENS_MOVES = 8;
            case 8 -> referencePoints.N_ITERATION_ALIENS_MOVES = 7;
            case 12 -> referencePoints.N_ITERATION_ALIENS_MOVES = 6;
            case 16 -> referencePoints.N_ITERATION_ALIENS_MOVES = 5;
            case 18 -> referencePoints.N_ITERATION_ALIENS_MOVES = 4;
            case 20 -> referencePoints.N_ITERATION_ALIENS_MOVES = 3;
            case 22 -> referencePoints.N_ITERATION_ALIENS_MOVES = 2;
            case 24 -> referencePoints.N_ITERATION_ALIENS_MOVES = 1;
        }
    }

    private void updatePlayer() {
        int playerMovement = GameConstants.PLAYER_MOVEMENT * playerDirection;
        if (referencePoints.PLAYER_X + playerMovement > GameConstants.GAP_SCREEN_X &&
                referencePoints.PLAYER_X + assets.getPlayer().getBounds().width + playerMovement < getWidth() - GameConstants.GAP_SCREEN_X
        )
            referencePoints.PLAYER_X += playerMovement;
    }

    private boolean contextualRectangleContainsPoint(Point initPoint, Rectangle rectangle, Point target) {
        if (target == null) return false;
        Rectangle zone = new Rectangle(initPoint.x, initPoint.y, rectangle.width, rectangle.height);
        return zone.contains(target);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (this.aliens.stream().noneMatch(AlienDisplayed::isAlive)) {
            stopGame();
            JOptionPane.showMessageDialog(this, "Game Finished! Player Wins!");
            System.exit(0);
        }

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

        if (playerProjectile != null)
            g.drawImage(assets.getProjectile().getAsset(), playerProjectile.x, playerProjectile.y, null);

        for (Point projectile : aliensProjectiles) {
            g.drawImage(assets.getProjectile().getAsset(), projectile.x, projectile.y, null);
        }

        g.drawImage(assets.getPlayer().getAsset(), referencePoints.PLAYER_X, getHeight() - GameConstants.GAP_SCREEN_Y, null);
    }
}
