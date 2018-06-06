package GameComponents;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Fruit extends Label {

    private int id;//Fruit ID.
    private int worth;//Fruit Points worth.

    private boolean out;//Is Fruit out on Board?

    private BufferedImage myImage;//Fruit Image.
    private int x,y;//Fruit Location.

    Fruit(int id){
        this.id=id;
        worth=(id+1)*100;
        myImage = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        myImage.getGraphics().drawImage(gameImagesArray[3][id],0,0,null);
        out = false;
        //timerSetup();
    }//Constructor
    //-----------------------Getters and Setters--------------------------//
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public int getWorth() {
        return worth;
    }

    public BufferedImage getMyImage() {
        return myImage;
    }

    public int getId() { return id; }

    //---------------------------Methods-----------------------//
    private BufferedImage blackImage(){
        BufferedImage image = new BufferedImage(pieceSize, pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        return image;
    }//Draw Black Image for Quick and easy use.
}
