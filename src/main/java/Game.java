import Aliens.BigHead;
import Aliens.Insect;
import Aliens.Octopus;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        setBackground(Color.BLACK);

        BigHead bigHead = new BigHead();

        g.drawImage(bigHead.getFirstAlienAsset(), 50, 50, null);
        g.drawImage(bigHead.getSecondAlienAsset(), 200, 50, null);

        Insect insect = new Insect();
        g.drawImage(insect.getFirstAlienAsset(), 50, 100, null);
        g.drawImage(insect.getSecondAlienAsset(), 200, 100, null);

        Octopus octopus = new Octopus();
        g.drawImage(octopus.getFirstAlienAsset(), 50, 150, null);
        g.drawImage(octopus.getSecondAlienAsset(), 200, 150, null);
    }
}
