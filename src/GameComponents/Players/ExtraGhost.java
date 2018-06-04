package GameComponents.Players;

import GameComponents.Board;
import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

import java.awt.image.BufferedImage;

import static Logic.Globals.gameFrame;

public class ExtraGhost extends Ghost implements Visitor {
    public ExtraGhost(int id, BufferedImage coveredImage) {
        super(id+1,true);
        setCoveredImage(coveredImage);
        setupTimer();
    }


    private void setupTimer(){
        repeats = 0;
        setChasing(true);
        timer = new Timer(333, e -> {

           setRoute(AStar.search(this,gameFrame.getBoard()));
           if (!getRoute().empty())
               Movement.moveGhost((int)getRoute().pop(),this,gameFrame.getBoard());
           repeats++;
        });
        timer.start();
    }

    @Override
    public void visit(Pacman pacman, Board board) {
        board.setLives(board.getLives()-1);
        if (board.getLives()==0){
            gameFrame.endGame();
        } else board.cleanBoard();
    }

    @Override
    public void visit(Ghost ghost, Board board) {

    }
}
