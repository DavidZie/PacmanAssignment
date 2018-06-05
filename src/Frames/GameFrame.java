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
    }

    public JPanel getGlass() {
        return glass;
    }

    public Board getBoard() {
        return board;
    }

    public void startGame(int boardIndex, int lives,int level, int[] currentScore){
        container = new JPanel();
        add(container);
        setVisible(true);
        if (board!=null)
            remove(board);
        board = new Board(boardIndex, level, highScoresArray[0],lives,currentScore);
        Keyboard.bindKeyboard((JPanel) getContentPane(), board);
        putGlass();
        container.add(board);
        pack();



    }

    public void endGame(){
        if (board.getCurrentScore()[0]>highScoresArray[4])
            new GameOverFrame();
        else new MainFrame();
    public void endGame(int[] points){
        if (board.getCurrentScore()>highScoresArray[4])
            new FinishGameFrame(points);
        else
            new GameOverFrame(points);
        remove(board);
        remove(container);
        setVisible(false);
        repaint();
    }

    public void finishBoard(int boardIndex,int lives,int level,int[] currentScore,int[] points)
        {
        if (level==3|lives==0)
            endGame(points);
        else {
            remove(container);
            repaint();
            gameFrame.startGame((boardIndex+1)%3,lives,level+1,currentScore);
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
