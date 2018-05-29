package Logic;

import GameComponents.Piece;
import GameComponents.Player;

import static Logic.Globals.*;

public class Movement {


    public static void move(int direction, Piece[][] pieces, Player player){
        int x = playerLocation[0],y=playerLocation[1];
        switch (direction){
            case 1://Move Up.
                if(pieces[x-1][y].isWall())
                    break;
                pieces[x-1][y].setImage(player.getImage());
                pieces[x][y].setImage(gameImagesArray[0]);
                playerLocation[0]-=1;
                break;
            case 2://Move Right.
                if(pieces[x][y+1].isWall())
                    break;
                pieces[x][y+1].setImage(player.getImage());
                pieces[x][y].setImage(gameImagesArray[0]);
                playerLocation[1]+=1;
                break;
            case 3://Move Down.
                if(pieces[x+1][y].isWall())
                    break;
                pieces[x+1][y].setImage(player.getImage());
                pieces[x][y].setImage(gameImagesArray[0]);
                playerLocation[0]+=1;
                break;
            case 4://Move Left.
                if(pieces[x][y-1].isWall())
                    break;
                pieces[x][y-1].setImage(player.getImage());
                pieces[x][y].setImage(gameImagesArray[0]);
                playerLocation[1]-=1;
                break;
        }

    }

}
