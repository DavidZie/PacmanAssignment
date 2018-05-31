package Logic;

import GameComponents.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class Keyboard {

    public static void bindKeyboard(JPanel jPanel, Board board) {
        InputMap inputMap = jPanel.getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        ActionMap actionMap = jPanel.getActionMap();
        actionMap.put("Up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (board.isPauseStatus())
                        return;
                    board.getPacman().changeDirection(1,board.getPieces());
                } catch (NullPointerException e1) {
                }
            }
        });
        actionMap.put("Right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isPauseStatus())
                    return;
                try {
                    board.getPacman().changeDirection(2,board.getPieces());
                } catch (NullPointerException e1) {
                }
            }
        });
        actionMap.put("Down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isPauseStatus())
                    return;
                try {
                    board.getPacman().changeDirection(3,board.getPieces());
                } catch (NullPointerException e1) {
                }
            }
        });
        actionMap.put("Left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isPauseStatus())
                    return;
                try {
                    board.getPacman().changeDirection(4,board.getPieces());
                } catch (NullPointerException e1) {
                }
            }
        });
    }//Bind Key Buttons to Corresponding Actions. Arrows Move Piece of Possible and update Moves Counter.

}
