package GameComponents;

import Logic.Movement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.*;

public class Board extends JPanel {
    Piece[][] pieces;
    Player player;
    Timer timer;
    int lastMoveNumber;
    int timerRepeats=0;

    Board(){
        /**
         * initiate GameComponents.Board..,
         */
        super();
        createBoard();

        for (int i=0; i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                pieces[i][j] = new Piece(i,j,gameBoards.getFirst()[i][j]);
                add(pieces[i][j]);
            }
        }
        drawGate(pieces[playerLocation[0]+1][playerLocation[1]]);
        swapIn();
        drawTime(0);
        timerSetup();
        drawLife();

    }//Constructor

    private void createBoard(){
        setLayout(new GridLayout(boardSize,boardSize,0,0));
        setBorder(new LineBorder(Color.GREEN));
        pieces = new Piece[boardSize][boardSize];
    }

    private void swapIn(){
        player = new Player();
        pieces[playerLocation[0]][playerLocation[1]].setImage(player.getImage());
    }


    //-----------------------Getters and Setters----------------//


    public Piece[][] getPieces() {
        return pieces;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLastMoveNumber() {
        return lastMoveNumber;
    }

    public void setLastMoveNumber(int lastMoveNumber) {
        this.lastMoveNumber = lastMoveNumber;
    }

    //--------------------------Methods--------------------------//

    private void drawGate(Piece piece){
        BufferedImage image = new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(gameImagesArray[0],0,0,null);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, pieceSize, 2);
        piece.setWall(true);
        piece.setImage(image);
    }

    private void timerSetup(){

        timer = new Timer(250, e -> {
            Movement.move(lastMoveNumber, pieces, player);
            timerRepeats++;
            if (timerRepeats%4==0)
                drawTime(timerRepeats/4);

        });
        timer.start();
    }

    private void drawLife(){
        Graphics g;
        for (int i=2;i<5;i++){
            g = pieces[0][i].getImage().getGraphics();
            g.setColor(Color.YELLOW);
            g.fillOval(0,0,24,24);
        }
        g = pieces[0][5].getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("X3",0,20);
    }

    private void drawTime(int seconds){
        String secs,mins;
        if (seconds/60<10)
            mins="0"+seconds/60;
        else mins= String.valueOf(seconds/60);
        if (seconds%60<10)
            secs="0"+seconds%60;
        else secs= String.valueOf(seconds%60);
        Graphics g =pieces[0][(boardSize/2)-1].getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString(mins+":",0,20);

        g =pieces[0][(boardSize/2)].getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString(secs,0,20);

    }



    public void changeDirection(int newDirection){
        if (newDirection == lastMoveNumber)
            return;
        switch (newDirection){
            case 1:
                if (!pieces[playerLocation[0]-1][playerLocation[1]].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 2:
                if (!pieces[playerLocation[0]][playerLocation[1]+1].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 3:
                if (!pieces[playerLocation[0]+1][playerLocation[1]].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 4:
                if (!pieces[playerLocation[0]][playerLocation[1]-1].isWall())
                    lastMoveNumber = newDirection;
                break;
        }
    }

}
