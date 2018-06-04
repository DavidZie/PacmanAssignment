package Logic;

import GameComponents.Board;
import GameComponents.Piece;
import GameComponents.Players.Ghost;
import GameComponents.Players.Pacman;

import java.awt.image.BufferedImage;

import static Logic.Globals.gameFrame;
import static Logic.Globals.pieceSize;

public class Movement {



    public static void movePacman(int direction, Board board) {
        if (board.getPacman().isFrozen())
            return;
        int[] pacmanLocation = board.getPacman().getLocation();
        Piece[][] pieces = board.getPieces();
        Pacman pacman = board.getPacman();
        int x = pacmanLocation[0], y = pacmanLocation[1];
        switch (direction) {
            case 1://Move Up.
                if (pieces[x - 1][y].isWall()||pieces[x - 1][y].isGhostHouse())
                    break;
                pacman.rotate(1);
                pieces[x - 1][y].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x-1][y]);
                pacmanLocation[0] -= 1;
                break;
            case 2://Move Right.
                if (pieces[x][y + 1].isWall()||pieces[x][y + 1].isGhostHouse())
                    break;
                pacman.rotate(2);
                pieces[x][y + 1].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x][y+1]);
                pacmanLocation[1] += 1;
                break;
            case 3://Move Down.
                if (pieces[x + 1][y].isWall()||pieces[x + 1][y].isGhostHouse())
                    break;
                pacman.rotate(3);
                pieces[x + 1][y].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x+1][y]);
                pacmanLocation[0] += 1;
                break;
            case 4://Move Left.
                if (pieces[x][y - 1].isWall()||pieces[x][y - 1].isGhostHouse())
                    break;
                pacman.rotate(4);
                pieces[x][y - 1].setImage(pacman.getImage());
                board.drawBlack(pieces[x][y]);
                board.updateScore(pieces[x][y-1]);
                pacmanLocation[1] -= 1;
                break;
        }
        gameFrame.repaint();
    }

    public static void moveGhost(int direction, Ghost ghost, Board board) {
        ghost.setFacing(direction);
        BufferedImage temp;
        Piece[][] pieces = board.getPieces();
        int x = ghost.getLocation()[0], y = ghost.getLocation()[1];
        switch (direction) {
            case 1://Move Up.
                if (pieces[x-1][y].isWall()){
                    if (killWeapon(x-1,y,ghost,board)) {
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x-1,y,board);
                pieces[x - 1][y].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[0] -= 1;
                break;
            case 2://Move Right.
                if (pieces[x][y+1].isWall()){
                    if (killWeapon(x,y+1,ghost,board)) {
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x,y+1,board);
                pieces[x][y+1].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[1] += 1;
                break;
            case 3://Move Down.
                if (pieces[x+1][y].isWall()){
                    if (killWeapon(x+1,y,ghost,board)) {
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x+1,y,board);
                pieces[x+1][y].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[0] += 1;
                break;
            case 4://Move Left.
                if (pieces[x][y-1].isWall()){
                    if (killWeapon(x,y-1,ghost,board)){
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)//
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x,y-1,board);
                pieces[x][y-1].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[1] -= 1;
                break;
        }
        gameFrame.repaint();
    }


    private static BufferedImage tempCreator(int x, int y, Board board){
        BufferedImage temp = new BufferedImage(pieceSize,pieceSize,BufferedImage.TYPE_INT_ARGB);
        Ghost[] ghosts= board.getGhosts();
        for (int i=0;i<5;i++){
            if (ghosts[i]!=null) {
                if (ghosts[i].getLocation()[0] == x && ghosts[i].getLocation()[1] == y) {
                    return ghosts[i].getCoveredImage();
                }
            }
        }
        temp.getGraphics().drawImage(board.getPieces()[x][y].getImage(),0,0,null);
        return temp;
    }

    private static boolean killWeapon(int x, int y, Ghost ghost, Board board) {
        return x == 1 | y == 5 | x == 22 | y == 27 || board.getGhosts()[4] == null || board.getGhosts()[4] != ghost;
    }


}
