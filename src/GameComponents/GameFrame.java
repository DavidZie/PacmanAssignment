package GameComponents;

import Logic.Keyboard;

import javax.swing.*;
import java.util.Stack;

import static Logic.Globals.gameBoards;
import static Logic.Globals.highScoresArray;


public class GameFrame extends JFrame {

    JPanel container;
    Board board;

    public GameFrame() {
        super();
        frameOptions();
    }

    private void frameOptions() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        container = new JPanel();
        add(container);
    }

    public void startGame(int boardIndex,int level){
        setVisible(true);
        if (board!=null)
            remove(board);
        if(boardIndex==1|boardIndex==2) {
            board = new Board((Stack[][]) gameBoards[boardIndex - 1], level, highScoresArray[boardIndex][0]);
        }
        else{
            board = new Board((Stack[][]) gameBoards[2], level, highScoresArray[3][0]);
        }
        Keyboard.bindKeyboard((JPanel) getContentPane(), board);
        container.add(board);
        pack();
    }

}
