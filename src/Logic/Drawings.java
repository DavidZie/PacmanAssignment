package Logic;

import GameComponents.Board;
import GameComponents.Fruit;
import GameComponents.Piece;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

import static Logic.Globals.boardSize;
import static Logic.Globals.highScoresArray;
import static Logic.Globals.pieceSize;

public class Drawings {



    //---------------------First Draw Methods-----------------------//

    public static void drawGate(Piece piece) {
        Graphics g = piece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, piece.getWidth(), 2);
    }

    public static void drawLife(Piece[][] pieces, int lives) {
        Graphics g;
        for (int i=7;i<10-lives;i++){
            g = pieces[1][i].getImage().getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0,0,pieceSize,pieceSize-2);
        }

        for (int i = 10-lives; i < 10; i++) {
            g = pieces[1][i].getImage().getGraphics();
            g.setColor(Color.YELLOW);
            g.fillOval(0, 0, 22, 22);
        }
        g = pieces[1][10].getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,pieceSize,pieceSize-2);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("X"+String.valueOf(lives), 0, 20);
    }

    public static void drawTimeLabel(Board board) {
        Piece timePiece = board.replaceLabels(1, (boardSize / 2) - 1, 2, 1);
        Graphics g = timePiece.getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, timePiece.getWidth(), timePiece.getHeight());
        Stack data = new Stack();
        data.push(3);
        timePiece.drawData(data);
    }

    public static void drawScoreLabel(Board board) {
        Piece scorePiece = board.replaceLabels(22, 7, 3, 2);
        Stack data = new Stack();
        data.push(1);
        scorePiece.drawData(data);
        board.setCurrentScore(0);
        Graphics g = scorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("SCORE:", 0, scorePiece.getHeight()/2);
        reDrawScoreLabel(scorePiece, board.getCurrentScore(), board.getCurrentHighScore(), board.getPieces());
    }

    public static void drawHighScoreLabel(Board board) {
        Piece highScorePiece = board.replaceLabels(22, 22, 5, 2);
        Stack data = new Stack();
        data.push(1);
        highScorePiece.drawData(data);
        Graphics g = highScorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("HIGH SCORE:", 0, highScorePiece.getHeight()/2);
        reDrawHighScoreLabel(highScorePiece,String.valueOf(highScoresArray[0][0]));
    }

    public static void drawPauseButton(Board board) {
        Piece pausePiece = board.replaceLabels(1, 23, 3, 1);
        Stack data = new Stack();
        data.push(3);
        pausePiece.drawData(data);
        reDrawPausePiece(pausePiece, board.isPauseStatus());

        pausePiece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                board.setPauseStatus(!board.isPauseStatus());
                if (board.isPauseStatus())
                    board.stop();
                else board.start();
                reDrawPausePiece(pausePiece, board.isPauseStatus());
            }
        });
    }

    public static void drawSpeedLabel(Board board) {
        Piece speedPiece = board.replaceLabels(22, 15, 3, 2);
        Stack data = new Stack();
        data.push(1);
        speedPiece.drawData(data);
        reDrawSpeedPiece(speedPiece, board.isSpeedActivated());
        speedPiece.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                board.setSpeedActivated(!board.isSpeedActivated());
                if (board.isSpeedActivated()) {
                    board.speedUp();
                } else {
                    board.speedDown();
                }
                reDrawSpeedPiece(speedPiece, board.isSpeedActivated());
            }
        });
    }

    public static void drawTime(int seconds, Piece[][] pieces) {
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

    public static void drawFruitsLabel(Board board){
        Piece fruitsPiece = board.replaceLabels(9,27,1,12);
        Stack data = new Stack();
        data.push(4);
        fruitsPiece.drawData(data);

    }
    //---------------------First Draw Methods END-----------------------//

    //--------------------- re-draw Methods-----------------------//

    public static void reDrawScoreLabel(Piece scorePiece, int currentScore, int currentHighScore, Piece[][] pieces){
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

    public static void reDrawHighScoreLabel(Piece highScorePiece, String highScore){
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



    public static void reDrawPausePiece(Piece pausePiece, boolean pauseStatus) {
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

    public static void reDrawSpeedPiece(Piece speedPiece, boolean speedActivated) {
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

    public static void reDrawFruitsLabel(Board board, int eatenFruits, Fruit fruit){
        Piece fruitPiece = board.getPieces()[9][27];
        fruitPiece.getImage().getGraphics().drawImage(fruit.getMyImage(),2,(pieceSize-1)*eatenFruits,null);
        fruitPiece.repaint();
    }



    //--------------------- re-draw Methods END-----------------------//

}
