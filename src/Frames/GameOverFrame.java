package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class GameOverFrame extends JFrame{
    private JPanel containerPanel;//Frame's Background Panel.
    private JPanel cont;
    private ActionListener backToMainListener;
    private ActionListener saveGameListener;

    public GameOverFrame(int[] points){
        super("Game Over");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.

        backToMainListener = e -> {
            setVisible(false);
            new MainFrame();
        };//Call new GameFrame and hide this Frame.

        createFrame();//Create Frame.

        JLabel label1=new JLabel("Game Over!");
        label1.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,60));
        label1.setForeground(Color.WHITE);
        containerPanel.add(label1, BorderLayout.PAGE_START);

        JLabel label2=new JLabel("Do you want to save your record?");
        label2.setFont(new Font("Forte",Font.PLAIN,32));
        label2.setForeground(Color.RED);
        containerPanel.add(label2);

        JTextField text=new JTextField(15);
        text.setFont(new Font("David",Font.PLAIN,35));
        text.setHorizontalAlignment(JTextField.CENTER);
        containerPanel.add(text, BorderLayout.CENTER);

        saveGameListener = e -> {
            setVisible(false);
            new WinnerTableFrame(gameFrame.getBoard().getCurrentScore());
        };//Call new GameFrame and hide this Frame.

        cont=new JPanel();
        JButton backBtn1=new JButton("Save");
        backBtn1.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,30));
        backBtn1.addActionListener(saveGameListener);
        cont.add(backBtn1,BorderLayout.WEST);
        JButton backBtn2=new JButton("Return");
        backBtn2.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,30));
        backBtn2.addActionListener(backToMainListener);
        cont.add(backBtn2,BorderLayout.EAST);
        containerPanel.add(cont,BorderLayout.PAGE_END);
    }//Constructor

    private void createFrame(){
        setSize(596,573);//Set Frame dimensions.
        setResizable(false);//Lock Frame Size.
        setVisible(true);//Make Frame Visible.
        setLocationRelativeTo(null);//Center the frame on the screen.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        try {
            containerPanel=new JPanelWithBackground(imagesPath+"\\gameover.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(containerPanel, BorderLayout.CENTER);//Add Container Panel to Frame.
    }
}
