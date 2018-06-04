package GameComponents.Players;


import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.gameFrame;
import static Logic.Globals.gameImagesArray;
import static Logic.Globals.pieceSize;

public class Ginky extends Ghost {
    public Ginky() {
        super(0,true);
        setupTimer();
    }


    private void setupTimer(){
        repeats = 0;
        timer = new Timer(333, e -> {
            if (repeats<=21){
                repeats++;
                return;
            }
            image.getGraphics().drawImage(gameImagesArray[4][0], 0, 0, pieceSize, pieceSize, null);
            setRoute(AStar.search(this,gameFrame.getBoard()));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this,gameFrame.getBoard());

            if (repeats==27)
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
