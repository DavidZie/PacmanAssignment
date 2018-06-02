package GameComponents.Players;


import GameComponents.Players.Ghost;
import GameComponents.Players.Pacman;

public interface Visitor {

    void visit(Pacman pacman);
    void visit(Ghost ghost);
}
