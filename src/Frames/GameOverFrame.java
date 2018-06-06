package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

class GameOverFrame extends JFrame{
    private JPanel containerPanel;//Frame's Background Panel.

    GameOverFrame(int[] points,boolean record){
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

        String newRecord="";
        if (record)
            newRecord = "New Score! Enter Your Name";
        JLabel label2=new JLabel(newRecord);
        label2.setFont(new Font("Forte",Font.PLAIN,32));
        label2.setForeground(Color.RED);
        containerPanel.add(label2);
        JPanel cont = new JPanel();
        if (record) {
            JTextField text = new JTextField(10);
            text.setFont(new Font("David", Font.PLAIN, 35));
            text.setHorizontalAlignment(JTextField.CENTER);
            containerPanel.add(text, BorderLayout.CENTER);

            ActionListener saveGameListener = e -> {
                setVisible(false);
                WinnerTableFrame.getInstance().addRowToTable(text.getText(), points);
                WinnerTableFrame.getInstance().setVisible(true);
            };

            JButton backBtn1 = new JButton("Save");
            backBtn1.setFont(new Font("Matura MT Script Capitals", Font.PLAIN, 30));
            backBtn1.addActionListener(saveGameListener);
            cont.add(backBtn1, BorderLayout.WEST);
        }
        JButton backBtn2=new JButton("Return");
        backBtn2.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,30));
        backBtn2.addActionListener(backToMainListener);
        cont.add(backBtn2,BorderLayout.EAST);

        JPanel panel=new JPanel();
        if (record)
            panel.setPreferredSize(new Dimension(400,180));
        else
            panel.setPreferredSize(new Dimension(400,240));
        JLabel label= new JLabel();
        label.setText("<html> Total Score: "+points[0] + "<br>Regular Pills: "+points[1]+ "<br>Energy Pills: "+points[2]+"<br>Pineapples : "+points[3]+"<br>Apples: "+points[4]+"<br>Strawberries: "+points[5]+"<br></html>");
        if (record)
            label.setFont(new Font("David",Font.BOLD,35));
        else
            label.setFont(new Font("David",Font.BOLD,22));
        label.setForeground(Color.white);
        label.setBackground(Color.black);
        panel.setBackground(Color.BLACK);
        panel.add(label);
        containerPanel.add(panel);

        containerPanel.add(cont);
    }//Constructor

    private void createFrame(){
        setSize(480,678);//Set Frame dimensions.
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
