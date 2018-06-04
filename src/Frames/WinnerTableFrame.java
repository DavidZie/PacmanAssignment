package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class WinnerTableFrame extends JFrame{

    private JPanel containerPanel;//Frame's Background Panel.
    ActionListener backListener;

    public WinnerTableFrame(){
        super("Winner Table");
        consHelper();
    }//Constructor

    public WinnerTableFrame(int[] score){
        super("Winner Table");
        consHelper();

    }//Constructor with new Score.

    private void consHelper(){
        backListener = e -> {
            setVisible(false);
        };//Call new GameFrame and hide this Frame.
        createFrame();//Create Frame.
        addLabel();
        createTable();//Create and Add Image Options Panel.
        addButton();
    }

    private void createFrame(){
        setSize(686,458);//Set Frame dimensions.
        setResizable(true);//Lock Frame Size.
        setVisible(true);//Make Frame Visible.
        setLocationRelativeTo(null);//Center the frame on the screen.
        try {
            containerPanel=new JPanelWithBackground(imagesPath+"\\misgeret2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(containerPanel, BorderLayout.CENTER);//Add Container Panel to Frame.
    }

    private void addLabel(){
        JLabel label=new JLabel("Champion Table");
        label.setFont(new Font("Algerian",Font.PLAIN,70));
        label.setForeground(Color.WHITE);
        containerPanel.add(label);
    }

    private void createTable(){
        String[] columNames={"Name","Score","Regular Pill","Energy Pill","Pineapple","Apple","Strawberry"};
        Object[][] data=new Object[5][7];
        for (int i=0;i<5;i++)
            for (int j=0;j<7;j++)
                if (j==0)
                    data[i][j]="";
        else
            data[i][j]=0;
        JTable table=new JTable(data,columNames);
        table.setPreferredScrollableViewportSize((new Dimension(475,200)));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane=new JScrollPane(table);
        containerPanel.add(scrollPane);
    }

    private void addButton() {
        JButton backBtn=new JButton("Return Back");
        backBtn.setFont(new Font("Algerian",Font.PLAIN,30));
        backBtn.addActionListener(backListener);
        containerPanel.add(backBtn);
    }
}
