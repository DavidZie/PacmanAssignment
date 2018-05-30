package GameComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static Logic.Globals.imagesPath;
import java.io.File;
import java.io.IOException;


public class OptionsFrame extends JFrame {

    JFrame optionsFrame;
    private JPanel containerPanel;//Frame's Background Panel.
    private JLabel imageLabel;//Selected Image Label.
    private String path;//Path to Sample Pictures.
    int selectedImageNumber=0;//index of selected Image from Samples.

    public OptionsFrame(){
        createFrame();//Create Frame.
        createImageOptionsPanel();//Create and Add Image Options Panel.
        createImagePanel();//Create and Add Image Panel.
    }//Constructor

    //------------------------Functions-------------------------//

    private void createFrame(){
        optionsFrame=new JFrame("Select Maze");
        optionsFrame.setSize(500,500);//Set Frame dimensions.
        optionsFrame.setResizable(false);//Lock Frame Size.
        optionsFrame.setVisible(true);//Make Frame Visible.
        optionsFrame.setLocationRelativeTo(null);//Center the frame on the screen.
        optionsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        containerPanel = new JPanel();//Create Container Panel.
        containerPanel.setBackground(Color.blue);
        optionsFrame.add(containerPanel);//Add Container Panel to Frame.
    }

    private void createImageOptionsPanel(){
        JPanel imageOptionsPanel = new JPanel();
        imageOptionsPanel.setBorder(new LineBorder(Color.red));

        JButton startGameButton = new JButton("Start Game");//Create Button with Text.
        JButton changeImageButton = new JButton("Change Image");

        ActionListener changeImageListener = e -> {
            optionsFrame.setVisible(false);
            //new SelectPictureFrame();
        };//Call new SelectPictureFrame and hide this Frame.

        ActionListener startGameListener = e -> {
            optionsFrame.setVisible(false);
            GameFrame gameFrame=new GameFrame();
            gameFrame.setVisible(true);
        };//Call new GameFrame and hide this Frame.

        changeImageButton.addActionListener(changeImageListener);
        startGameButton.addActionListener(startGameListener);

        imageOptionsPanel.add(startGameButton);//Add buttons to Panel.
        imageOptionsPanel.add(changeImageButton);
        containerPanel.add(imageOptionsPanel);//Add imageOptions Panel to Container Panel.
    }

    private void createImagePanel(){
        JPanel imagePanel = new JPanel();//Create image Panel
        imagePanel.setBorder(new LineBorder(Color.MAGENTA));//Set Panel Border Color.
        imageLabel = new JLabel();//Create Label for image to be attached.
        updateImageLabel();//Attach Default Picture to label.

        imagePanel.add(imageLabel);//Attach label to panel.
        containerPanel.add(imagePanel);//Add Image Panel to Container Panel.
    }
    private void updateImageLabel(){
        imageLabel.setText("");//Empty Text and Icon in to make update leaves no traces of overwritten data.
        try {
            BufferedImage image=ImageIO.read(new File(imagesPath+"\\board1.png"));
            imageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadImage(String index){
        setVisible(true);
        int newSelection = Integer.parseInt(index);
        if (newSelection!=selectedImageNumber){
            selectedImageNumber=newSelection;
            updateImageLabel();
        }//Get Index of new Selected image and update the Image Panel if necessary.
    }
}
