package GameComponents;

import Logic.Movement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.*;

public class Board extends JPanel {
    private Piece[][] pieces;
    private Player player;
    private Timer timer;
    private int lastMoveNumber;
    private int timerRepeats=0;
    private boolean pauseStatus;
    private boolean speedActivated;
    private int speedDivisor;
    private int pills=-1;


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

    public boolean isPauseStatus() {
        return pauseStatus;
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
            if (timer.getDelay()==250)
                speedDivisor = 4;
            else speedDivisor = 8;
            if (timerRepeats%4==0)
                drawTime(timerRepeats/ speedDivisor);

        });
        timer.start();
    }

    private void drawLife(){
        Graphics g;
        for (int i=7;i<10;i++){
            g = pieces[1][i].getImage().getGraphics();
            g.setColor(Color.YELLOW);
            g.fillOval(0,0,22,22);
        }
        g = pieces[1][10].getImage().getGraphics();
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
        drawScoreLabel();
        drawHighScoreLabel();
        drawPauseButton();
        drawSpeedLabel();

    }

    private void drawTimeLabel(){
        Piece timePiece = replaceLabels(1,(boardSize/2)-1,2,1);

        Graphics g = timePiece.getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,timePiece.getWidth(),timePiece.getHeight());

        Stack data = new Stack();
        data.push(3);
        timePiece.drawData(data);

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
            for (int j=0;j<height;j++){
                remove(pieces[x+j][y+i]);
                pieces[x+j][y+i] = newPiece;
            }
        }
        constraints.gridwidth = width;
        constraints.gridheight = height;
        newPiece.setSize(width*pieceSize,height*pieceSize);
        newPiece.setImage(new BufferedImage(width*pieceSize,height*pieceSize,BufferedImage.TYPE_INT_ARGB));
        add(newPiece,constraints);
        return newPiece;
    }

    private void drawScoreLabel(){
        Piece scorePiece = replaceLabels(22,7,3,2);
        Stack data = new Stack();
        data.push(1);
        scorePiece.drawData(data);
        Graphics g = scorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("SCORE:",0,20);
        g.drawString("0000000",0,40);
    }
    private void drawHighScoreLabel(){
        Piece highScorePiece = replaceLabels(22,22,5,2);
        Stack data = new Stack();
        data.push(1);
        highScorePiece.drawData(data);
        Graphics g = highScorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("HIGH SCORE:",0,20);
        g.drawString("     0000000",0,40);
    }
    private void drawPauseButton(){
        Piece pausePiece = replaceLabels(1,23,3,1);
        Stack data = new Stack();
        data.push(3);
        pausePiece.drawData(data);
        reDrawPausePiece(pausePiece);

        pausePiece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pauseStatus=!pauseStatus;
                if (pauseStatus)
                    timer.stop();
                else timer.start();
                reDrawPausePiece(pausePiece);
            }
        });
    }
    private void drawSpeedLabel(){
        Piece speedPiece = replaceLabels(22,15,3,2);
        Stack data = new Stack();
        data.push(1);
        speedPiece.drawData(data);
        reDrawSpeedPiece(speedPiece);
        speedPiece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                speedActivated=!speedActivated;
                if (speedActivated)
                    timer.setDelay(timer.getDelay()/2);
                else timer.setDelay(timer.getDelay()*2);
                reDrawSpeedPiece(speedPiece);
            }
        });
    }

    private void reDrawPausePiece(Piece pausePiece){
        Graphics g = pausePiece.getImage().getGraphics();
        if (pauseStatus){
            g.setColor(Color.RED);
            g.fillRect(0,0,pausePiece.getWidth(),pausePiece.getHeight()-4);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString(" PAUSE",3,18);
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,pausePiece.getWidth(),pausePiece.getHeight()-2);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString(" PAUSE",3,18);
        }
        g.drawRect(0,0,pausePiece.getWidth()-1,pausePiece.getHeight()-5);
        pausePiece.repaint();
    }

    private void reDrawSpeedPiece(Piece speedPiece){
        Graphics g = speedPiece.getImage().getGraphics();
        if (speedActivated){
            g.setColor(Color.RED);
            g.fillRect(1,4,speedPiece.getWidth()-4,speedPiece.getHeight()-8);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString(" SPEED",0,22);
            g.drawString("     X2",0,42);
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0,3,speedPiece.getWidth(),speedPiece.getHeight()-2);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));

            g.drawString(" SPEED",0,22);
            g.drawString("     X2",0,42);
        }
        g.drawRect(0,4,speedPiece.getWidth()-4,speedPiece.getHeight()-8);
        speedPiece.repaint();
    }


}
