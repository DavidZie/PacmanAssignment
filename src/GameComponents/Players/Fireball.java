package GameComponents.Players;

import Logic.Movement;

import javax.swing.*;

public class Fireball extends Ghost {

    Fireball(int id){
        super(id,false);
        setupTimer();
    }
    private void setupTimer(){
        repeats = 0;
        timer = new Timer(200, e -> {

            Movement.moveGhost(facing,this);
            repeats++;

        });
    }

    @Override
    public void dismiss() {

    }

    @Override
    public void visit(Pacman pacman) {

    }

    @Override
    public void visit(Ghost ghost) {

    }
}