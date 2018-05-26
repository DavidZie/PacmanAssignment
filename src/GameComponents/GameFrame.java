package GameComponents;

import Logic.Keyboard;

import javax.swing.*;



public class GameFrame extends JFrame {

    JPanel container;
    Board board;

    public GameFrame(){
        super();
        frameOptions();
        addBoard();
        pack();
        Keyboard.bindKeyboard((JPanel)getContentPane(), board);

    }

    //Contents


    private void frameOptions(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        container = new JPanel();
        add(container);

    }

    private void addBoard(){
        board = new Board();
        container.add(board);

   }

}
