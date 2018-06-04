package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.gameFrame;
import static Logic.Globals.imagesPath;
import static Logic.Globals.mainFrame;

public class WinnerTableFrame {

    JFrame winnerTableFrame;
    private JPanel containerPanel;//Frame's Background Panel.
    ActionListener backListener;
    String[] columNames={"Name","Score","Regular Pill","Energy Pill","Pineapple","Apple","Strawberry"};
    Object[][] data;

    public WinnerTableFrame(){
        backListener = e -> {
            winnerTableFrame.setVisible(false);
            if (mainFrame.isVisible()==false) mainFrame.setVisible(true);
        };//Call new GameFrame and hide this Frame.
        createFrame();//Create Frame.
        addLabel();
        createTable();//Create and Add Image Options Panel.
        addButton();
    }//Constructor

    public WinnerTableFrame(String name, int[] points){
        backListener = e -> {
            winnerTableFrame.setVisible(false);
            if (mainFrame.isVisible()==false) mainFrame.setVisible(true);
        };//Call new GameFrame and hide this Frame.
        createFrame();//Create Frame.
        addLabel();
        createTable();//Create and Add Image Options Panel.
        addRowToTable(name, points);
        addButton();
    }//Constructor

    private void createFrame(){
        winnerTableFrame=new JFrame("Winner Table");
        winnerTableFrame.setSize(686,458);//Set Frame dimensions.
        winnerTableFrame.setResizable(true);//Lock Frame Size.
        winnerTableFrame.setVisible(true);//Make Frame Visible.
        winnerTableFrame.setLocationRelativeTo(null);//Center the frame on the screen.
        try {
            containerPanel=new JPanelWithBackground(imagesPath+"\\misgeret2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        winnerTableFrame.add(containerPanel, BorderLayout.CENTER);//Add Container Panel to Frame.
    }

    private void addLabel(){
        JLabel label=new JLabel("Champion Table");
        label.setFont(new Font("Algerian",Font.PLAIN,70));
        label.setForeground(Color.WHITE);
        containerPanel.add(label);
    }

    private void createTable(){
        data=new Object[5][7];
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

    private void addRowToTable(String name, int[] points){
        for (int i=0;i<data[4].length-1;i++)
            data[4][i+1]=points[i];
        data[4][0]=name;

        swapInts(data, points);
        swapName(data, name);
    }

    private void swapInts(Object[][] data, int[] points) {
        int[] tmp=points;
        int index=4;
        while (index>0){
            for (int i=0;i<data[index].length-1;i++)
                data[index][i+1]=data[index-1][i+1];
            index--;
        }
        for (int i=0;i<data[0].length-1;i++)
            data[0][i+1]=points[i];
    }

    private void swapName(Object[][] data, String name) {
        String tmp=name;
        int index=4;
        while (index>0){
            data[index][0]=data[index-1][0];
            index--;
        }
        data[0][0]=tmp;
    }

    private void addButton() {
        JButton backBtn=new JButton("Return Back");
        backBtn.setFont(new Font("Algerian",Font.PLAIN,30));
        backBtn.addActionListener(backListener);
        containerPanel.add(backBtn);
    }
}
