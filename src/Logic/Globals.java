package Logic;

import GameComponents.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Globals {

    //------------Global Variables--------------------//

    public static final int boardSize = 32;
    public static final int pieceSize = 25;
    public static final String imagesPath = System.getProperty("user.dir")+"\\images";
    public static final BufferedImage[] gameImagesArray = loadImages();
    public static final LinkedList<Stack[][]> gameBoards = gameBoardsCreator();
    public static int[][] highScoresArray = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    public static final GameFrame gameFrame = new GameFrame();



    //--------------Variables Generators--------------------//

    private static LinkedList<Stack[][]> gameBoardsCreator() {
        LinkedList<Stack[][]> boardsToLoad = new LinkedList<>();
        Stack[][] cellsInfo = new Stack[boardSize][boardSize];
        File file = new File(System.getProperty("user.dir") + "\\maze.csv");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CSV File Missing!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        mazeFileParser(cellsInfo, scanner);
        boardsToLoad.add(cellsInfo);
        return boardsToLoad;
    }

    private static void mazeFileParser(Stack[][] cellsInfo, Scanner scanner) {
        String line;
        int lineNumber = -1;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            lineNumber++;
            if (!checkLine(line)) {
                JOptionPane.showMessageDialog(null, "CSV File Corrupted!", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (line.equals("#"))
                return;
            String[] strings = line.split(",");
            for (int i = 0; i < 32; i++) {
                cellsInfo[lineNumber][i] = new Stack();
                for (int j = 0; j < strings[i].length(); j++) {
                    int numberToPush = Character.getNumericValue(strings[i].charAt(j));
                    cellsInfo[lineNumber][i].push(numberToPush);
                }
            }
        }
        return;

    }

    private static boolean checkLine(String line) {
        int counter = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                counter++;
                i++;
            }
        }
        return counter == boardSize - 1;
    }

    private static BufferedImage[] loadImages(){
        BufferedImage[] images = new BufferedImage[3];
        System.out.println(imagesPath);
        try { images[0] = ImageIO.read(new File(imagesPath+"\\pineapple.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[1] = ImageIO.read(new File(imagesPath+"\\apple.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[2] = ImageIO.read(new File(imagesPath+"\\strawberry.png")); }
        catch (IOException e) { e.printStackTrace(); }
        return images;
    }
}
