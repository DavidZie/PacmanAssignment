package GameComponents.Players;

import Logic.AStar;
import Logic.Movement;

import javax.swing.*;

import static Logic.Globals.*;

public class Blinky extends Ghost implements Visited  {

    private boolean charging;
    private int level;

    public Blinky(int level) {
        super(2,true);
        weapon = new Fireball(2);
        this.level=level;
        setupTimer();
    }

    private void setupTimer(){
        repeats = 0;//Use Repeats to measure passing time and make the Object act Accordingly.
        timer = new Timer(1000, e -> {
            if (repeats<=13){
                repeats++;
                return;
            }//Leave Base after 13 Seconds.
            if (!charging)//Redraw Regular Image to avoid getting overwritten by other Players.
                image.getGraphics().drawImage(gameImagesArray[4][2], 0, 0, pieceSize, pieceSize, null);
            setRoute(AStar.search(this,gameFrame.getBoard()));//Use A* Search Algorithm to find shortest path to destination.
            if (!getRoute().empty())
                Movement.moveGhost((int)getRoute().pop(), this,gameFrame.getBoard());//Move only if there's a possible move.
            if (repeats==15)
                setChasing(true);//Start Chasing after 15 seconds.

            if (repeats==17&level>2)
                charging=true;//Start Charging Fireball after 17 Seconds.

            if (charging)
                changeImage(repeats%2);//While Charging, change pictures every tick.

            if (repeats==22&&charging)
                loaded=true;//5 seconds after started charging, be Ready to Fire.

            repeats++;//increment to count ticks.
        });
    }//Setup timer as part of construction.

    public void fired(){
        repeats=14;
        charging=false;
        loaded=false;
    }//Reset Fields after Weapon Fired.

    private void changeImage(int num){
        switch (num){
            case 0:
                image.getGraphics().drawImage(gameImagesArray[4][2],0,0,null);
                break;
            case 1:
                image.getGraphics().drawImage(gameImagesArray[4][3],0,0,null);
                break;
        }
    }//Change Image according to input.

    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }//Visit Pacman on Impact.
}
