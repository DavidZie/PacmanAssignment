package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logic.Globals.imagesPath;

public class WinnerTableFrame extends JFrame {

    private static WinnerTableFrame ourInstance = new WinnerTableFrame();
    public static WinnerTableFrame getInstance() {
        return ourInstance;
    }

    private JPanel containerPanel;//Frame's Background Panel.
    private ActionListener backListener;
    private String[] columNames={"Name","Score","Regular Pill","Energy Pill","Pineapple","Apple","Strawberry"};
    private String[][] scores;
    private JTable table;

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
    }//Constructor


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
        JLabel label=new JLabel("Leaderboards");
        label.setFont(new Font("Algerian",Font.PLAIN,70));
        label.setForeground(Color.WHITE);
        containerPanel.add(label);
    }

    private void createTable(){
        scores = new String[][]{{"","","","","","",""},{"","","","","","",""},{"","","","","","",""},{"","","","","","",""},{"","","","","","",""}};
        table=new JTable(scores,columNames);
        table.setPreferredScrollableViewportSize((new Dimension(475,200)));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane=new JScrollPane(table);
        containerPanel.add(scrollPane);
    }

    public void addRowToTable(String name, int[] points){
        int index = 4;
        int scoreString;
        if (scores[index][1].equals(""))
            scoreString=0;
        else scoreString = Integer.valueOf(scores[index][1]);
        while (scoreString<points[0]){
            index--;
            if (index==0)
                break;
            if (scores[index][1].equals(""))
                scoreString=0;
            else scoreString = Integer.parseInt(scores[index][1]);
        }
        String[][] newScores = new String[5][7];
        for (int i=0;i<index;i++){
            for (int j=0;j<7;j++)
                newScores[i][j]=scores[i][j];
        }
        for (int i=index+1;i<5;i++){
            for (int j=0;j<7;j++)
                newScores[i][j]=scores[i-1][j];
        }
        scores[index][0]= name;
        for (int i=0;i<points.length;i++){
            scores[index][i+1]=String.valueOf(points[i]);
        }
        remove(table);
        table = new JTable(newScores,columNames);
        scores=newScores;
        repaint();
    }

    private void insertScore(String name, int[] points,int index){
        scores[index][0]= name;
        for (int i=0;i<points.length;i++){
            scores[index][i+1]=String.valueOf(points[i]);
        }
    }

    private void moveScorerDown(int index){
        for (int i=0;i<scores[0].length;i++){
            scores[index+1][i]= scores[index][i];
        }

    }

    private void addButton() {
        JButton backBtn=new JButton("Return");
        backBtn.setFont(new Font("Algerian",Font.PLAIN,30));
        backBtn.addActionListener(backListener);
        containerPanel.add(backBtn);
    }
}
