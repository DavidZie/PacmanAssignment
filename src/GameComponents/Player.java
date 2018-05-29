package GameComponents;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

    BufferedImage image;//Should be Array of Different Animations.

    Player() {
        image = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 25, 25);
        g.setColor(Color.YELLOW);
        g.fillOval(0, 0, 24, 24);
    }

    public BufferedImage getImage() {
        return image;
    }
}
