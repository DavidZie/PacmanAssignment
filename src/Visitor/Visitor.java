package Visitor;


import GameComponents.Ghost;
import GameComponents.Pacman;

public interface Visitor {

    void visit(Pacman pacman);
    void visit(Ghost ghost);
}
