package Visitor;

import GameComponents.Players.Ghost;
import GameComponents.Players.Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class impact implements Visitor {
    @Override
    public void visit(Pacman pacman) {
        Graphics g = pacman.getImage().getGraphics();
        g.setColor(Color.BLACK);
        for (int i=0;i<pieceSize;i++){
            for (int j=0;j<pieceSize;j++){
                g.fillRect(j,i,1,1);
            }
        }
    }

    @Override
    public void visit(Ghost ghost) {
        final int[] expiration = {0};
        Timer timer = new Timer(250,e -> {
            BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0,0,pieceSize,pieceSize);
            g.rotate(Math.toRadians(90* expiration[0]), pieceSize/2, pieceSize/2);
            g.drawImage(gameImagesArray[0][ghost.getId()],null,0,0);
            ghost.setImage(newImage);
            expiration[0]++;
            if (expiration[0] ==16){
                //Death
            }

        });
        timer.start();


    }
}
