package Logic;

import GameComponents.Board;
import GameComponents.Fruit;
import GameComponents.Piece;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Logic.Globals.*;

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
        timePiece.drawData("3");
    }

    public static void drawScoreLabel(Board board) {
        Piece scorePiece = board.replaceLabels(22, 7, 3, 2);
        scorePiece.drawData("1");
        board.setCurrentScore(0);
        Graphics g = scorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("SCORE:", 0, scorePiece.getHeight()/2);
        reDrawScoreLabel(scorePiece, board.getCurrentScore(), board.getCurrentHighScore(), board.getPieces());
    }

    public static void drawHighScoreLabel(Board board) {
        Piece highScorePiece = board.replaceLabels(22, 22, 5, 2);
        highScorePiece.drawData("1");
        Graphics g = highScorePiece.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("HIGH SCORE:", 0, highScorePiece.getHeight()/2);
        reDrawHighScoreLabel(highScorePiece,String.valueOf(highScoresArray[0]));
    }

    public static void drawPauseButton(Board board) {
        Piece pausePiece = board.replaceLabels(1, 23, 3, 1);
        pausePiece.drawData("3");
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
        speedPiece.drawData("1");
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
        fruitsPiece.drawData("4");

    }
    //---------------------First Draw Methods END-----------------------//

    //--------------------- re-draw Methods-----------------------//

    public static void reDrawScoreLabel(Piece scorePiece, int currentScore, int currentHighScore, Piece[][] pieces){
        Graphics g = scorePiece.getImage().getGraphics();
        StringBuilder currentScoreStringBuilder = new StringBuilder(String.valueOf(currentScore));
        while (currentScoreStringBuilder.length()<7)
            currentScoreStringBuilder.insert(0, "0");
        String currentScoreString = currentScoreStringBuilder.toString();
        if (currentScore>currentHighScore)
            reDrawHighScoreLabel(pieces[22][22],currentScoreString);
        g.setColor(Color.BLACK);
        g.fillRect(0,scorePiece.getHeight()/2,scorePiece.getWidth(),scorePiece.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString(currentScoreString, 0, scorePiece.getHeight());
        scorePiece.repaint();
    }

    private static void reDrawHighScoreLabel(Piece highScorePiece, String highScore){
        Graphics g = highScorePiece.getImage().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,highScorePiece.getHeight()/2,highScorePiece.getWidth(),highScorePiece.getHeight());
        StringBuilder highScoreBuilder = new StringBuilder(highScore);
        while (highScoreBuilder.length()<7)
            highScoreBuilder.insert(0, "0");
        highScore = highScoreBuilder.toString();
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString("     " + highScore, 0, highScorePiece.getHeight());
        highScorePiece.repaint();
    }



    static void reDrawPausePiece(Piece pausePiece, boolean pauseStatus) {
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

    private static void reDrawSpeedPiece(Piece speedPiece, boolean speedActivated) {
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
