package GameComponents.Players;

import GameComponents.Piece;
import Visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.*;

public abstract class Ghost implements Visitable {

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

    public int getId() {
        return id;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void insert(Piece piece){
        piece.setImage(image);
        piece.repaint();
    }

    public void impact(Visitor visitor) {
        visitor.visit(this);
    }
}
