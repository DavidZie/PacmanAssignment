package GameComponents;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.*;

public class Piece extends JLabel {


    private BufferedImage image;
    private boolean eaten=false;
    private boolean wall;
    int x,y;

    Piece(int x, int y,Stack walls){
        super();
        this.x=x;
        this.y=y;
        addWalls(walls);
        if (!wall)
            image = gameImagesArray[1];
        setIcon(new ImageIcon(image));
    }//Constructor

    //---------------------------Getters and Setter-----------------------//


    public boolean isEaten() {
        return eaten;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public void setImage(BufferedImage image) {
        eaten=true;
        this.image = image;
        setIcon(new ImageIcon(image));
    }

    public BufferedImage getImage() {
        return image;
    }

    //-------------------Construction Time Methods------------------------//

    public void addWalls(Stack s){
        image = new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(gameImagesArray[0],0,0,null);
        while (!s.empty()){
            int popped = (int)s.pop();
            switch (popped){
                case 0:
                    break;
                case 1:
                    drawNorthWall();
                    break;
                case 2:
                    drawEastWall();
                    break;
                case 3:
                    drawSouthWall();
                    break;
                case 4:
                    drawWestWall();
                    break;
                case 5:
                    wall = true;
                    break;
            }
        }
    }

    private void drawNorthWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, pieceSize, 2);
        wall = true;
    }
    private void drawEastWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(pieceSize-1,0,2,pieceSize);
        wall = true;
    }
    private void drawSouthWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, pieceSize - 1, pieceSize, 2);
        wall = true;
    }
    private void drawWestWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 2, pieceSize);
        wall = true;
    }

    //-----------------------------Methods-------------------------------//
}
