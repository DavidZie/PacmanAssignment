package GameComponents.Players;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.pieceSize;


public abstract class Weapon implements Visitor {

    protected BufferedImage image;

    protected Timer timer;
    protected int repeats;
    protected boolean loaded;
    protected int[] location;
    protected int direction;


    Weapon(){
        image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        location = new int[2];
    }

    public void setLocation(int x, int y) {
        location[0]=x;
        location[1]=y;
    }

    public int[] getLocation() {
        return location;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
