package Frames;

import GameComponents.Board;
import Logic.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Logic.Globals.gameBoards;
import static Logic.Globals.highScoresArray;
import static Logic.Globals.mainFrame;


public class GameFrame extends JFrame {

    JPanel container;
    Board board;
    private int boardIndex;
    private JPanel glass;
    private int level = 1;

    public GameFrame() {
        super();
    }

    public JPanel getGlass() {
        return glass;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) { this.board = board; }

    private void frameOptions() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        glass = new JPanel();
        setGlassPane(glass);
        container = new JPanel();
        add(container);
    }

    public void startGame(int boardIndex, int lives,int currentScore){
        frameOptions();
        this.boardIndex=boardIndex;
        setVisible(true);
        if (board!=null)
            remove(board);
        board = new Board((String[][]) gameBoards[boardIndex], level, highScoresArray[0],lives,currentScore);
        Keyboard.bindKeyboard((JPanel) getContentPane(), board);
        container.add(board);
        putGlass();

        pack();



    }

    public void endGame(){
        if (board.getCurrentScore()>highScoresArray[4])
            new GameOverFrame();
        else mainFrame.setVisible(true);
        remove(board);
        remove(container);
        setVisible(false);
        repaint();
    }

    public void finishBoard(int lives,int currentScore){

        if (level==3)
            endGame();
        else {
            remove(board);
            remove(container);
            level++;
            startGame((boardIndex + 1) % 3, lives, currentScore);
        }
    }

    private void putGlass(){
        JPanel glass = (JPanel) getGlassPane();
        JLabel l = new JLabel();
        BufferedImage image = new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
        Graphics g= image.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(250,200,325,200);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.YELLOW);
        g.drawString("GET READY", 325, 275);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Press Space To Start", 325, 325);
        l.setIcon(new ImageIcon(image));
        glass.add(l);
        glass.setOpaque(false);
        getGlassPane().setVisible(true);
    }
}
