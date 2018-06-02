package Logic;

import GameComponents.Piece;
import GameComponents.Players.Ghost;
import GameComponents.Players.Pacman;

import java.util.Stack;

import static Logic.Globals.gameFrame;

public class Routing {

    private static Piece[][] pieces = gameFrame.getBoard().getPieces();
    private static Pacman pacman = gameFrame.getBoard().getPacman();

    public static Stack calculate(Ghost ghost){
        Stack<Integer> s = new Stack<>();
        int destX,destY,myX,myY;
        if (ghost.isChasing()){
            destX = pacman.getLocation()[0];
            destY = pacman.getLocation()[1];
        } else {
            destX = 2;
            destY = 6;
        }
        myX = ghost.getLocation()[0];
        myY = ghost.getLocation()[1];
        cal(destX,destY,myX,myY,s,ghost);
        Stack s1 = new Stack();
        while (!s.empty())
            s1.push(s.pop());
        return s1;
    }

    private static void cal(int destX, int destY, int myX, int myY, Stack<Integer> s, Ghost ghost) {
        if (destX == myX && destY == myY)
            return;
        if (s.size() > 50)
            return;

        boolean up = !pieces[myX-1][myY].isWall(), right = !pieces[myX][myY+1].isWall(), down = !pieces[myX+1][myY].isWall(),left = !pieces[myX][myY-1].isWall();

        if (destX == myX){
            if (destY < myY) {
                if (left && ghost.getFacing() != 2) {
                    s.push(4);
                    cal(destX, destY, myX, myY - 1, s, ghost);
                    return;
                } else if (up && ghost.getFacing() != 3) {
                    s.push(1);
                    cal(destX, destY, myX-1, myY, s, ghost);
                    return;
                } else if (down && ghost.getFacing() != 1) {
                    s.push(3);
                    cal(destX, destY, myX +1, myY, s, ghost);
                    return;
                } else if (right && ghost.getFacing() != 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s, ghost);
                    return;
                }
            }else {
                if (right && ghost.getFacing() != 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s, ghost);
                    return;
                } else if (up && ghost.getFacing() != 3) {
                    s.push(1);
                    cal(destX, destY, myX-1, myY, s, ghost);
                    return;
                } else if (down && ghost.getFacing() != 1) {
                    s.push(3);
                    cal(destX, destY, myX +1, myY, s, ghost);
                    return;
                } if (left && ghost.getFacing() != 2) {
                    s.push(4);
                    cal(destX, destY, myX, myY - 1, s, ghost);
                    return;
                }
            }
        } else if (destY == myY){
            if (destX < myX) {
                if (up && ghost.getFacing() != 3) {
                    s.push(1);
                    cal(destX, destY, myX-1, myY, s, ghost);
                    return;
                } else if (left && ghost.getFacing() != 2) {
                    s.push(4);
                    cal(destX, destY, myX, myY - 1, s, ghost);
                    return;
                } else if (right && ghost.getFacing()!= 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s,ghost);
                    return;
                } else if (down && ghost.getFacing() != 1) {
                    s.push(3);
                    cal(destX, destY, myX+1, myY, s, ghost);
                    return;
                }
            }else {
                if (down && ghost.getFacing() != 1) {
                    s.push(3);
                    cal(destX, destY, myX+1, myY, s, ghost);
                    return;
                } else if (left && ghost.getFacing() != 2) {
                    s.push(4);
                    cal(destX, destY, myX, myY - 1, s, ghost);
                    return;
                } else if (right && ghost.getFacing()!= 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s,ghost);
                    return;
                } else if (up && ghost.getFacing() != 3) {
                    s.push(1);
                    cal(destX, destY, myX-1, myY, s, ghost);
                    return;
                }
            }
        }

        if (destX < myX) {
            if (up && ghost.getFacing()!=3) {
                s.push(1);
                cal(destX, destY, myX - 1, myY, s,ghost);
                return;
            } else if (destY <= myY) {
                if (left && ghost.getFacing()!=2) {
                    s.push(4);
                    cal(destX, destY, myX, myY - 1, s,ghost);
                    return;
                } else if (down && ghost.getFacing()!= 1) {
                    s.push(3);
                    cal(destX, destY, myX+1, myY, s,ghost);
                    return;
                } else if (right && ghost.getFacing()!= 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s,ghost);
                    return;
                }
            } else if (destY > myY) {
                if (right && ghost.getFacing()!= 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s,ghost);
                    return;
                } else if (down && ghost.getFacing()!= 1) {
                    s.push(3);
                    cal(destX, destY, myX+1, myY, s,ghost);
                    return;
                }
            }
        } else {
            if (down && ghost.getFacing()!=1) {
                s.push(3);
                cal(destX, destY, myX + 1, myY, s,ghost);
                return;
            } else if (destY <= myY) {
                if (left && ghost.getFacing()!=2) {
                    s.push(4);
                    cal(destX, destY, myX, myY - 1, s,ghost);
                    return;
                } else if (down && ghost.getFacing()!=1) {
                    s.push(3);
                    cal(destX, destY, myX+1, myY, s,ghost);
                    return;
                }
            } else if (destY > myY) {
                if (right && ghost.getFacing()!= 4) {
                    s.push(2);
                    cal(destX, destY, myX, myY + 1, s,ghost);
                    return;
                } else if (down && ghost.getFacing()!= 1) {
                    s.push(3);
                    cal(destX, destY, myX+1, myY, s,ghost);
                    return;
                }
            } else if (up && ghost.getFacing()!= 3) {
                s.push(1);
                cal(destX, destY, myX - 1, myY, s,ghost);
                return;
            }
        }
    }

}
