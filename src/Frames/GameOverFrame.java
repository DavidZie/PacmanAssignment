package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.gameFrame;
import static Logic.Globals.imagesPath;

public class GameOverFrame extends JFrame{
    private JPanel containerPanel;//Frame's Background Panel.
    private JPanel cont;
    private ActionListener backToMainListener;
    private ActionListener saveGameListener;

    public GameOverFrame(){
        super("Game Over");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.

        backToMainListener = e -> {
            setVisible(false);
            new MainFrame();
        };//Call new GameFrame and hide this Frame.

        saveGameListener = e -> {
            setVisible(false);
            new WinnerTableFrame(gameFrame.getBoard().getCurrentScore());
        };//Call new GameFrame and hide this Frame.

        createFrame();//Create Frame.
        addLabel1();
        addLabel2();
        addTextBox();
        addButtons();
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

    private void addLabel1(){
        JLabel label=new JLabel("Game Over!");
        label.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,60));
        label.setForeground(Color.WHITE);
        containerPanel.add(label, BorderLayout.PAGE_START);
    }
    private void addLabel2() {
        JLabel label=new JLabel("New Record! Enter Name to Save");
        label.setFont(new Font("Forte",Font.PLAIN,32));
        label.setForeground(Color.RED);
        containerPanel.add(label);
    }

    private void addTextBox(){
        JTextField text=new JTextField(15);
        text.setFont(new Font("David",Font.PLAIN,35));
        text.setHorizontalAlignment(JTextField.CENTER);
        containerPanel.add(text, BorderLayout.CENTER);
    }

    private void addButtons() {
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
    }
}
