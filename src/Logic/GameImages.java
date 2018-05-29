package Logic;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.*;

public class GameImages {

    protected static BufferedImage[] imagesCreator(){
        BufferedImage[] images = new BufferedImage[20];
        for (int i=0;i<images.length;i++)
            images[i] = new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);

        images[0] = new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
        drawCells(0,images[0].getGraphics());//Cell that was already visited.
        drawCells(1,images[1].getGraphics());//Cell With Regular food.
        drawCells(2,images[2].getGraphics());//Cell With Special food.

        return images;
    }
    private static void drawCells(int type, Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,25,25);
        g.setColor(Color.WHITE);
        switch (type){
            case 1:
                g.fillOval(12,12,2,2);
                break;
            case 2:
                g.fillOval(12,12,5,5);
                break;
        }
    }

    private static void drawWalls(int type,Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,25,25);
        g.setColor(Color.BLUE);
        switch (type){
            case 3:
                g.fillRect(0, pieceSize - 1, pieceSize, 2);
                break;
            case 4:
                g.fillRect(0, 0, 2, pieceSize);
                break;
            case 5:
                g.fillRect(0, 0, pieceSize, 2);
                break;
            case 6:
                g.fillRect(pieceSize-1,0,2,pieceSize);
                break;
        }
    }


}
