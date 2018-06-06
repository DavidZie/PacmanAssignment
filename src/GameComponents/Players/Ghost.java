package GameComponents.Players;

import GameComponents.Board;
import GameComponents.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.gameFrame;
import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public abstract class Ghost implements Visited {

    /**
     * All Ghosts extend this class and use some of it's functionality.
     * Could act Exactly the same or differently.
     * IMPORTANT: subclasses that use fields that were already commented on here, will not be commented on again.
     */

    private int id;//For ghost Type Identification.
    protected BufferedImage image;//Ghost Image.
    private BufferedImage coveredImage;//Save the image upon which the ghost is standing on so it'll be put back when the Ghost move.
    private int[] location;//Ghost location on board as an array with 2 cells {x,y}.
    protected Timer timer;//Timer for Ghosts movement and actions.
    private Timer killTimer;//Timer Used when the Ghost kill pacman for visuals.
    int repeats;//Number of ticks the Timer already ran.
    private Stack route;//Stack Contains moves to get to pacman in the shortest route.
    private boolean chasing;//For whether the Ghost is chasing pacman.
    int facing;//What Direction the Ghost is facing.
    Ghost weapon;//Some Ghosts can shoot weapons which act similar to ghosts only "dumber".
    boolean loaded;//For whether Ghost is ready to fire.


    Ghost(int id,boolean ghost){
        image = new BufferedImage(pieceSize, pieceSize, BufferedImage.TYPE_INT_ARGB);
        coveredImage = drawBlackImage();
        Graphics g = image.getGraphics();
        if (ghost) {
            this.id = id;
            g.drawImage(gameImagesArray[4][id], 0, 0, pieceSize, pieceSize, null);
            route = new Stack();
        } else {
            g.drawImage(gameImagesArray[5][id-1],0,0,pieceSize, pieceSize,null);
        }
        location=new int[2];
    }//Constructor. Draw Ghost image and set its location.

    //-----------------------Getters and Setters--------------------------//

    public int getId() {
        return id;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public Timer getTimer() {
        return timer;
    }

    public Ghost getWeapon() {
        return weapon;
    }

    public BufferedImage getCoveredImage() {
        return coveredImage;
    }

    public void setCoveredImage(BufferedImage coveredImage) {
        this.coveredImage = coveredImage;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void insert(Piece piece){
        piece.setImage(image);
        piece.repaint();
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int x, int y) {
        location[0] = x;
        location[1] = y;
    }//To make it easier to set location.

    Stack getRoute() {
        return route;
    }

    void setRoute(Stack route) {
        this.route = route;
    }

    public boolean isChasing() {
        return chasing;
    }

    void setChasing(boolean chasing) {
        this.chasing = chasing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isLoaded() {
        return loaded;
    }
    //---------------------------Methods---------------------------//

    public void fire(Piece[][] pieces){
        int myX=0,myY=0;
        switch (facing){
            case 1:
                myX=location[0]-1;
                myY=location[1];
                break;
            case 2:
                myX=location[0];
                myY=location[1]+1;
                break;
            case 3:
                myX=location[0]+1;
                myY=location[1];
                break;
            case 4:
                myX=location[0];
                myY=location[1]-1;
                break;
        }
        if (pieces[myX][myY].isWall())
            return;
        weapon.setLocation(myX,myY);
        weapon.setFacing(facing);
        weapon.insert(pieces[myX][myY]);
        weapon.getTimer().start();
        loaded=false;
        fired();
    }//Get fired Ghost first cell and Fire. Also prevent ghost from shooting if it is facing a wall.

    private BufferedImage drawBlackImage(){
        BufferedImage image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, pieceSize, pieceSize);
        return image;
    }//For Quick and easy use.




    public void fired(){}//Reset necessary fields after Weapon Fire.

    public void killAnimation(Board board){
        repeats=0;
        timer.stop();
        Graphics gGhost = image.getGraphics();
        gGhost.setColor(Color.BLACK);
        gameFrame.getBoard().stop();
        killTimer = new Timer(7,e -> {
            gGhost.fillRect(repeats % 32, repeats / 32, 3, 3);
            repeats += 3;
            if (repeats > 1024){
                killTimer.stop();
            }
            board.repaint();

        });
        killTimer.start();
    }//Start the kill Timer and visual the killing.


}
