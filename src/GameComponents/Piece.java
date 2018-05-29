package GameComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import static Logic.Globals.imagesPath;
import static Logic.Globals.pieceSize;

public class Piece extends JLabel {


    int x, y;
    private BufferedImage image;
    private boolean eaten = false;
    private boolean wall = true;
    private boolean ghostHouse;
    private int worth;

    Piece(int x, int y, Stack data) {
        super();
        this.x = x;
        this.y = y;
        setSize(pieceSize, pieceSize);
        image = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        drawData(data);
        setIcon(new ImageIcon(image));
    }//Constructor

    //---------------------------Getters and Setter-----------------------//


    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isGhostHouse() {
        return ghostHouse;
    }

    public void setGhostHouse(boolean ghostHouse) {
        this.ghostHouse = ghostHouse;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        setIcon(new ImageIcon(image));
    }

    public int getWorth() {
        return worth;
    }

    //-------------------Construction Time Methods------------------------//

    //-----------------------------Methods-------------------------------//
    public void drawData(Stack s) {
        if (s == null)
            return;
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        while (!s.empty()) {
            int popped = (int) s.pop();
            switch (popped) {
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
                    break;
                case 6:
                    drawEnergyPill();
                    break;
                case 7:
                    wall = true;
                    ghostHouse = true;
                    break;
            }
        }
    }

    //------------------------Drawings-----------------------//

    private void drawNorthWall() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), 2);
        wall = true;
    }

    private void drawEastWall() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(getWidth() - 1, 0, 2, getHeight());
        wall = true;
    }

    private void drawSouthWall() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, getHeight() - 1, getWidth(), 2);
        wall = true;
    }

    private void drawWestWall() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 2, getHeight());
        wall = true;
    }

    private void drawRegularPill() {
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(getWidth() / 2, getHeight() / 2, 2, 2);
        wall=false;
        worth = 10;
    }


    private void drawEnergyPill(){
        BufferedImage energyPill;
        try {
            energyPill = ImageIO.read(new File(imagesPath+"\\water.png"));
            setImage(energyPill);
        } catch (IOException e) {
            e.printStackTrace();
        }
        wall = false;
        worth = 50;
    }
}
