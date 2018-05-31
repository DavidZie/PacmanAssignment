package GameComponents.Players;

import GameComponents.Piece;
import Logic.Movement;
import Visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.*;

public abstract class Ghost implements Visitable {

    private BufferedImage image;
    private int[] location;
    protected Timer timer;
    private int repeats;
    private int id;
    private Stack route;


    Ghost(int id){
        this.id = id;
        image = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(gameImagesArray[4][id],0,0,pieceSize, pieceSize,null);
        setupTimer();

    }

    public int getId() {
        return id;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void insert(Piece piece){
        piece.setImage(image);
        piece.repaint();
    }

    public Stack getRoute() {
        return route;
    }

    public void setRoute(Stack route) {
        this.route = route;
    }

    public void changeDirection(int newDirection, Piece[][] pieces) {

        switch (newDirection) {
            case 1:
                if (!pieces[location[0] - 1][location[1]].isWall())

                break;
            case 2:
                if (!pieces[location[0]][location[1] + 1].isWall())

                break;
            case 3:
                if (!pieces[location[0] + 1][location[1]].isWall())

                break;
            case 4:
                if (!pieces[location[0]][location[1] - 1].isWall())

                break;
        }
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(250, e -> {
            if (repeats%20==0) //Every 5 Seconds Re-calculate Route.
                route = calculateRoute();

        });
    }




    private Stack calculateRoute(){
        Stack route = new Stack();
        /***
         *  Find Shortest Route To Destination and push the moves into a Stack.
         */
        return route;
    }





    public void impact(Visitor visitor) {
        visitor.visit(this);
    }


}
