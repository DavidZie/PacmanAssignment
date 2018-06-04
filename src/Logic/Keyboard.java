package Logic;

import GameComponents.Board;
import GameComponents.Piece;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static Logic.Drawings.reDrawPausePiece;
import static Logic.Globals.gameFrame;


public class Keyboard {

    public static void bindKeyboard(JPanel jPanel, Board board) {
        InputMap inputMap = jPanel.getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Space");
        ActionMap actionMap = jPanel.getActionMap();
        actionMap.put("Up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (board.isPauseStatus())
                        return;
                    Movement.movePacman(1,board);
                } catch (NullPointerException ignored) { }
            }
        });
        actionMap.put("Right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isPauseStatus())
                    return;
                try {
                    Movement.movePacman(2,board);
                } catch (NullPointerException ignored) { }
            }
        });
        actionMap.put("Down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isPauseStatus())
                    return;
                try {
                    Movement.movePacman(3,board);
                } catch (NullPointerException ignored) { }
            }
        });
        actionMap.put("Left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isPauseStatus())
                    return;
                try {
                    Movement.movePacman(4,board);
                } catch (NullPointerException ignored) {}
            }
        });
        actionMap.put("Space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setPauseStatus(!board.isPauseStatus());
                gameFrame.getGlass().setVisible(false);
                if (board.isPauseStatus())
                    board.stop();
                else board.start();
                Piece pausePiece = board.getPieces()[1][23];
                reDrawPausePiece(pausePiece, board.isPauseStatus());
            }
        });
    }//Bind Key Buttons to Corresponding Actions. Arrows Move Piece of Possible and update Moves Counter.

}
