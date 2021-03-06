package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.io.IOException;

import static Logic.Globals.imagesPath;

class InstructionsFrame {

    private JFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;//Each Panel is a Tab on the Frame.

    InstructionsFrame(){
        createFrame();
        createTab();
    }

    private void createFrame() {
        frame=new JFrame("Introductions");
        frame.setSize(618,525);//Set Frame dimensions.
        frame.setResizable(false);//Lock Frame Size.
        frame.setVisible(true);//Make Frame Visible.
        frame.setLocationRelativeTo(null);//Center the frame on the screen.

        try {
            panel1=new JPanelWithBackground(imagesPath+"\\intro1.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            panel2=new JPanelWithBackground(imagesPath+"\\intro2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            panel3=new JPanelWithBackground(imagesPath+"\\intro3.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            panel4=new JPanelWithBackground(imagesPath+"\\intro4.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //frame.add(panel1, BorderLayout.CENTER);//Add Container Panel to Frame.
    }

    private void createTab() {
        JTabbedPane tp = new JTabbedPane();
        tp.addTab("Pacman Players", panel1);
        tp.addTab("Gosts",panel2);
        tp.addTab("Irteraction",panel3);
        tp.addTab("Score",panel4);
        frame.add(tp);
    }

}
