package GameComponents.Players;

import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.gameFrame;

public class Fireball extends Ghost implements Visited {

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
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }
}
