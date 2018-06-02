package GameComponents.Players;


import GameComponents.Piece;
import Logic.Movement;
import Logic.Routing;

import javax.swing.*;
import java.util.Stack;

public class Ginky extends Ghost {
    public Ginky() {
        super(0,true);
        setupTimer();
    }


    private void setupTimer(){
        repeats = 0;
        timer = new Timer(333, e -> {

            setRoute(Routing.calculate(this));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this);

            if (repeats/3>=6)
                setChasing(true);

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
