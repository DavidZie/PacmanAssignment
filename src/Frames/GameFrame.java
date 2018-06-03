package Frames;

import GameComponents.Board;
import GameComponents.Fruit;
import Logic.Keyboard;

import javax.swing.*;
import java.util.Stack;

import static Logic.Globals.gameBoards;
import static Logic.Globals.highScoresArray;


public class GameFrame extends JFrame {

    JPanel container;
    Board board;
    private int[] eatenFruitsArray;

    public GameFrame() {
        super();
        frameOptions();
        eatenFruitsArray=new int[6];
        for (int i=0;i<6;i++)
            eatenFruitsArray[i]=0;

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) { this.board = board; }

    private void frameOptions() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        container = new JPanel();
        add(container);
    }

    public void startGame(int boardIndex,int level, int lives,int currentScore){
        setVisible(true);
        if (board!=null)
            remove(board);
        board = new Board((Stack[][]) gameBoards[boardIndex - 1], level, highScoresArray[boardIndex-1][0],lives,currentScore);
        Keyboard.bindKeyboard((JPanel) getContentPane(), board);
        container.add(board);
        pack();

    }

    public void endGame(){
        remove(container);
        setVisible(false);
        MainFrame mainFrame=new MainFrame();
        GameOverFrame game=new GameOverFrame();
        repaint();
    }

    public void finishBoard(){
        while(board.getEatenFruits().empty());
        //Fruit fruit=(Fruit)board.getEatenFruits().pop();
    }

}
