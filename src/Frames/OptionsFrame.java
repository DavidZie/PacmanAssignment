package Frames;

import GameComponents.JPanelWithBackground;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Logic.Globals.gameFrame;
import static Logic.Globals.imagesPath;

public class OptionsFrame extends JFrame {

    private JPanel containerPanel;//Frame's Background Panel.

    private ActionListener startGameListener;

    public OptionsFrame(){
        super("Select Maze");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        startGameListener = e -> {

            setVisible(false);
            gameFrame.startGame(Integer.parseInt(e.getActionCommand())-1,3,1,new int[6]);
        };//Call new GameFrame and hide this Frame.
        createFrame();//Create Frame.
        createImageOptionsPanel();//Create and Add Image Options Panel.
        createImagePanel();//Create and Add Image Panel.
    }//Constructor

    //------------------------Functions-------------------------//

    private void createFrame(){

        setSize(1200,421);//Set Frame dimensions.
        setResizable(false);//Lock Frame Size.
        setLocationRelativeTo(null);//Center the frame on the screen.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        try {
            containerPanel=new JPanelWithBackground(imagesPath+"\\misgeret.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(containerPanel,BorderLayout.CENTER);//Add Container Panel to Frame.
    }

    private void createImageOptionsPanel(){
        JPanel imageOptionsPanel = new JPanel();
        imageOptionsPanel.setBorder(new LineBorder(Color.blue));
        JLabel startLbl = new JLabel("Press the board you want play with");//Create Button with Text.
        startLbl.setFont(new Font("Forte",Font.PLAIN,35));
        startLbl.setForeground(Color.black);
        startLbl.setBackground(Color.blue);
        containerPanel.add(imageOptionsPanel);//Add imageOptions Panel to Container Panel.
        imageOptionsPanel.add(startLbl,BorderLayout.PAGE_START);//Add buttons to Panel.
    }

    private void createImagePanel(){
        JPanel imagePanel = new JPanel();//Create image Panel
        imagePanel.setBorder(new LineBorder(Color.MAGENTA));//Set Panel Border Color.
        updateImageLabel(3,imagePanel);//Attach Default Picture to label.
        containerPanel.add(imagePanel);//Add Image Panel to Container Panel.
    }

    private void updateImageLabel(int selectedImageNumber, JPanel panel){
        try {
            for (int i=1;i<=selectedImageNumber;i++) {
                JButton btn = new JButton();//Create Label for image to be attached.
                BufferedImage image = ImageIO.read(new File(imagesPath + "\\board"+i+".png"));
                btn.setIcon(new ImageIcon(image));
                btn.setActionCommand(String.valueOf(i));
                btn.addActionListener(startGameListener);
                panel.add(btn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
