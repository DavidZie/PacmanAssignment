package GameComponents;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import static Logic.Globals.*;

public class Piece extends JLabel {


    private BufferedImage image;
    private boolean eaten=false;
    private boolean wall;
    int x,y;

    Piece(int x, int y,Stack data){
        super();
        this.x=x;
        this.y=y;
        setSize(pieceSize,pieceSize);
        image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        drawData(data);
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
    public void drawData(Stack s){
        if (s==null)
            return;
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0,getWidth(),getHeight());
        while (!s.empty()){
            int popped = (int)s.pop();
            switch (popped){
                case 0:
                    drawRegularPill();
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
                case 6:
                    drawEnergyPill();
                    break;
                case 7:
                    drawApple();
                    break;
                case 8:
                    drawPineApple();
                    break;
                case 9:
                    drawStrabarry();
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

    private void drawRegularPill(){
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(getWidth()/2,getHeight()/2,2,2);
    }

    private void drawEnergyPill(){
        BufferedImage energyPill= null;
        try {
            energyPill = ImageIO.read(new File("C:/Users/peleg/IdeaProjects/PacmanAssignment/Images/water.png"));
            setImage(energyPill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void drawApple(){
        BufferedImage apple= null;
        try {
            apple = ImageIO.read(new File("C:/Users/peleg/IdeaProjects/PacmanAssignment/src/Images/apple.png"));
            setImage(apple);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawPineApple(){
        BufferedImage pineapple= null;
        try {
            pineapple = ImageIO.read(new File("C:/Users/peleg/IdeaProjects/PacmanAssignment/src/Images/pineapple.png"));
            setImage(pineapple);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void drawStrabarry(){
        BufferedImage strabbary= null;
        try {
            strabbary = ImageIO.read(new File("C:/Users/peleg/IdeaProjects/PacmanAssignment/src/Images/strabary.png"));
            setImage(strabbary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
