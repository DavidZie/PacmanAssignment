package GameComponents.Players;


import GameComponents.Board;

public interface Visitor {

    void visit(Pacman pacman, Board board);
    void visit(Ghost ghost, Board board);
    void visit(Ginky ginky, Board board);
    void visit(Inky inky, Board board);
    void visit(Blinky blinky, Board board);
    void visit(Water water, Board board);
    void visit(Fireball fireball, Board board);
    void visit(ExtraGhost extraGhost, Board board);
}
