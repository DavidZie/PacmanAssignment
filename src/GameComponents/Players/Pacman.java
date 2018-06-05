package GameComponents.Players;

import GameComponents.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameFrame;
import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Pacman implements Visited,Visitor {

    private BufferedImage image;//Should be Array of Different Animations.
    private int[] location;
    private int currentImage;
    private int level;
    private boolean frozen;
    private int repeats;
    private BufferedImage collisionImage;

    public Pacman(int level) {
        repeats=0;
        image = gameImagesArray[level/2][level/3];
        this.level=level;
        currentImage =0 ;
        Timer timer = new Timer(200, e -> {
            if (level<2) {
                currentImage = (currentImage + 1) % 3;
                image = gameImagesArray[0][currentImage];
            } else {image = gameImagesArray[1][level-2];}
            if (repeats==15)
                frozen=false;
            repeats++;

        });
        timer.start();
    }

    public void setCollisionImage(BufferedImage collisionImage) {
        this.collisionImage = collisionImage;
    }

    public BufferedImage getCollisionImage() {
        return collisionImage;
    }

    public boolean isFrozen() {
        return frozen;
    }

    private void freeze() {
        frozen=true;
        repeats=0;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int[] getLocation() {
        return location;
    }

    public int getLevel() {
        return level;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void rotate(int direction) {

        switch (direction){
            case 1:
                lookUp();
                break;
            case 2:
                lookRight();
                break;
            case 3:
                lookDown();
                break;
            case 4:
                lookLeft();
                break;
        }
    }

    private void lookUp(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(-90), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[level/2][level/3],null,0,0);
        image = newImage;
    }

    private void lookRight(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[level/2][level/3],null,0,0);
        image = newImage;
    }

    private void lookDown(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(90), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[level/2][level/3],null,0,0);
        image = newImage;
    }

    private void lookLeft(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        g.drawImage(gameImagesArray[level/2][level/3],pieceSize,0,-pieceSize,pieceSize,null);
        image = newImage;
    }

    private void blackImage(BufferedImage image){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
    }

    public void attack(Ghost ghost){
        ghost.impact(this);
    }



    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this,gameFrame.getBoard());
    }

    @Override
    public void visit(Pacman pacman, Board board) {

    }

    @Override
    public void visit(Ghost ghost, Board board) {
    }

    @Override
    public void visit(Ginky ginky, Board board) {
        switch (level){
            case 1:
                board.setLives(board.getLives()-1);
                if (board.getLives()==0){
                    gameFrame.endGame(board.getCurrentScore());
                } else board.cleanBoard();
                break;
            case 2:
                ginky.setRepeats(22);
                ginky.setDisappear(true);
                image.getGraphics().drawImage(ginky.getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
                ginky.setChasing(false);
                break;
            case 3:
                ginky.setDead(true);
                ginky.getTimer().stop();
                image.getGraphics().drawImage(ginky.getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
                break;
        }
    }

    @Override
    public void visit(Inky inky, Board board) {
        switch (level){
            case 1:
                board.setLives(board.getLives()-1);
                if (board.getLives()==0){
                    gameFrame.endGame(board.getCurrentScore());
                } else board.cleanBoard();
                break;
            case 2:
                freeze();
                inky.setChasing(false);
                board.getCurrentScore()[0]=board.getCurrentScore()[0]-10;
                break;
            case 3:
                inky.freeze();
                setCollisionImage(inky.getImage());
                blackImage(inky.getCoveredImage());
                break;
        }
    }

    @Override
    public void visit(Blinky blinky, Board board) {
        board.setLives(board.getLives()-1);
        if (board.getLives()==0){
            gameFrame.endGame(board.getCurrentScore());
        } else board.cleanBoard();
    }

    @Override
    public void visit(Water water, Board board) {
        switch (level){
            case 1:
                board.setLives(board.getLives()-1);
                if (board.getLives()==0){
                    gameFrame.endGame(board.getCurrentScore());
                } else board.cleanBoard();
                break;
            case 2:
                freeze();
                board.getCurrentScore()[0]=board.getCurrentScore()[0]-10;
                break;
            case 3:
                board.getGhosts()[1].setRepeats(7);
                board.getGhosts()[1].setChasing(false);
                break;
        }
    }

    @Override
    public void visit(Fireball fireball, Board board) {
        board.setLives(board.getLives()-1);
        if (board.getLives()==0){
            gameFrame.endGame(board.getCurrentScore());
        } else board.cleanBoard();
    }

    @Override
    public void visit(ExtraGhost extraGhost, Board board) {
        board.setLives(board.getLives()-1);
        if (board.getLives()==0){
            gameFrame.endGame(board.getCurrentScore());
        } else board.cleanBoard();
    }


}
