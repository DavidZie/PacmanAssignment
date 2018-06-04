package GameComponents.Players;

import GameComponents.Board;
import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.gameFrame;

public class Fireball extends Ghost {

    Fireball(int id){
        super(id,false);
        setupTimer();
    }
    private void setupTimer(){
        repeats = 0;
        timer = new Timer(200, e -> {
            Movement.moveGhost(facing,this,gameFrame.getBoard());
            repeats++;
        });
    }


    @Override
    public void visit(Pacman pacman, Board board) {

    }

    @Override
    public void visit(Ghost ghost,Board board) {

    }
}
