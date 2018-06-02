package GameComponents.Players;

import Logic.Movement;

import javax.swing.*;

public class Water extends Ghost {

    Water(int id){
        super(id,false);
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(250,e -> {
            try {Movement.moveGhost(facing,this);}
            catch (Exception ignored){}
            repeats++;

        });
    }

    @Override
    public void visit(Pacman pacman) {

    }

    @Override
    public void visit(Ghost ghost) {

    }
}
