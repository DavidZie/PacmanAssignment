package GameComponents;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import static Logic.Globals.gameFrame;

public class MainFrame extends JFrame {

    JFrame mainFrame;
    JPanel containerPanel;

    public MainFrame(){
        mainFrame = new JFrame("Pac-Man");//Create Frame
        mainFrame.setVisible(true);
        mainFrame.setSize(400,530);//Set Frame dimensions to 400 width and 400 height
        mainFrame.setLocationRelativeTo(null);//Center the frame on the screen.
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        containerPanel=new JPanel();

        JButton startGameButton = new JButton("Start Game");//Create Button with text.
        JButton exitGameButton  = new JButton("Exit");

        ActionListener startGameListener = e -> {
            mainFrame.setVisible(false);
            gameFrame.startGame(0,3);
        };

        exitGameButton.addActionListener(e -> System.exit(0));//Make Exit button Abort program.
        startGameButton.addActionListener(startGameListener);

        containerPanel.setBounds(0,0,200,200);
        containerPanel.setBorder(new LineBorder(Color.BLACK));
        containerPanel.setBorder(new EmptyBorder(75,0,0,0));
        JLabel title=new JLabel("Pac-Man");
        title.setFont(new Font("Serif",Font.PLAIN,50));
        JPanel startPanel = new JPanel();
        JPanel exitPanel = new JPanel();
        JPanel titlePanel=new JPanel();
        startPanel.add(startGameButton);
        exitPanel.add(exitGameButton);
        titlePanel.add(title);
        titlePanel.setPreferredSize(new Dimension(200,75));
        startGameButton.setPreferredSize(new Dimension(200,75));
        exitGameButton.setPreferredSize(new Dimension(200,75));


        containerPanel.add(titlePanel);
        containerPanel.add(startPanel);//Add Button.
        containerPanel.add(exitPanel);
        mainFrame.add(containerPanel);
    }//Create Main Frame with Buttons and assign actions to buttons.

}