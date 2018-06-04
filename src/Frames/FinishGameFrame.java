package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class FinishGameFrame {

        JFrame finishFrame;
        private JPanel containerPanel;//Frame's Background Panel.
        JPanel cont;
        ActionListener backToMainListener;
        ActionListener saveGameListener;

        public FinishGameFrame(int[] points){

            backToMainListener = e -> {
                finishFrame.setVisible(false);
                MainFrame mainFrame=new MainFrame();
            };//Call new GameFrame and hide this Frame.

            createFrame();//Create Frame.

            JLabel label=new JLabel("Congratulations!");
            label.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,75));
            label.setForeground(Color.RED);
            containerPanel.add(label, BorderLayout.PAGE_START);

            JLabel label2=new JLabel("Do you want to save your record?");
            label2.setFont(new Font("Forte",Font.PLAIN,65));
            label2.setForeground(Color.RED);
            containerPanel.add(label2);

            JTextField text=new JTextField(20);
            text.setFont(new Font("David",Font.PLAIN,40));
            text.setHorizontalAlignment(JTextField.CENTER);
            containerPanel.add(text, BorderLayout.CENTER);

            saveGameListener = e -> {
                finishFrame.setVisible(false);
                new MainFrame();
                new WinnerTableFrame(text.getText(),points);
            };//Call new GameFrame and hide this Frame.

            cont=new JPanel();
            JButton backBtn1=new JButton("Save");
            backBtn1.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,45));
            backBtn1.addActionListener(saveGameListener);
            cont.add(backBtn1,BorderLayout.WEST);
            JButton backBtn2=new JButton("Return");
            backBtn2.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,45));
            backBtn2.addActionListener(backToMainListener);
            cont.add(backBtn2,BorderLayout.EAST);
            containerPanel.add(cont,BorderLayout.PAGE_END);

        }//Constructor

        private void createFrame(){
            finishFrame=new JFrame("Bravo!");
            finishFrame.setSize(968,502);//Set Frame dimensions.
            finishFrame.setResizable(false);//Lock Frame Size.
            finishFrame.setVisible(true);//Make Frame Visible.
            finishFrame.setLocationRelativeTo(null);//Center the frame on the screen.
            finishFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
            try {
                containerPanel=new JPanelWithBackground(imagesPath+"\\finish.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            finishFrame.add(containerPanel, BorderLayout.CENTER);//Add Container Panel to Frame.
        }
  }


