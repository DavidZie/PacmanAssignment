package Visitor;


import GameComponents.Ghost;
import GameComponents.Pacman;

public interface Visitor {

    public void visit(Pacman pacman);
    public void visit(Ghost ghost);
}
