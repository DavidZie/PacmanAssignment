package GameComponents.Players;

import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.*;

public class Blinky extends Ghost implements Visited  {

    private boolean charging;

    public Blinky() {
        super(2,true);
        weapon = new Fireball(2);
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;
        timer = new Timer(1000, e -> {
            if (repeats<=13){
                repeats++;
                return;
            }
            if (!charging)
                image.getGraphics().drawImage(gameImagesArray[4][2], 0, 0, pieceSize, pieceSize, null);
            setRoute(AStar.search(this,gameFrame.getBoard()));
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this,gameFrame.getBoard());

            if (repeats==15)
                setChasing(true);

            if (repeats==17)
                charging=true;

            if (charging)
                changeImage(repeats%2);

            if (repeats==22)
                loaded=true;

            repeats++;
        });
    }

    public void fired(){
        repeats=14;
        charging=false;
        loaded=false;
    }

    private void changeImage(int num){
        switch (num){
            case 0:
                image.getGraphics().drawImage(gameImagesArray[4][2],0,0,null);
                break;
            case 1:
                image.getGraphics().drawImage(gameImagesArray[4][3],0,0,null);
                break;
        }
    }

    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }
}
