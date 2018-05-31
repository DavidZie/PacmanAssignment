package GameComponents.Players;

import GameComponents.Piece;
import Visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Pacman implements Visitable {

    private BufferedImage image;//Should be Array of Different Animations.
    private int facing;
    private int[] location;
    private int current;
    private int lastMoveNumber;

    public Pacman() {
        image = gameImagesArray[0][0];
        facing = 2;
        current =0 ;
        Timer timer = new Timer(200, e -> {
            current = (current+1)%3;
            rotate(facing);
            image = gameImagesArray[0][current];
        });
        timer.start();
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getLastMoveNumber() {
        return lastMoveNumber;
    }

    public void rotate(int direction) {

        switch (direction){
            case 1:
                lookUp();
                break;
            case 2:
                lookRight();
                break;
            case 3:
                lookDown();
                break;
            case 4:
                lookLeft();
                break;
        }
        facing = direction;
    }

    private void lookUp(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(-90), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][current],null,0,0);
        image = newImage;
    }

    private void lookRight(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][current],null,0,0);
        image = newImage;
    }

    private void lookDown(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(90), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][current],null,0,0);
        image = newImage;
    }

    private void lookLeft(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][current],pieceSize,0,-pieceSize,pieceSize,null);
        image = newImage;
    }

    public void changeDirection(int newDirection, Piece[][] pieces) {
        if (newDirection == lastMoveNumber)
            return;
        switch (newDirection) {
            case 1:
                if (!pieces[location[0] - 1][location[1]].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 2:
                if (!pieces[location[0]][location[1] + 1].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 3:
                if (!pieces[location[0] + 1][location[1]].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 4:
                if (!pieces[location[0]][location[1] - 1].isWall())
                    lastMoveNumber = newDirection;
                break;
        }
    }



    public void impact(Visitor visitor) {visitor.visit(this);}

}
