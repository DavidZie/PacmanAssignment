package GameComponents.Players;

import Logic.AStar;
import Logic.Movement;

import javax.swing.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameFrame;

public class ExtraGhost extends Ghost implements Visited {
    public ExtraGhost(int id, BufferedImage coveredImage) {
        super(id+1,true);
        setCoveredImage(coveredImage);
        setupTimer();
    }


    private void setupTimer(){
        repeats = 0;
        setChasing(true);
        timer = new Timer(333, e -> {

           setRoute(AStar.search(this,gameFrame.getBoard()));
           if (!getRoute().empty())
               Movement.moveGhost((int)getRoute().pop(),this,gameFrame.getBoard());
           repeats++;
        });
        timer.start();
    }

    @Override
    public void impact(Visitor visitor) {
        visitor.visit(this, gameFrame.getBoard());
    }
}
