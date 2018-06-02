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
    JMenuBar menuBar;
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


            ActionListener startGameListener = e -> {
                mainFrame.setVisible(false);
                optionsFrame = new OptionsFrame();
            };
            ActionListener winTableListener = e -> {
                mainFrame.setVisible(false);
                WinnerTableFrame winnerTableFrame = new WinnerTableFrame();
            };
            ActionListener finishListener = e -> {
                System.exit(0);
            };

            ActionListener introdactionsListener = e -> {
                mainFrame.setVisible(false);
                InstructionsFrame instructions = new InstructionsFrame();
            };

            ActionListener aboutListener = e -> {
                mainFrame.setVisible(false);
                AboutFrame aboutFrame = new AboutFrame();
            };


            menuBar=new JMenuBar();
            mainFrame.setJMenuBar(menuBar);

            JMenu game=new JMenu("Game");
            menuBar.add(game);
            JMenu help=new JMenu("Help");
            menuBar.add(help);

            JMenuItem start=new JMenuItem("Start Game");
            game.add(start);
            JMenuItem records=new JMenuItem("Records Table");
            game.add(records);
            JMenuItem exit=new JMenuItem("Exit");
            game.add(exit);

            start.addActionListener(startGameListener);
            records.addActionListener(winTableListener);
            exit.addActionListener(finishListener);

            JMenuItem instructions=new JMenuItem("Instructions");
            help.add(instructions);
            JMenuItem about=new JMenuItem("About");
            help.add(about);

            instructions.addActionListener(introdactionsListener);
            about.addActionListener(aboutListener);

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