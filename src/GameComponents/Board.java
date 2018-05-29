package GameComponents;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Globals.*;

public class Board extends JPanel {
    private boolean completed;
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
    private Stack<Fruit> fruits;


    Board(Stack[][] board,int level) {
        super(new GridBagLayout());
        createBoard(board);
        drawInfo();
        levelSetup(level);
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


    public boolean isCompleted() {
        return completed;
    }

    public boolean isPauseStatus() {
        return pauseStatus;
    }
    //--------------------------Methods--------------------------//

    private Piece replaceLabels(int x, int y, int width, int height) {
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
            move(lastMoveNumber, pieces, player);
            timerRepeats++;
            if (timer.getDelay() == 250)
                speedDivisor = 4;
            else speedDivisor = 8;
            if (timerRepeats % speedDivisor == 0) {
                drawTime(timerRepeats / speedDivisor);
                if (timerRepeats/speedDivisor==10)
                    insertFruits();
            }

        });
        timer.start();
    }
    private void levelSetup(int level){
        fruits = new Stack<>();

    }

    private void drawBlack(Piece piece) {
        BufferedImage blackImage = new BufferedImage(piece.getWidth(), piece.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = blackImage.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, piece.getWidth(), piece.getHeight());
        piece.setImage(blackImage);
    }

    private void updateScore(Piece piece){
        if (!piece.isEaten()){
            piece.setEaten(true);
            currentScore+=piece.getWorth();
            reDrawScoreLabel(pieces[22][7]);
        }
    }

    //-----------------------First Draw Methods------------------//

    private void drawInfo() {
        swapIn();
        drawGate(pieces[playerLocation[0] + 1][playerLocation[1]]);
        drawLife();
        drawTimeLabel();
        drawScoreLabel();
        drawHighScoreLabel();
        drawPauseButton();
        drawSpeedLabel();
        drawTime(0);
    }

    private void swapIn() {
        player = new Player();
        pieces[playerLocation[0]][playerLocation[1]].setImage(player.getImage());
    }

    private void drawGate(Piece piece) {
        Graphics g = piece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, piece.getWidth(), 2);
    }

    private void drawLife() {
        Graphics g;
        for (int i = 7; i < 10; i++) {
            g = pieces[1][i].getImage().getGraphics();
            g.setColor(Color.YELLOW);
            g.fillOval(0, 0, 22, 22);
        }
        g = pieces[1][10].getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("X3", 0, 20);
    }

    private void drawTimeLabel() {
        Piece timePiece = replaceLabels(1, (boardSize / 2) - 1, 2, 1);
        Graphics g = timePiece.getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, timePiece.getWidth(), timePiece.getHeight());
        Stack data = new Stack();
        data.push(3);
        timePiece.drawData(data);
    }

    private void drawScoreLabel() {
        Piece scorePiece = replaceLabels(22, 7, 3, 2);
        Stack data = new Stack();
        data.push(1);
        scorePiece.drawData(data);
        currentScore=0;
        Graphics g = scorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("SCORE:", 0, scorePiece.getHeight()/2);
        reDrawScoreLabel(scorePiece);
    }

    private void drawHighScoreLabel() {
        Piece highScorePiece = replaceLabels(22, 22, 5, 2);
        Stack data = new Stack();
        data.push(1);
        highScorePiece.drawData(data);
        Graphics g = highScorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("HIGH SCORE:", 0, highScorePiece.getHeight()/2);
        reDrawHighScoreLabel(highScorePiece,String.valueOf(highScoresArray[0][0]));
    }

    private void drawPauseButton() {
        Piece pausePiece = replaceLabels(1, 23, 3, 1);
        Stack data = new Stack();
        data.push(3);
        pausePiece.drawData(data);
        reDrawPausePiece(pausePiece);

        pausePiece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pauseStatus = !pauseStatus;
                if (pauseStatus)
                    timer.stop();
                else timer.start();
                reDrawPausePiece(pausePiece);
            }
        });
    }

    private void drawSpeedLabel() {
        Piece speedPiece = replaceLabels(22, 15, 3, 2);
        Stack data = new Stack();
        data.push(1);
        speedPiece.drawData(data);
        reDrawSpeedPiece(speedPiece);
        speedPiece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                speedActivated = !speedActivated;
                if (speedActivated) {
                    timer.setDelay(timer.getDelay() / 2);
                    timerRepeats = timerRepeats * 2;
                } else {
                    timer.setDelay(timer.getDelay() * 2);
                    timerRepeats = timerRepeats / 2;
                }
                reDrawSpeedPiece(speedPiece);
            }
        });
    }

    private void drawTime(int seconds) {
        String secs, mins;
        if (seconds / 60 < 10)
            mins = "0" + seconds / 60;
        else mins = String.valueOf(seconds / 60);
        if (seconds % 60 < 10)
            secs = "0" + seconds % 60;
        else secs = String.valueOf(seconds % 60);
        if (mins.equals("99") & secs.equals("99"))
            return;
        Piece timePiece = pieces[1][(boardSize / 2) - 1];
        Graphics g = timePiece.getImage().getGraphics();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, timePiece.getWidth(), timePiece.getHeight() - 2);
        g.setColor(Color.WHITE);
        g.drawString(mins + ":" + secs, 0, 20);
        timePiece.repaint();
    }

    //---------------------First Draw Methods END-----------------------//
    //--------------------- re-draw Methods-----------------------//

    private void reDrawScoreLabel(Piece scorePiece){
        Graphics g = scorePiece.getImage().getGraphics();
        String currentScoreString = String.valueOf(currentScore);
        while (currentScoreString.length()<7)
            currentScoreString = "0"+currentScoreString;
        if (currentScore>currentHighScore)
            reDrawHighScoreLabel(pieces[22][22],currentScoreString);
        g.setColor(Color.BLACK);
        g.fillRect(0,scorePiece.getHeight()/2,scorePiece.getWidth(),scorePiece.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString(currentScoreString, 0, scorePiece.getHeight());
        scorePiece.repaint();
    }

    private void reDrawHighScoreLabel(Piece highScorePiece, String highScore){
        Graphics g = highScorePiece.getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,highScorePiece.getHeight()/2,highScorePiece.getWidth(),highScorePiece.getHeight());
        while (highScore.length()<7)
            highScore = "0"+highScore;
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("     " + highScore, 0, highScorePiece.getHeight());
        highScorePiece.repaint();
    }



    private void reDrawPausePiece(Piece pausePiece) {
        Graphics g = pausePiece.getImage().getGraphics();
        if (pauseStatus) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, pausePiece.getWidth(), pausePiece.getHeight() - 4);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString(" PAUSE", 3, 18);
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, pausePiece.getWidth(), pausePiece.getHeight() - 2);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString(" PAUSE", 3, 18);
        }
        g.drawRect(0, 0, pausePiece.getWidth() - 1, pausePiece.getHeight() - 5);
        pausePiece.repaint();
    }

    private void reDrawSpeedPiece(Piece speedPiece) {
        Graphics g = speedPiece.getImage().getGraphics();
        if (speedActivated) {
            g.setColor(Color.RED);
            g.fillRect(1, 4, speedPiece.getWidth() - 4, speedPiece.getHeight() - 8);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
            g.drawString(" SPEED", 0, 22);
            g.drawString("     X2", 0, 42);
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 3, speedPiece.getWidth(), speedPiece.getHeight() - 2);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 18));

            g.drawString(" SPEED", 0, 22);
            g.drawString("     X2", 0, 42);
        }
        g.drawRect(0, 4, speedPiece.getWidth() - 4, speedPiece.getHeight() - 8);
        speedPiece.repaint();
    }

    //--------------------- re-draw Methods END-----------------------//
    //------------------------Fruits--------------------------------//

    private void insertFruits(){

    }


    //-----------------------Fruits END---------------------------//

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
    //-----------------------------Movement----------------------//
    public void move(int direction, Piece[][] pieces, Player player) {
        int x = playerLocation[0], y = playerLocation[1];
        switch (direction) {
            case 1://Move Up.
                if (pieces[x - 1][y].isWall())
                    break;
                pieces[x - 1][y].setImage(player.getImage());
                drawBlack(pieces[x][y]);
                updateScore(pieces[x-1][y]);
                playerLocation[0] -= 1;
                break;
            case 2://Move Right.
                if (pieces[x][y + 1].isWall())
                    break;
                pieces[x][y + 1].setImage(player.getImage());
                drawBlack(pieces[x][y]);
                updateScore(pieces[x][y+1]);
                playerLocation[1] += 1;
                break;
            case 3://Move Down.
                if (pieces[x + 1][y].isWall())
                    break;
                pieces[x + 1][y].setImage(player.getImage());
                drawBlack(pieces[x][y]);
                updateScore(pieces[x+1][y]);
                playerLocation[0] += 1;
                break;
            case 4://Move Left.
                if (pieces[x][y - 1].isWall())
                    break;
                pieces[x][y - 1].setImage(player.getImage());
                drawBlack(pieces[x][y]);
                updateScore(pieces[x][y-1]);
                playerLocation[1] -= 1;
                break;
        }
    }
}
