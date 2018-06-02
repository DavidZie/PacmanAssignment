package GameComponents.Players;

import Logic.Movement;
import Logic.Routing;

import javax.swing.*;

import static Logic.Globals.gameImagesArray;

public class Blinky extends Ghost implements Visitor  {

    boolean charging;
    int weaponRepeater;

    public Blinky() {
        super(2,true);
        weapon = new Fireball(2);
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(1000, e -> {

            setRoute(Routing.calculate(this));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this);

            if (repeats==2){
                setChasing();
                charging=true;
            }
            if (charging)
                changeImage(repeats%2);

            if (repeats==7)
                loaded=true;

            repeats++;
        });
    }

    public void fired(){
        repeats=0;
        weaponRepeater = 0;
        charging=false;
        loaded=false;
    }

    private void changeImage(int num){
        switch (num){
            case 0:
                image.getGraphics().drawImage(gameImagesArray[4][2],0,0,null);
                break;
            case 1:
                image.getGraphics().drawImage(gameImagesArray[4][5],0,0,null);
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
