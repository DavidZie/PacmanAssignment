package GameComponents.Players;

import GameComponents.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Pacman implements Visited {

    private BufferedImage image;//Should be Array of Different Animations.
    private int[] location;
    private int currentImage;

    public Pacman() {
        image = gameImagesArray[0][0];
        currentImage =0 ;
        Timer timer = new Timer(200, e -> {
            currentImage = (currentImage+1)%3;
            image = gameImagesArray[0][currentImage];
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
    }

    private void lookUp(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(-90), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][currentImage],null,0,0);
        image = newImage;
    }

    private void lookRight(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][currentImage],null,0,0);
        image = newImage;
    }

    private void lookDown(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(90), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][currentImage],null,0,0);
        image = newImage;
    }

    private void lookLeft(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[0][currentImage],pieceSize,0,-pieceSize,pieceSize,null);
        image = newImage;
    }


    public void visit(Pacman pacman){
        Graphics g = pacman.getImage().getGraphics();
        g.setColor(Color.BLACK);
        for (int i=0;i<pieceSize;i++){
            for (int j=0;j<pieceSize;j++){
                g.fillRect(j,i,1,1);
            }
        }
    }

    @Override
    public void impact(Visitor visitor) {

    }
}
