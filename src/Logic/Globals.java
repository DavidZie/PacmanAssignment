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
    public static final BufferedImage[][] gameImagesArray = loadImages();
    public static final Object[]gameBoards = gameBoardsArrayCreator();
    public static int[][] highScoresArray = {{1000, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    public static final GameFrame gameFrame = new GameFrame();



    //--------------Variables Generators--------------------//

    private static Object[] gameBoardsArrayCreator(){
        Object[] array = new Object[3];
        for (int i=0; i<3;i++){
            array[i] = gameBoardsCreator(i+1);
        }
        return array;
    }

    private static Stack[][] gameBoardsCreator(int index) {
        Stack[][] cellsInfo = new Stack[boardSize][boardSize];
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
        images[4] = new BufferedImage[3];
        try { images[4][0] = ImageIO.read(new File(imagesPath+"\\ginky1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][1] = ImageIO.read(new File(imagesPath+"\\YellowGost1.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[4][2] = ImageIO.read(new File(imagesPath+"\\BlinkyRed1.jpg")); }
        catch (IOException e) { e.printStackTrace(); }
        images[5] = new BufferedImage[2];
        try { images[5][0] = ImageIO.read(new File(imagesPath+"\\water.png")); }
        catch (IOException e) { e.printStackTrace(); }
        try { images[5][0] = ImageIO.read(new File(imagesPath+"\\fire.png")); }
        catch (IOException e) { e.printStackTrace(); }
        return images;
    }
}
