package GameComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.*;

public class Piece extends JLabel {


    private BufferedImage image;
    private boolean eaten = false;
    private boolean wall = true;
    private boolean ghostHouse;
    private int worth;

    private Timer fruitTimer;
    private int repeats;
    private  Fruit fruit;

    Piece(String data) {
        super();
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
        if (eaten) {
            worth = 0;
            if (fruit != null) {
                fruitTimer.stop();
                killFruit();
                gameFrame.getBoard().eatenFruit(fruit);
            }
        }
    }

    public Timer getFruitTimer() {
        return fruitTimer;
    }

    public boolean isWall() {
        return wall;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isGhostHouse() {
        return ghostHouse;
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

    public void setWorth(int worth) {
        this.worth = worth;
    }

    //-------------------Construction Time Methods------------------------//

    //-----------------------------Methods-------------------------------//
    public void drawData(String s) {
        if (s == null)
            return;
        drawBlackImage();
        for (int i=0;i<s.length();i++){
            switch (s.charAt(i)) {
                case '0':
                    drawRegularPill();
                    break;
                case '1':
                    drawNorthWall();
                    break;
                case '2':
                    drawEastWall();
                    break;
                case '3':
                    drawSouthWall();
                    break;
                case '4':
                    drawWestWall();
                    break;
                case '5':
                    break;
                case '6':
                    drawEnergyPill();
                    break;
                case '7':
                    wall = false;
                    ghostHouse = true;
                    break;
                case '8':
                    wall=false;
                    break;
            }
        }
    }

    //------------------------Drawings-----------------------//

    private void drawBlackImage(){
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

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
        Graphics g = image.getGraphics();
        g.drawImage(gameImagesArray[2][0],0,0,null);
        wall = false;
        worth = 50;
    }

    public void addFruit(Fruit fruit){
        repeats=0;
        this.fruit = fruit;
        worth = fruit.getWorth()+worth;
        fruitTimer = new Timer(500,e -> {
            repeats++;
            if (repeats<=8) {
                if (repeats % 2 == 0) {
                    Graphics g = image.getGraphics();
                    g.drawImage(fruit.getMyImage(), 0, 0, null);
                    repaint();
                } else {
                    drawBlackImage();
                    repaint();
                }
            }
            else if (repeats>18&repeats<40) {
                BufferedImage blackImage = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
                Graphics g1 = blackImage.getGraphics();
                g1.setColor(Color.BLACK);
                g1.fillRect(0,0,pieceSize, pieceSize);
                if (!eaten) {
                    if (worth-fruit.getWorth()==10){
                        g1.setColor(Color.WHITE);
                        g1.fillOval(getWidth() / 2, getHeight() / 2, 2, 2);
                    } else if (worth-fruit.getWorth()==50){
                        g1.drawImage(gameImagesArray[2][0],0,0,null);
                    }
                }

                Graphics2D g = (Graphics2D) image.getGraphics();
                g.setComposite(AlphaComposite.SrcOver.derive(0.15f));
                g.drawImage(blackImage, 0, 0, null);
                repaint();
            } else if (repeats==40) {
                fruitTimer.stop();
                killFruit();
            }

        });
        fruitTimer.start();
    }
    public void killFruit(){
        String dataString = "";
        if (!eaten){
            worth -= fruit.getWorth();
            if (worth==10){
                dataString+="0";

            } else if (worth == 50) {
                dataString+="6";
            }
            drawData(dataString);
            fruit.setOut(false);
            fruit.getTimer().stop();
            fruit.setRepeats(0);
        }

    }
}
