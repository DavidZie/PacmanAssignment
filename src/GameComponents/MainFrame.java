package GameComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class MainFrame extends JFrame {

    JFrame mainFrame;
    JPanel containerPanel;
    OptionsFrame optionsFrame;
    public static final String songPath = System.getProperty("user.dir");

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
            startGameButton.setFont(new Font("Steamer",Font.PLAIN,60));
            startGameButton.setBackground(Color.BLACK);
            startGameButton.setForeground(Color.red);
            JButton exitGameButton  = new JButton("Exit");
            exitGameButton.setFont(new Font("Steamer",Font.PLAIN,50));
            exitGameButton.setForeground(Color.red);
            exitGameButton.setBackground(Color.BLACK);

            ActionListener startGameListener = e -> {
                mainFrame.setVisible(false);
                optionsFrame = new OptionsFrame();
                optionsFrame.setVisible(true);
            };
            exitGameButton.addActionListener(e -> System.exit(0));//Make Exit button Abort program.
            startGameButton.addActionListener(startGameListener);

            containerPanel.setBorder(new EmptyBorder(455,0,0,0));
            containerPanel.setLayout(new BorderLayout());
            startGameButton.setPreferredSize(new Dimension(675,68));
            exitGameButton.setPreferredSize(new Dimension(209,68));
            containerPanel.add(startGameButton,BorderLayout.WEST);//Add Button.
            containerPanel.add(exitGameButton ,BorderLayout.EAST);
            mainFrame.add(containerPanel);
            mainFrame.pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Create Main Frame with Buttons and assign actions to buttons.

}