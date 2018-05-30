package Logic;

import GameComponents.Board;
import GameComponents.Piece;
import GameComponents.Player;

public class Movement {

    public static void move(int direction, Board board) {
        int[] playerLocation = board.getPlayerLocation();
        Piece[][] pieces = board.getPieces();
        Player player = board.getPlayer();
        int x = playerLocation[0], y = playerLocation[1];
        switch (direction) {
            case 1://Move Up.
                if (pieces[x - 1][y].isWall())
                    break;
                player.rotate(1);
                pieces[x - 1][y].setImage(player.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x-1][y]);
                playerLocation[0] -= 1;
                break;
            case 2://Move Right.
                if (pieces[x][y + 1].isWall())
                    break;
                player.rotate(2);
                pieces[x][y + 1].setImage(player.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x][y+1]);
                playerLocation[1] += 1;
                break;
            case 3://Move Down.
                if (pieces[x + 1][y].isWall())
                    break;
                player.rotate(3);
                pieces[x + 1][y].setImage(player.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x+1][y]);
                playerLocation[0] += 1;
                break;
            case 4://Move Left.
                if (pieces[x][y - 1].isWall())
                    break;
                player.rotate(4);
                pieces[x][y - 1].setImage(player.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x][y-1]);
                playerLocation[1] -= 1;
                break;
        }
    }

}
