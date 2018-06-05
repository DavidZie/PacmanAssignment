package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static Logic.Globals.highScoresArray;
import static Logic.Globals.imagesPath;

public class WinnerTableFrame extends JFrame {

    /**
     *
     * LeaderBoards Frame. Scorers are loaded from CSV file.
     */

    private static WinnerTableFrame ourInstance = new WinnerTableFrame();
    public static WinnerTableFrame getInstance() {
        return ourInstance;
    }

    private JPanel containerPanel;//Frame's Background Panel.
    private ActionListener backListener;
    private String[] columNames={"Name","Score","Regular Pill","Energy Pill","Pineapple","Apple","Strawberry"};

    private WinnerTableFrame() {
        super("Winner Table");
        backListener = e -> {
            setVisible(false);
            MainFrame.getInstance().setVisible(true);
        };//Call new GameFrame and hide this Frame.
        createFrame();//Create Frame.
        addLabel();
        createTable();//Create and Add Image Options Panel.
        addButton();
    }//Constructor.


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
        JLabel label=new JLabel("Leader boards");
        label.setFont(new Font("Algerian",Font.PLAIN,70));
        label.setForeground(Color.WHITE);
        containerPanel.add(label);
    }

    private void createTable(){
        JTable table = new JTable(highScoresArray, columNames);
        table.setPreferredScrollableViewportSize((new Dimension(475,200)));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane=new JScrollPane(table);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public void addRowToTable(String name, int[] points){
        int index = 4;
        int scoreString;
        if (highScoresArray[index-1][1].equals(""))
            scoreString=0;
        else scoreString = Integer.valueOf(highScoresArray[index-1][1]);
        while (scoreString<points[0]){
            index--;
            if (index==0)
                break;
            if (highScoresArray[index-1][1].equals(""))
                scoreString=0;
            else scoreString = Integer.parseInt(highScoresArray[index-1][1]);
        }
        System.arraycopy(highScoresArray, index, highScoresArray, index + 1, 4 - index);
        insertScore(name,points,index);
        reWriteCsv();
        repaint();
    }

    private void insertScore(String name, int[] points,int index){
        highScoresArray[index]= new String[7];
        highScoresArray[index][0]= name;
        for (int i=0;i<points.length;i++){
            highScoresArray[index][i+1]=String.valueOf(points[i]);
        }
    }

    private void addButton() {
        JButton backBtn=new JButton("   Return   ");
        backBtn.setFont(new Font("Algerian",Font.PLAIN,30));
        backBtn.addActionListener(backListener);
        containerPanel.add(backBtn);
    }

    private void reWriteCsv(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 7; j++){
                builder.append(highScoresArray[i][j]);//append to the output string
                builder.append(",");//then add comma
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer;
        File file = new File("scores.csv");
        if (!file.exists()){
            try {file.createNewFile();} catch (IOException ignored){}
        }
        try {
            FileWriter fileWriter = new FileWriter("scores.csv");
            writer = new BufferedWriter(fileWriter);
            writer.write(builder.toString());//save the string representation of the board
            writer.close();}
        catch (Exception ignored){}
    }
}
