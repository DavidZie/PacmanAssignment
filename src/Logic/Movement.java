package Logic;

import GameComponents.*;
import GameComponents.Players.Pacman;

public class Movement {

    public static void move(int direction, Board board) {
        int[] pacmanLocation = board.getPacman().getLocation();
        Piece[][] pieces = board.getPieces();
        Pacman pacman = board.getPacman();
        int x = pacmanLocation[0], y = pacmanLocation[1];
        switch (direction) {
            case 1://Move Up.
                if (pieces[x - 1][y].isWall())
                    break;
                pacman.rotate(1);
                pieces[x - 1][y].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x-1][y]);
                pacmanLocation[0] -= 1;
                break;
            case 2://Move Right.
                if (pieces[x][y + 1].isWall())
                    break;
                pacman.rotate(2);
                pieces[x][y + 1].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x][y+1]);
                pacmanLocation[1] += 1;
                break;
            case 3://Move Down.
                if (pieces[x + 1][y].isWall())
                    break;
                pacman.rotate(3);
                pieces[x + 1][y].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x+1][y]);
                pacmanLocation[0] += 1;
                break;
            case 4://Move Left.
                if (pieces[x][y - 1].isWall())
                    break;
                pacman.rotate(4);
                pieces[x][y - 1].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x][y-1]);
                pacmanLocation[1] -= 1;
                break;
        }
    }

}
