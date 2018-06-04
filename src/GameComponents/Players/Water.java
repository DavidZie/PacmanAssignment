package GameComponents.Players;

import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.gameFrame;

public class Water extends Ghost implements Visited {

    Water(int id){
        super(id,false);
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(250,e -> {
            try {Movement.moveGhost(facing,this,gameFrame.getBoard());}
            catch (Exception ignored){}
            repeats++;
        });
    }

    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }
}
