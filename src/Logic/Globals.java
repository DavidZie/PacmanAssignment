package Logic;

import Frames.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Globals {

    //------------Global Variables--------------------//

    public static final int boardSize = 32;
    public static final int pieceSize = 25;
    public static final String imagesPath = System.getProperty("user.dir")+"\\images";
    public static final BufferedImage[][] gameImagesArray = loadImages();
    public static final Object[]gameBoards = gameBoardsArrayCreator();
    public static String[][] highScoresArray = loadScores();
    public static final GameFrame gameFrame = new GameFrame();



    //--------------Variables Generators--------------------//

    private static Object[] gameBoardsArrayCreator(){
        Object[] array = new Object[3];
        for (int i=0; i<3;i++){
            array[i] = gameBoardsCreator(i+1);
        }
        return array;
    }

    private static String[][] gameBoardsCreator(int index) {
        String[][] cellsInfo = new String[boardSize][boardSize];
        File file = new File(System.getProperty("user.dir") + "\\maze"+index+".csv");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CSV File Missing!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        mazeFileParser(cellsInfo, scanner);
        return cellsInfo;
    }

    private static void mazeFileParser(String[][] cellsInfo, Scanner scanner) {
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
            System.arraycopy(strings, 0, cellsInfo[lineNumber], 0, 32);
        }
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

    private static String[][] loadScores(){
        String[][] scores = new String[5][];
        int rowIndex=0;
        File file = new File("scores.csv");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()){
                scores[rowIndex] = scanner.nextLine().split(",");
                rowIndex++;
            }
        } catch (FileNotFoundException exception){
            for (int i=0;i<5;i++){
                scores[i] = new String[7];
                for (int j=0;j<7;j++)
                    scores[i][j]="0";
            }
        }
        return scores;
    }

    private static BufferedImage[][] loadImages(){
        BufferedImage[][] images = new BufferedImage[14][];
        images[0]= new BufferedImage[3];
        try { images[0][0] = ImageIO.read(new File(imagesPath+"\\pacman1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[0][1] = ImageIO.read(new File(imagesPath+"\\pacman2.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[0][2] = ImageIO.read(new File(imagesPath+"\\pacman3.png")); }
        catch (IOException e) { e.printStackTrace(); }
        images[1] = new BufferedImage[2];
        try { images[1][0] = ImageIO.read(new File(imagesPath+"\\blue.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[1][1] = ImageIO.read(new File(imagesPath+"\\red.png")); }
        catch (IOException e) { e.printStackTrace(); }
        images[2] = new BufferedImage[1];
        try { images[2][0] = ImageIO.read(new File(imagesPath+"\\energy.png")); }
        catch (IOException e) { e.printStackTrace(); }
        images[3] = new BufferedImage[3];
        try { images[3][0] = ImageIO.read(new File(imagesPath+"\\pineapple.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[3][1] = ImageIO.read(new File(imagesPath+"\\apple.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[3][2] = ImageIO.read(new File(imagesPath+"\\strawberry.png")); }
        catch (IOException e) { e.printStackTrace(); }
        images[4] = new BufferedImage[8];
        try { images[4][0] = ImageIO.read(new File(imagesPath+"\\ginky1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][1] = ImageIO.read(new File(imagesPath+"\\inky1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][2] = ImageIO.read(new File(imagesPath+"\\blinky1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][3] = ImageIO.read(new File(imagesPath+"\\blinky2.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][4] = ImageIO.read(new File(imagesPath+"\\inky2.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][6] = ImageIO.read(new File(imagesPath+"\\scaryGhost1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][7] = ImageIO.read(new File(imagesPath+"\\scaryGhost2.png")); }
        catch (IOException e) { e.printStackTrace(); }
        images[5] = new BufferedImage[2];
        try { images[5][0] = ImageIO.read(new File(imagesPath+"\\water.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[5][1] = ImageIO.read(new File(imagesPath+"\\fire.png")); }
        catch (IOException e) { e.printStackTrace(); }
        return images;
    }
}
