import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        setBackground(Color.BLACK);
        g.drawImage(Alien.getFirstAlienAsset(), 50, 50, null);
        g.drawImage(Alien.getSecondAlienAsset(), 200, 50, null);
    }
}
