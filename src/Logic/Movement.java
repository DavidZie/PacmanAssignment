package Logic;

import GameComponents.*;
import GameComponents.Players.Ghost;
import GameComponents.Players.Pacman;

import java.awt.image.BufferedImage;

import static Logic.Globals.gameFrame;
import static Logic.Globals.pieceSize;

public class Movement {

    private static Board board = gameFrame.getBoard();

    public static void movePacman(int direction) {
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
    }

    public static void moveGhost(int direction, Ghost ghost) {
        ghost.setFacing(direction);
        BufferedImage temp;
        Piece[][] pieces = board.getPieces();
        int x = ghost.getLocation()[0], y = ghost.getLocation()[1];
        switch (direction) {
            case 1://Move Up.
                if (pieces[x-1][y].isWall()){
                    if (!weaponCheck(x-1,y,ghost));
                    {
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x-1,y);
                pieces[x - 1][y].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[0] -= 1;
                break;
            case 2://Move Right.
                if (pieces[x][y+1].isWall()){
                    if (!weaponCheck(x,y+1,ghost));
                    {
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x,y+1);
                pieces[x][y+1].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[1] += 1;
                break;
            case 3://Move Down.
                if (pieces[x+1][y].isWall()){
                    if (!weaponCheck(x+1,y,ghost));
                    {
                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x+1,y);
                pieces[x+1][y].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[0] += 1;
                break;
            case 4://Move Left.
                if (pieces[x][y-1].isWall()){
                    if (!weaponCheck(x,y-1,ghost));
                    {

                        pieces[x][y].setImage(ghost.getCoveredImage());
                        ghost.getTimer().stop();
                        if (board.getGhosts()[3]==ghost)
                            board.getGhosts()[3]=null;//Kill Water.
                        else board.getGhosts()[4]=null;//Kill Fire.
                        return;
                    }
                }
                temp = tempCreator(x,y-1);
                pieces[x][y-1].setImage(ghost.getImage());
                pieces[x][y].setImage(ghost.getCoveredImage());
                ghost.setCoveredImage(temp);
                ghost.getLocation()[1] -= 1;
                break;
        }
        board.repaint();
    }


    private static BufferedImage tempCreator(int x, int y){
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

    private static boolean weaponCheck(int x, int y, Ghost ghost){
        if (x==1|y==5|x==21|y==27){
            return false;
        }
        if (board.getGhosts()[4]!=null){
            if (board.getGhosts()[4]==ghost)
                return true;
        }
        return false;
    }


}
