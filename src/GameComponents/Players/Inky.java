package GameComponents.Players;


import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.*;


public class Inky extends Ghost implements Visited {

    private boolean charging;

    public Inky() {
        super(1,true);
        weapon = new Water(1);
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(666, e -> {
            if (repeats<=15){
                repeats++;
                return;
            }
            if (!charging)
                image.getGraphics().drawImage(gameImagesArray[4][1], 0, 0, pieceSize, pieceSize, null);
            setRoute(AStar.search(this,gameFrame.getBoard()));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this,gameFrame.getBoard());

            if (repeats==18)
                setChasing(true);

            if (repeats==21)
                charging=true;

            if (charging)
                changeImage(repeats%2);

            if (repeats==29)
                loaded=true;

            repeats++;
        });
    }
    @Override
    public void fired(){
        repeats=16;
        charging=false;
        loaded=false;
    }



    private void changeImage(int num){
        switch (num){
            case 0:
                image.getGraphics().drawImage(gameImagesArray[4][1],0,0,null);
                break;
            case 1:
                image.getGraphics().drawImage(gameImagesArray[4][4],0,0,null);
                break;
        }
    }

    public void freeze(){
        setRepeats(7);
        setChasing(false);
    }

    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }

}
