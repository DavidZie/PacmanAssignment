package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class MainFrame extends JFrame {

    JFrame mainFrame;
    JPanel containerPanel;
    OptionsFrame optionsFrame;

    public MainFrame(){
        mainFrame = new JFrame("Pac-Man");//Create Frame
        mainFrame.setVisible(true);
        mainFrame.setSize(883,590);//Set Frame dimensions to 400 width and 400 height
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);//Center the frame on the screen.
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        mainFrame.pack();
        try {
            containerPanel=new JPanelWithBackground(imagesPath+"\\background.jpg");
            JButton startGameButton = new JButton("Start Game!");//Create Button with text.
            startGameButton.setFont(new Font("Monotype Corsiva",Font.PLAIN,50));
            startGameButton.setBackground(Color.BLACK);
            startGameButton.setForeground(Color.red);
            JButton winTableButton = new JButton("Champions Table");//Create Button with text.
            winTableButton.setFont(new Font("Monotype Corsiva",Font.PLAIN,50));
            winTableButton.setBackground(Color.BLACK);
            winTableButton.setForeground(Color.red);
            JButton exitGameButton  = new JButton("Exit");
            exitGameButton.setFont(new Font("Monotype Corsiva",Font.PLAIN,40));
            exitGameButton.setForeground(Color.red);
            exitGameButton.setBackground(Color.BLACK);

            ActionListener startGameListener = e -> {
                mainFrame.setVisible(false);
                optionsFrame = new OptionsFrame();
            };
            ActionListener winTableListener = e -> {
                mainFrame.setVisible(false);
                WinnerTableFrame winnerTableFrame = new WinnerTableFrame();
            };

            exitGameButton.addActionListener(e -> System.exit(0));//Make Exit button Abort program.
            winTableButton.addActionListener(winTableListener);
            startGameButton.addActionListener(startGameListener);

            containerPanel.setBorder(new EmptyBorder(455,0,0,0));
            containerPanel.setLayout(new BorderLayout());
            startGameButton.setPreferredSize(new Dimension(333,68));
            winTableButton.setPreferredSize(new Dimension(400,68));
            exitGameButton.setPreferredSize(new Dimension(150,68));
            containerPanel.add(startGameButton,BorderLayout.WEST);//Add Button.
            containerPanel.add(winTableButton ,BorderLayout.CENTER);
            containerPanel.add(exitGameButton ,BorderLayout.EAST);
            mainFrame.add(containerPanel);
            mainFrame.pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Create Main Frame with Buttons and assign actions to buttons.

}