package GameComponents.Players;


import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

public class Ginky extends Ghost {
    public Ginky() {
        super(0,true);
        setupTimer();
    }


    private void setupTimer(){
        repeats = 0;
        timer = new Timer(333, e -> {

            setRoute(AStar.search(this));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this);

            if (repeats/3>=6)
                setChasing();

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
