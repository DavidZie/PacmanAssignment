package GameComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Fruit extends Label {

    private int id;
    private int worth;

    private boolean out;

    private BufferedImage myImage;
    private int repeats;
    private Timer timer;

    Fruit(int id){
        this.id=id;
        worth=(id+1)*100;
        myImage = gameImagesArray[3][id];
        repeats=0;
        out = false;
        timer = new Timer(500,e -> {
            repeats++;
            if (repeats%2==1){
                myImage = blackImage();
            } else {myImage = gameImagesArray[3][id];}
            if (repeats==6){
                timer.stop();
                repeats=0;
            }

        });
        timer.start();
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public int getWorth() {
        return worth;
    }

    public BufferedImage getMyImage() {
        return myImage;
    }

    public Timer getTimer() {
        return timer;
    }

    //---------------------------Methods-----------------------//
    private BufferedImage blackImage(){
        BufferedImage image = new BufferedImage(pieceSize, pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        return image;
    }

}
