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
        ActionListener backToMainListener;
        ActionListener saveGameListener;

        public FinishGameFrame(){

            backToMainListener = e -> {
                finishFrame.setVisible(false);
                MainFrame mainFrame=new MainFrame();
            };//Call new GameFrame and hide this Frame.

            saveGameListener = e -> {
                finishFrame.setVisible(false);
                WinnerTableFrame winnerTableFrame=new WinnerTableFrame();
            };//Call new GameFrame and hide this Frame.

            createFrame();//Create Frame.
            addLabel1();
            addLabel2();
            addButton1();
            addButton2();
        }//Constructor

        private void createFrame(){
            finishFrame=new JFrame("Bravo!");
            finishFrame.setSize(968,502);//Set Frame dimensions.
            finishFrame.setResizable(true);//Lock Frame Size.
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

        private void addLabel1(){
            JLabel label=new JLabel("Congratulations!");
            label.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,75));
            label.setForeground(Color.RED);
            containerPanel.add(label, BorderLayout.PAGE_START);
        }
        private void addLabel2() {
            JLabel label=new JLabel("Do you want to save your record?");
            label.setFont(new Font("Forte",Font.PLAIN,65));
            label.setForeground(Color.RED);
            containerPanel.add(label);
        }

        private void addButton1() {
            JButton backBtn=new JButton("Save");
            backBtn.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,45));
            backBtn.addActionListener(saveGameListener);
            containerPanel.add(backBtn,BorderLayout.WEST);
        }
        private void addButton2() {
            JButton backBtn=new JButton("Return");
            backBtn.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,45));
            backBtn.addActionListener(backToMainListener);
            containerPanel.add(backBtn,BorderLayout.EAST);
        }
  }


