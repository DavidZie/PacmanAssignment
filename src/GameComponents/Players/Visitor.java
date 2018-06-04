package GameComponents.Players;


import GameComponents.Board;

public interface Visitor {

    void visit(Pacman pacman, Board board);
    void visit(Ghost ghost, Board board);
}
