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
        setSize(pieceSize,pieceSize);
        image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        addWalls(walls);
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
        this.image = image;
        setIcon(new ImageIcon(image));
    }

    public BufferedImage getImage() {
        return image;
    }

    //-------------------Construction Time Methods------------------------//





    //-----------------------------Methods-------------------------------//
    public void addWalls(Stack s){
        if (s==null)
            return;
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0,getWidth(),getHeight());
        while (!s.empty()){
            int popped = (int)s.pop();
            switch (popped){
                case 0:
                    drawFood();
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
        g.fillRect(0, 0, getWidth(), 2);
        wall = true;
    }
    private void drawEastWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(getWidth() - 1,0,2,getHeight());
        wall = true;
    }
    private void drawSouthWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, getHeight() - 1, getWidth(), 2);
        wall = true;
    }
    private void drawWestWall(){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 2, getHeight());
        wall = true;
    }

    public void drawFood(){
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(getWidth()/2,getHeight()/2,2,2);
    }


}
