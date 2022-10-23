package assets;

import assets.aliens.BigHead;
import assets.aliens.Destroyed;
import assets.aliens.Insect;
import assets.aliens.Octopus;

public class Assets {
    private final BigHead bigHead;
    private final Insect insect;
    private final Octopus octopus;
    private final Destroyed destroyed;
    private final Player player;
    private final Projectile projectile;
    private final BackgroundScreen backgroundScreen;


    public Assets() {
        this.bigHead = new BigHead();
        this.insect = new Insect();
        this.octopus = new Octopus();
        this.destroyed = new Destroyed();
        this.player = new Player();
        this.projectile = new Projectile();
        this.backgroundScreen = new BackgroundScreen();
    }

    public BigHead getBigHead() {
        return bigHead;
    }

    public Insect getInsect() {
        return insect;
    }

    public Octopus getOctopus() {
        return octopus;
    }

    public Destroyed getDestroyed() {
        return destroyed;
    }

    public Player getPlayer() {
        return player;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public BackgroundScreen getBackgroundScreen() {
        return backgroundScreen;
    }
}
