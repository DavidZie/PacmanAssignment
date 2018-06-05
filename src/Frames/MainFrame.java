package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class MainFrame extends JFrame {
    private static MainFrame ourInstance = new MainFrame();

    public static MainFrame getInstance() {
        return ourInstance;
    }

    JMenuBar menuBar;
    JPanel container;

    private MainFrame() {
        super("Pac-Man");//Create Frame
        setSize(883,590);//Set Frame dimensions to 400 width and 400 height
        setResizable(false);
        setLocationRelativeTo(null);//Center the frame on the screen.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        pack();
        try {
            container=new JPanelWithBackground(imagesPath+"\\background.jpg");
            ActionListener startGameListener = e -> {
                setVisible(false);
                OptionsFrame.getInstance().setVisible(true);
            };
            ActionListener winTableListener = e -> {
                setVisible(false);
                WinnerTableFrame.getInstance().setVisible(true);
            };
            ActionListener finishListener = e -> {
                System.exit(0);
            };

            ActionListener introdactionsListener = e -> {
                InstructionsFrame instructions = new InstructionsFrame();
            };

            ActionListener aboutListener = e -> {
                AboutFrame aboutFrame = new AboutFrame();
            };


            menuBar=new JMenuBar();
            setJMenuBar(menuBar);

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


            ImageIcon icon1=new ImageIcon(imagesPath+"\\playnow.png");
            JButton startGameButton = new JButton("",icon1);//Create Button with text.
            startGameButton.setBackground(Color.BLACK);
            ImageIcon icon2=new ImageIcon(imagesPath+"\\win.png");
            JButton winTableButton = new JButton("",icon2);//Create Button with text.
            winTableButton.setBackground(Color.BLACK);
            ImageIcon icon3=new ImageIcon(imagesPath+"\\exit.png");
            JButton exitGameButton  = new JButton("",icon3);
            exitGameButton.setBackground(Color.BLACK);

            exitGameButton.addActionListener(e -> System.exit(0));//Make Exit button Abort program.
            winTableButton.addActionListener(winTableListener);
            startGameButton.addActionListener(startGameListener);

            container.setBorder(new EmptyBorder(455,0,0,0));
            container.setLayout(new BorderLayout());
            startGameButton.setPreferredSize(new Dimension(310,100));
            winTableButton.setPreferredSize(new Dimension(520,100));
            exitGameButton.setPreferredSize(new Dimension(100,100));
            container.add(startGameButton,BorderLayout.WEST);//Add Button.
            container.add(winTableButton ,BorderLayout.CENTER);
            container.add(exitGameButton ,BorderLayout.EAST);
            add(container);
            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Create Main Frame with Buttons and assign actions to buttons.

}

