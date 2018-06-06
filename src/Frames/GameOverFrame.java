package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

class GameOverFrame extends JFrame{
    private JPanel containerPanel;//Frame's Background Panel.

    GameOverFrame(int[] points){
        super("Game Over");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.

        ActionListener backToMainListener = e -> {
            setVisible(false);
            MainFrame.getInstance().setVisible(true);
        };

        createFrame();//Create Frame for user who got a new highscore.

        JLabel label1=new JLabel("Game Over!");
        label1.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,60));
        label1.setForeground(Color.WHITE);
        containerPanel.add(label1, BorderLayout.PAGE_START);

        JLabel label2=new JLabel("New Score! Enter Your Name");
        label2.setFont(new Font("Forte",Font.PLAIN,32));
        label2.setForeground(Color.RED);
        containerPanel.add(label2);

        JTextField text=new JTextField(15);
        text.setFont(new Font("David",Font.PLAIN,35));
        text.setHorizontalAlignment(JTextField.CENTER);
        containerPanel.add(text, BorderLayout.CENTER);

        ActionListener saveGameListener = e -> {
            setVisible(false);
            WinnerTableFrame.getInstance().addRowToTable(text.getText(),points);
            WinnerTableFrame.getInstance().setVisible(true);
        };

        JPanel cont = new JPanel();
        JButton backBtn1=new JButton("Save");
        backBtn1.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,30));
        backBtn1.addActionListener(saveGameListener);
        cont.add(backBtn1,BorderLayout.WEST);
        JButton backBtn2=new JButton("Return");
        backBtn2.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,30));
        backBtn2.addActionListener(backToMainListener);
        cont.add(backBtn2,BorderLayout.EAST);

        JLabel label= new JLabel();
        label.setText("<html> Your Score: "+points[0] + "<br>The number of the regular pills you collate : "+points[1]+ "<br>The number of the energy pills you collate : "+points[2]+"<br>The number of the pineapples you collate : "+points[3]+"<br>The number of the apples you collate : "+points[4]+"<br>The number of the strawberrys you collate: "+points[5]+"<br></html>");
        label.setFont(new Font("David",Font.BOLD,20));
        label.setForeground(Color.white);
        label.setBackground(Color.black);
        JPanel cont2 =new JPanel();
        cont2.setBackground(Color.BLACK);
        cont2.add(label);
        containerPanel.add(cont2);

        containerPanel.add(cont,BorderLayout.PAGE_END);
    }//Constructor

    private void createFrame(){
        setSize(596,678);//Set Frame dimensions.
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
