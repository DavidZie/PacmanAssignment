package Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Globals {

    //------------Global Variables--------------------//

    public static final int boardSize = 32;
    public static final int pieceSize = 25;
    public static int[] playerLocation = {14,16};
    public static final BufferedImage[] gameImagesArray = GameImages.imagesCreator();
    public static final LinkedList<Stack[][]> gameBoards = gameBoardsCreator();


    //--------------Variables Generators--------------------//

    private static LinkedList<Stack[][]> gameBoardsCreator(){
        LinkedList<Stack[][]> boardsToLoad = new LinkedList<>();
        Stack[][] cellsInfo = new Stack[boardSize][boardSize];
        File file = new File(System.getProperty("user.dir")+"\\maze.csv");
        Scanner scanner = null;
        try { scanner = new Scanner(file);}
        catch (Exception e){JOptionPane.showMessageDialog(null,"CSV File Missing!","Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);}
        mazeFileParser(cellsInfo,scanner);
        boardsToLoad.add(cellsInfo);
        return boardsToLoad;
    }

    private static void mazeFileParser(Stack[][] cellsInfo, Scanner scanner){
        String line;
        int lineNumber = -1;
        while (scanner.hasNext()){
            line = scanner.nextLine();
            lineNumber++;
            if (!checkLine(line)) {
                JOptionPane.showMessageDialog(null, "CSV File Corrupted!", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (line.equals("#"))
                return;
            String[] strings = line.split(",");
            for (int i=0;i<32;i++){
                cellsInfo[lineNumber][i] = new Stack();
                for (int j=0;j<strings[i].length();j++){
                    int numberToPush = Character.getNumericValue(strings[i].charAt(j));
                    cellsInfo[lineNumber][i].push(numberToPush);
                }
            }
        }
        return;

    }

    private static boolean checkLine(String line){
        int counter=0;
        for (int i=0;i<line.length();i++){
            if (line.charAt(i)==','){
                counter++;
                i++;
            }
        }
        return counter==boardSize-1;

    }

}
