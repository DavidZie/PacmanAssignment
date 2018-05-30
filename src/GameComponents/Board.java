package GameComponents;

import Logic.Drawings;
import Logic.Movement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Drawings.*;
import static Logic.Globals.*;

public class Board extends JPanel {
    private int[] playerLocation;
    private Piece[][] pieces;
    private Player player;
    private Timer timer;

    private int lastMoveNumber;
    private int timerRepeats = 0;
    private boolean pauseStatus;
    private boolean speedActivated;
    private int speedDivisor;
    private int currentScore;
    private int currentHighScore;
    private int pills = -1;
    private Fruit[] fruits;
    private int level;


    Board(Stack[][] board,int level, int currentHighScore) {
        super(new GridBagLayout());
        this.level=level;
        this.currentHighScore=currentHighScore;
        createBoard(board);
        drawInfo();
        levelSetup();
        timerSetup();
    }//Constructor
    //----------------Board Initiation----------------------//
    private void createBoard(Stack[][] board) {
        setBorder(new LineBorder(Color.GREEN));
        pieces = new Piece[boardSize][boardSize];
        GridBagConstraints constraints = new GridBagConstraints();
        for (int i = 0; i < boardSize; i++) {
            constraints.gridy = i;
            for (int j = 0; j < boardSize; j++) {
                constraints.gridx = j;
                if ((int) board[i][j].peek() == 0)
                    pills++;
                if ((int) board[i][j].peek() == 7 && playerLocation == null) {
                    playerLocation = new int[2];
                    playerLocation[0] = i - 1;
                    playerLocation[1] = j;
                }
                pieces[i][j] = new Piece(i, j, gameBoards.getFirst()[i][j]);
                add(pieces[i][j], constraints);
            }
        }
    }
    //------------------------Board Initiation END--------------------------------------//

    //-----------------------Getters and Setters----------------//

    public boolean isPauseStatus() {
        return pauseStatus;
    }

    public int[] getPlayerLocation() {
        return playerLocation;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setPauseStatus(boolean pauseStatus) {
        this.pauseStatus = pauseStatus;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isSpeedActivated() {
        return speedActivated;
    }

    public void setSpeedActivated(boolean speedActivated) {
        this.speedActivated = speedActivated;
    }

    public int getTimerRepeats() {
        return timerRepeats;
    }

    public void setTimerRepeats(int timerRepeats) {
        this.timerRepeats = timerRepeats;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getCurrentHighScore() {
        return currentHighScore;
    }

    //--------------------------Methods--------------------------//

    public Piece replaceLabels(int x, int y, int width, int height) {
        Piece newPiece = new Piece(x, y, null);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = y;
        constraints.gridy = x;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                remove(pieces[x + j][y + i]);
                pieces[x + j][y + i] = newPiece;
            }
        }
        constraints.gridwidth = width;
        constraints.gridheight = height;
        newPiece.setSize(width * pieceSize, height * pieceSize);
        newPiece.setImage(new BufferedImage(width * pieceSize, height * pieceSize, BufferedImage.TYPE_INT_ARGB));
        add(newPiece, constraints);
        return newPiece;
    }

    private void timerSetup() {
        timer = new Timer(250, e -> {
            Movement.move(lastMoveNumber,this);
            timerRepeats++;
            if (timer.getDelay() == 250)
                speedDivisor = 4;
            else speedDivisor = 8;
            if (timerRepeats % speedDivisor == 0) {
                Drawings.drawTime(timerRepeats / speedDivisor,pieces);
                if ((timerRepeats / speedDivisor)%10==0) {
                    insertFruits();
                }
            }

        });
        timer.start();
    }

    public void drawBlack(Piece piece) {
        BufferedImage blackImage = new BufferedImage(piece.getWidth(), piece.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = blackImage.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, piece.getWidth(), piece.getHeight());
        piece.setImage(blackImage);
    }

    public void updateScore(Piece piece){
        if (!piece.isEaten()){
            currentScore+=piece.getWorth();
            piece.setEaten(true);
            Drawings.reDrawScoreLabel(pieces[22][7],currentScore,currentHighScore,pieces);
        }
    }

    //-----------------------First Draw Methods------------------//

    private void drawInfo() {
        swapIn();
        Drawings.drawGate(pieces[playerLocation[0] + 1][playerLocation[1]]);
        drawLife(pieces);
        drawTimeLabel(this);
        drawScoreLabel(this);
        drawHighScoreLabel(this);
        drawPauseButton(this);
        drawSpeedLabel(this);
        drawTime(0,pieces);
    }

    private void swapIn() {
        player = new Player();
        pieces[playerLocation[0]][playerLocation[1]].setImage(player.getImage());
    }



    //---------------------First Draw Methods END-----------------------//
    //--------------------- re-draw Methods-----------------------//

    //--------------------- re-draw Methods END-----------------------//
    //------------------------Level Initiation--------------------------------//

    private void levelSetup(){
        prepareFruits();
        //prepareGhosts();//TO BE WRITTEN.
    }


    //-----------------------Level Initiation END---------------------------//
    //------------------------Fruits-----------------------------//

    private void insertFruits(){
        Piece piece;
        Fruit fruit;
        int x,y;
        int index;
        int num=1;
        while (num!=0){
            x = (int)(Math.random() * 19 + 2);
            y = (int)(Math.random() * 22 + 6);
            piece = pieces[x][y];
            if (!piece.isWall()&&!(x==playerLocation[0]&&y==playerLocation[1])){
                index = (int)(Math.random()*fruits.length);
                if (fruits[index].isOut()){
                    index = 0;
                    while (index<fruits.length&&fruits[index].isOut()){
                        index++;
                    }
                }
                if (index==fruits.length)
                    return;

                fruit = fruits[index];
                piece.addFruit(fruit);
                piece.setEaten(false);
                fruit.setOut(true);
                piece.repaint();
                num--;
            }
        }


    }

    private void prepareFruits(){
        switch (level){
            case 1:
                fruits = new Fruit[4];
                fruits[0] = new Fruit(0);
                fruits[1] = new Fruit(1);
                fruits[2] = new Fruit(0);
                fruits[3] = new Fruit(1);
                break;
            case 2:
                fruits = new Fruit[9];
                fruits[0] = new Fruit(0);
                fruits[1] = new Fruit(1);
                fruits[2] = new Fruit(0);
                fruits[3] = new Fruit(1);
                fruits[4] = new Fruit(0);
                fruits[5] = new Fruit(1);
                fruits[6] = new Fruit(0);
                fruits[7] = new Fruit(1);
                fruits[8] = new Fruit(2);
                break;
            case 3:
                fruits = new Fruit[12];
                fruits[0] = new Fruit(0);
                fruits[1] = new Fruit(1);
                fruits[2] = new Fruit(0);
                fruits[3] = new Fruit(1);
                fruits[4] = new Fruit(0);
                fruits[5] = new Fruit(1);
                fruits[6] = new Fruit(0);
                fruits[7] = new Fruit(1);
                fruits[8] = new Fruit(0);
                fruits[9] = new Fruit(1);
                fruits[10] = new Fruit(2);
                fruits[11] = new Fruit(2);
                break;
        }
    }



    //-----------------------------Movement----------------------//
    public void changeDirection(int newDirection) {
        if (newDirection == lastMoveNumber)
            return;
        switch (newDirection) {
            case 1:
                if (!pieces[playerLocation[0] - 1][playerLocation[1]].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 2:
                if (!pieces[playerLocation[0]][playerLocation[1] + 1].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 3:
                if (!pieces[playerLocation[0] + 1][playerLocation[1]].isWall())
                    lastMoveNumber = newDirection;
                break;
            case 4:
                if (!pieces[playerLocation[0]][playerLocation[1] - 1].isWall())
                    lastMoveNumber = newDirection;
                break;
        }
    }

}
