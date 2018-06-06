package Frames;

import GameComponents.Board;
import Logic.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameFrame;
import static Logic.Globals.highScoresArray;


public class GameFrame extends JFrame {

    private JPanel container;
    private Board board;
    private JPanel glass;

    public GameFrame() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        glass = new JPanel();
        setGlassPane(glass);
    }//Set Frame options and set Glass Pane to appear above Board.

    public JPanel getGlass() {
        return glass;
    }

    public Board getBoard() {
        return board;
    }

    public void startGame(int boardIndex, int lives, int level, int[] currentScore) {
        container = new JPanel();
        add(container);
        setVisible(true);
        if (board != null)
            remove(board);
        int highScore = Integer.valueOf(highScoresArray[0][1]);
        board = new Board(boardIndex, level, highScore, lives, currentScore);
        Keyboard.bindKeyboard((JPanel) getContentPane(), board);
        putGlass();
        container.add(board);
        pack();
    }//Create Board, put Glass on top.


    public void endGame(int[] points) {
        if (board.getCurrentScore()[0] > Integer.valueOf(highScoresArray[4][1])) {
            new GameOverFrame(points,true);
        } else new GameOverFrame(points,false);
        remove(board);
        remove(container);
        setVisible(false);
        repaint();
    }//Game Over. Return to Main menu or Give option to save score if user scored enough points.


    public void finishBoard(int boardIndex, int lives, int level, int[] currentScore) {
        if (level == 3 | lives == 0)
            endGame(currentScore);
        else {
            remove(container);
            repaint();
            gameFrame.startGame((boardIndex + 1) % 3, lives, level + 1, currentScore);
        }
    }//Kill Current board and finish game if user has no more lives/Finished last level.

    private void putGlass() {
        JPanel glass = (JPanel) getGlassPane();
        JLabel l = new JLabel();
        BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(250, 200, 325, 200);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.YELLOW);
        g.drawString("GET READY", 325, 275);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Press Space To Start", 325, 325);
        l.setIcon(new ImageIcon(image));
        glass.add(l);
        glass.setOpaque(false);
        getGlassPane().setVisible(true);
    }//Prepare Glass and put it on.
}

