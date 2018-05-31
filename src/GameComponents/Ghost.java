package GameComponents;

import Visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.*;

public class Ghost implements Visitor.Visitable {

    BufferedImage image;
    Timer timer;
    int repeats;
    int id;

    Ghost(int id){
        this.id = id;
        image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(gameImagesArray[4][id],0,0,pieceSize, pieceSize,null);
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public int getId() {
        return id;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void impact(Visitor visitor) {
        visitor.visit(this);
    }
}
