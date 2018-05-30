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
    private int timerRepeats;
    private Timer timer;

    Fruit(int id){
        this.id=id;
        worth=(id+1)*100;
        myImage = gameImagesArray[3][id];
        timerRepeats=0;
        out = false;
        timer = new Timer(500,e -> {
            timerRepeats++;
            if (timerRepeats%2==1){
                myImage = blackImage();
            } else {myImage = gameImagesArray[3][id];}
            if (timerRepeats==6)
                timer.stop();

        });
        timer.start();
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

    //---------------------------Methods-----------------------//
    private BufferedImage blackImage(){
        BufferedImage image = new BufferedImage(pieceSize, pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        return image;
    }

}
