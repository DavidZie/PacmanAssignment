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
    private int[] location;//Location on Board.
    private int currentImage;//Current displayed Image.
    private int level;//pacman level.
    private boolean frozen;//is pacman Frozen?
    private int repeats;//Timer Ticker.
    private BufferedImage collisionImage;//Image Displayed when pacman freezes inky.
    private Timer timer;//Timer For image Changing and freezing pacman if necessary.
    private int facing;//Save direction in which Pacman is facing for reDrawing in the right Direction.
    private Timer deathTimer;//Pacman Death timer for visuals of pacman death.

    public Pacman(int level) {
        repeats=0;
        image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(gameImagesArray[level/2][level/3],0,0,null);
        this.level=level;
        currentImage =0 ;
        timer = new Timer(200, e -> {
            if (level<2) {
                currentImage = (currentImage + 1) % 3;
                rotate(facing);
            } else {
                BufferedImage newImage = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
                newImage.getGraphics().drawImage(gameImagesArray[1][level-2],0,0,null);
                image = newImage;}
            if (repeats==15)
                frozen=false;
            repeats++;

        });
        timer.start();
    }//Constructor.

    //-----------------------Getters and Setters--------------------------//
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
    }//Basically setFreeze but we only assign true to it here.

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int[] getLocation() {
        return location;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }


    public void setLocation(int[] location) {
        this.location = location;
    }

    public BufferedImage getImage() {
        return image;
    }

    //-----------------------Methods------------------------//

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
    }//Rotate Pacman in needed Direction.

    private void lookUp(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(-90), pieceSize/2, pieceSize/2);
        if (level==1){
            g.drawImage(gameImagesArray[0][currentImage],0,0,null);
        }else g.drawImage(gameImagesArray[level/2][level/3],null,0,0);
        image = newImage;
    }

    private void lookRight(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        if (level==1){
            g.drawImage(gameImagesArray[0][currentImage],0,0,null);
        }else g.drawImage(gameImagesArray[level/2][level/3],null,0,0);
        image = newImage;
    }

    private void lookDown(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(90), pieceSize/2, pieceSize/2);
        if (level==1){
            g.drawImage(gameImagesArray[0][currentImage],0,0,null);
        }else g.drawImage(gameImagesArray[level/2][level/3],null,0,0);
        image = newImage;
    }

    private void lookLeft(){
        BufferedImage newImage = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
        g.rotate(Math.toRadians(0), pieceSize/2, pieceSize/2);
        if (level==1){
            g.drawImage(gameImagesArray[0][currentImage],pieceSize,0,-pieceSize,pieceSize,null);
        }else g.drawImage(gameImagesArray[level/2][level/3],pieceSize,0,-pieceSize,pieceSize,null);
        image = newImage;
    }
    //Redraw image after rotating it to the right direction.

    private void blackImage(BufferedImage image){
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize);
    }//Black image for quick access and use.

    private void deathAnimation(Ghost ghost, Board board){
        repeats=0;
        timer.stop();
        Graphics gGhost = ghost.getImage().getGraphics();
        Graphics gPacman = image.getGraphics();
        gPacman.setColor(Color.BLACK);
        gGhost.setColor(Color.BLACK);
        frozen=true;
        gameFrame.getBoard().stop();
        deathTimer = new Timer(7,e -> {
            gGhost.fillRect(repeats % 32, repeats / 32, 3, 3);
            gPacman.fillRect(repeats % 32, repeats / 32, 3, 3);
            repeats += 3;
            if (repeats > 1024){
                deathTimer.stop();
                if (board.getLives()==0){
                    gameFrame.endGame(board.getCurrentScore());
                } else {board.cleanBoard();
                return;
                }
            }
            board.repaint();

        });
        deathTimer.start();
    }//If Pacman is Killed, initiate the timer to show the dying animation and check whether the game is over.

    public void attack(Ghost ghost){
        ghost.impact(this);
    }//Attack a ghost. this is called when pacman stands on the same tile as a ghost.



    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this,gameFrame.getBoard());
    }//impact a Visitor (Accept)

    @Override
    public void visit(Pacman pacman, Board board) { }//Pacman never visits pacman.

    @Override
    public void visit(Ginky ginky, Board board) {
        switch (level){
            case 1:
                board.setLives(board.getLives()-1);
                deathAnimation(ginky,board);
                break;
            case 2:
                ginky.setRepeats(22);
                ginky.setDisappear(true);
                image.getGraphics().drawImage(ginky.getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
                ginky.setChasing(false);
                break;
            case 3:
                ginky.getTimer().stop();
                image.getGraphics().drawImage(ginky.getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
                ginky.setDead(true);
                break;
        }
    }//In Case Ginky is visiting, act according to pacman level.

    @Override
    public void visit(Inky inky, Board board) {
        switch (level){
            case 1:
                board.setLives(board.getLives()-1);
                deathAnimation(inky,board);
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
    }//In Case inky is visiting, act according to pacman level.

    @Override
    public void visit(Blinky blinky, Board board) {
        board.setLives(board.getLives()-1);
        deathAnimation(blinky,board);
    }//In Case Blinky is visiting, kill pacman.

    @Override
    public void visit(Water water, Board board) {
        switch (level){
            case 1:
                board.setLives(board.getLives()-1);
                deathAnimation(water,board);
                board.getGhosts()[1].killAnimation(board);
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
    }//In Case Water Attack is visiting, act according to pacman level.

    @Override
    public void visit(Fireball fireball, Board board) {
        board.setLives(board.getLives()-1);
        deathAnimation(fireball,board);
        board.getGhosts()[2].killAnimation(board);
    }//In Case Fireball is visiting, kill pacman.

    @Override
    public void visit(ExtraGhost extraGhost, Board board) {
        board.setLives(board.getLives()-1);
        deathAnimation(extraGhost,board);
    }//In Case Bonus Ghost is visiting, kill pacman.
}
