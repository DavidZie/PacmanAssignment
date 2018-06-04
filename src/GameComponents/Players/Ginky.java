package GameComponents.Players;


import GameComponents.Board;
import Logic.AStar;
import Logic.Movement;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.*;

public class Ginky extends Ghost {

    private boolean disappear;
    private boolean dead;

    public Ginky() {
        super(0,true);
        setupTimer();
    }


    private void setupTimer(){
        repeats = 0;
        timer = new Timer(333, e -> {
            if (repeats<=21|dead){
                repeats++;
                return;
            }

            if (disappear) {
                if (repeats==22){
                    BufferedImage newImage = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
                    Graphics g = newImage.getGraphics();
                    g.setColor(Color.BLACK);
                    g.fillRect(0,0,pieceSize,pieceSize);
                    image.getGraphics().drawImage(newImage, 0, 0, pieceSize, pieceSize, null);
                }
                else if (repeats==37)
                    disappear=false;
                else image.getGraphics().drawImage(getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
            } else image.getGraphics().drawImage(gameImagesArray[4][0], 0, 0, pieceSize, pieceSize, null);
            setRoute(AStar.search(this,gameFrame.getBoard()));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this,gameFrame.getBoard());

            if (repeats==27)
                setChasing(true);

            repeats++;

        });
    }


    @Override
    public void visit(Pacman pacman, Board board) {

        switch (pacman.getLevel()){
                case 1:
                    board.setLives(board.getLives()-1);
                    if (board.getLives()==0){
                        gameFrame.endGame();
                    } else board.cleanBoard();
                    break;
                case 2:
                    repeats=22;
                    disappear = true;
                    image.getGraphics().drawImage(getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
                    setChasing(false);
                    break;
                case 3:
                    //Die

                    dead=true;
                    timer.stop();
                    image.getGraphics().drawImage(getCoveredImage(), 0, 0, pieceSize, pieceSize, null);
                    break;
            }
            board.repaint();





    }

    @Override
    public void visit(Ghost ghost,Board board) {

    }

}
