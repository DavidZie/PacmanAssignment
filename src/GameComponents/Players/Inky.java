package GameComponents.Players;


import Logic.Movement;
import Logic.Routing;

import javax.swing.*;

import static Logic.Globals.gameImagesArray;


public class Inky extends Ghost {

    boolean charging;
    int weaponRepeater;

    public Inky() {
        super(1,true);
        weapon = new Water(1);
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(666, e -> {

            setRoute(Routing.calculate(this));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this);

            if (repeats==6){
                setChasing(true);
                charging=true;
            }
            if (charging)
                changeImage(repeats%2);

            if (repeats==21)
                loaded=true;

            repeats++;
        });
    }
    @Override
    public void fired(){
        repeats=0;
        weaponRepeater = 0;
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







    @Override
    public void visit(Pacman pacman) {

    }

    @Override
    public void visit(Ghost ghost) {

    }
}
