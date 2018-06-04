package GameComponents.Players;

import GameComponents.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public abstract class Ghost implements Visitor {

    private int id;
    protected BufferedImage image;
    private BufferedImage coveredImage;
    private int[] location;
    protected Timer timer;
    int repeats;
    private Stack route;
    private boolean chasing;
    int facing;
    Ghost weapon;
    boolean loaded;


    Ghost(int id,boolean ghost){
        image = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        coveredImage = drawBlackImage();
        Graphics g = image.getGraphics();
        if (ghost) {
            this.id = id;
            g.drawImage(gameImagesArray[4][id], 0, 0, pieceSize, pieceSize, null);
            route = new Stack();
        } else {
            g.drawImage(gameImagesArray[5][id-1],0,0,pieceSize, pieceSize,null);
        }
        location=new int[2];
    }

    public int getId() {
        return id;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public Timer getTimer() {
        return timer;
    }

    public Ghost getWeapon() {
        return weapon;
    }

    public BufferedImage getCoveredImage() {
        return coveredImage;
    }

    public void setCoveredImage(BufferedImage coveredImage) {
        this.coveredImage = coveredImage;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void insert(Piece piece){
        piece.setImage(image);
        piece.repaint();
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    Stack getRoute() {
        return route;
    }

    void setRoute(Stack route) {
        this.route = route;
    }

    public boolean isChasing() {
        return chasing;
    }

    void setChasing() {
        this.chasing = true;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public void fire(Piece[][] pieces){
        int myX=0,myY=0;
        switch (facing){
            case 1:
                myX=location[0]-1;
                myY=location[1];
                break;
            case 2:
                myX=location[0];
                myY=location[1]+1;
                break;
            case 3:
                myX=location[0]+1;
                myY=location[1];
                break;
            case 4:
                myX=location[0];
                myY=location[1]-1;
                break;
        }
        if (pieces[myX][myY].isWall())
            return;
        weapon.setLocation(myX,myY);
        weapon.setFacing(facing);
        weapon.insert(pieces[myX][myY]);
        weapon.getTimer().start();
        loaded=false;
        fired();
    }

    private BufferedImage drawBlackImage(){
        BufferedImage image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, pieceSize, pieceSize);
        return image;
    }

    public void impact(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean isLoaded() {
        return loaded;
    }


    public void fired(){}


}
