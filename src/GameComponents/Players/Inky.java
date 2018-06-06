package GameComponents.Players;


import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.*;


public class Inky extends Ghost implements Visited {

    private boolean charging;
    private int level;

    public Inky(int level) {
        super(1,true);
        weapon = new Water(1);
        this.level=level;
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

            if (repeats==26)
                setChasing(true);//Used for when Inky Freezes Pacmen.
            // Chasing was stopped because pacman was frozen and ticker was reset to 19. So we need to get away from pacman until it unfreezes.

            if (repeats==21&level>1)
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
    }//Switch Images. Called when charging weapon.

    public void freeze(){
        setRepeats(7);
        setChasing(false);
    }//Freeze Ghost.

    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }//Visit Pacman.

}
