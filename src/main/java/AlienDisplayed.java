import assets.aliens.AbstractAlien;

import java.awt.*;

public class AlienDisplayed {
    private Point point;
    private boolean isAlive;
    private final AbstractAlien alien;

    public AlienDisplayed(Point point, AbstractAlien alien) {
        this.point = point;
        this.isAlive = true;
        this.alien = alien;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public AbstractAlien getAlien() {
        return alien;
    }
}
