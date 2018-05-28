package GameComponents;

import Logic.Movement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.*;

public class Board extends JPanel {
    private Piece[][] pieces;
    private Player player;
    private Timer timer;
    private int lastMoveNumber;
    private int timerRepeats=0;
    private int pills=0;


    Board(){
        super(new GridBagLayout());
        createBoard();

        drawGate(pieces[playerLocation[0]+1][playerLocation[1]]);
        swapIn();
        drawInfo();
        drawTime(0);
        timerSetup();

    }//Constructor

    private void createBoard(){
        setBorder(new LineBorder(Color.GREEN));
        pieces = new Piece[boardSize][boardSize];
        GridBagConstraints constraints = new GridBagConstraints();
        for (int i=0; i<boardSize;i++){
            constraints.gridy=i;
            for (int j=0;j<boardSize;j++){
                constraints.gridx=j;
                if ((int)gameBoards.getFirst()[i][j].peek()==0)
                    pills++;
                pieces[i][j] = new Piece(i,j,gameBoards.getFirst()[i][j]);
                add(pieces[i][j],constraints);
            }
        }
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
            g = pieces[1][i].getImage().getGraphics();
            g.setColor(Color.YELLOW);
            g.fillOval(0,0,22,22);
        }
        g = pieces[1][5].getImage().getGraphics();
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
        if (mins.equals("99")&secs.equals("99"))
            return;
        Piece timePiece = pieces[1][(boardSize/2)-1];
        Graphics g = timePiece.getImage().getGraphics();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.setColor(Color.BLACK);
        g.fillRect(0,0,timePiece.getWidth(),timePiece.getHeight()-2);
        g.setColor(Color.WHITE);
        g.drawString(mins+":"+secs,0,20);
        repaint();
    }

    private void drawInfo(){
        drawLife();
        drawTimeLabel();
    }

    private void drawTimeLabel(){
        Piece timePiece = replaceLabels(1,(boardSize/2)-1,2,1);

        Graphics g = timePiece.getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,timePiece.getWidth(),timePiece.getHeight());

        Stack walls = new Stack();
        walls.push(3);
        timePiece.addWalls(walls);

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

    private Piece replaceLabels(int x, int y, int width, int height){
        Piece newPiece = new Piece(x,y,null);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = y;
        constraints.gridy = x;
        for (int i=0; i<width;i++){
            remove(pieces[x][y+i]);
            pieces[x][y+i] = newPiece;
        }
        constraints.gridwidth = width;
        constraints.gridheight = height;
        newPiece.setSize(width*pieceSize,height*pieceSize);
        newPiece.setImage(new BufferedImage(width*pieceSize,height*pieceSize,BufferedImage.TYPE_INT_ARGB));
        add(newPiece,constraints);
        return newPiece;

    }

}
