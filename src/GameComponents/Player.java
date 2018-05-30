package GameComponents;

import java.awt.*;
import java.awt.image.BufferedImage;


import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Player {

    private BufferedImage image;//Should be Array of Different Animations.
    private int facing;
    private int current;

    Player() {
        image = gameImagesArray[0][0];
        facing = 2;
        current =0 ;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void rotate(int direction) {
        if (facing==direction)
            return;

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


}
