package GameComponents;

import Logic.Keyboard;

import javax.swing.*;
import java.util.Stack;

import static Logic.Globals.gameBoards;


public class GameFrame extends JFrame {

    JPanel container;
    Board board;

    public GameFrame() {
        super();
        frameOptions();
        Keyboard.bindKeyboard((JPanel) getContentPane(), board);
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
        board = new Board(gameBoards.get(boardIndex),level);
        container.add(board);
        pack();
    }

}
