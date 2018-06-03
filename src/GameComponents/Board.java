package GameComponents;

import GameComponents.Players.*;
import Logic.Drawings;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static Logic.Drawings.*;
import static Logic.Globals.*;

public class Board extends JPanel {
    private Piece[][] pieces;
    private Pacman pacman;
    private Timer timer;
    private Stack eatenFruits;
    private int timerRepeats;
    private boolean pauseStatus;
    private boolean speedActivated;
    private int speedDivisor;
    private int currentScore;
    private int currentHighScore;
    private int pills;
    private Fruit[] fruits;
    private int level;
    private Ghost[] ghosts;
    private int lives;
    private int[] gate;


    public Board(Stack[][] board, int level, int currentHighScore, int lives, int currentScore) {
        super(new GridBagLayout());
        this.level=level;
        this.currentHighScore=currentHighScore;
        this.currentScore = currentScore;
        this.lives = lives;
        pills=0;
        eatenFruits=new Stack();
        createBoard(board);
        drawInfo();
        prepareFruits();
        prepareGhosts();//TO BE WRITTEN.
        timerSetup();
        //replaceLabels(10,15,1,1);

    }//Constructor
    //----------------Board Initiation----------------------//
    private void createBoard(Stack[][] board) {
        pacman = new Pacman();
        setBorder(new LineBorder(Color.GREEN));
        pieces = new Piece[boardSize][boardSize];
        GridBagConstraints constraints = new GridBagConstraints();
        for (int i = 0; i < boardSize; i++) {
            constraints.gridy = i;
            for (int j = 0; j < boardSize; j++) {
                constraints.gridx = j;
                if ((int) board[i][j].peek() == 0||(int) board[i][j].peek() == 6)
                    pills++;
                if ((int) board[i][j].peek() == 7 && pacman.getLocation() == null) {
                    int[] playerLocation = {i-1,j};
                    pacman.setLocation(playerLocation);
                    gate = new int[2];
                    gate[0]=i;
                    gate[1]=j;
                }
                pieces[i][j] = new Piece(i, j, board[i][j]);
                add(pieces[i][j], constraints);
            }
        }
    }
    //------------------------Board Initiation END--------------------------------------//

    //-----------------------Getters and Setters----------------//

    public boolean isPauseStatus() {
        return pauseStatus;
    }

    public Ghost[] getGhosts() {
        return ghosts;
    }

    public void setGhosts(Ghost[] ghosts) {
        this.ghosts = ghosts;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setPauseStatus(boolean pauseStatus) {
        this.pauseStatus = pauseStatus;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isSpeedActivated() {
        return speedActivated;
    }

    public void setSpeedActivated(boolean speedActivated) {
        this.speedActivated = speedActivated;
    }

    public int getTimerRepeats() {
        return timerRepeats;
    }

    public void setTimerRepeats(int timerRepeats) {
        this.timerRepeats = timerRepeats;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getCurrentHighScore() {
        return currentHighScore;
    }

    //--------------------------Methods--------------------------//

    public Piece replaceLabels(int x, int y, int width, int height) {
        Piece newPiece = new Piece(x, y, null);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = y;
        constraints.gridy = x;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                remove(pieces[x + j][y + i]);
                pieces[x + j][y + i] = newPiece;
            }
        }
        constraints.gridwidth = width;
        constraints.gridheight = height;
        newPiece.setSize(width * pieceSize, height * pieceSize);
        newPiece.setImage(new BufferedImage(width * pieceSize, height * pieceSize, BufferedImage.TYPE_INT_ARGB));
        add(newPiece, constraints);
        return newPiece;
    }

    private void timerSetup() {
        timerRepeats=0;
        timer = new Timer(250, e -> {
            timerRepeats++;
            if (!speedActivated)
                speedDivisor = 4;
            else speedDivisor = 8;
            if (timerRepeats % speedDivisor == 0) {
                Drawings.drawTime(timerRepeats / speedDivisor,pieces);
                if ((timerRepeats / speedDivisor)%10==0) {
                    insertFruits();
                }
            }

            if (((timerRepeats / speedDivisor)>=7)) {
                ghosts[0].getTimer().start();
            }

            if (((timerRepeats / speedDivisor)>=9)){
                ghosts[1].getTimer().start();
            }

            if (((timerRepeats / speedDivisor)>=11)){
                ghosts[2].getTimer().start();
            }

            if (((timerRepeats / speedDivisor)==15)){
                pieces[gate[0]][gate[1]].setWall(true);
            }


            if (ghosts[1].isLoaded())
                fire(ghosts[1]);

            if (ghosts[2].isLoaded())
                fire(ghosts[2]);

            checkKill();


        });
        timer.start();
    }

    public void drawBlack(Piece piece) {
        BufferedImage blackImage = new BufferedImage(piece.getWidth(), piece.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = blackImage.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, piece.getWidth(), piece.getHeight());
        piece.setImage(blackImage);
    }

    public void updateScore(Piece piece){
        if (!piece.isEaten()){
            currentScore+=piece.getWorth();
            if (piece.getFruit()==null)
                pills--;
            else {
                if (piece.getWorth()-piece.getFruit().getWorth()!=0)
                    pills--;
            }

            piece.setEaten(true);
            Drawings.reDrawScoreLabel(pieces[22][7],currentScore,currentHighScore,pieces);
        }
    }

    //-----------------------First Draw Methods------------------//

    private void drawInfo() {
        swapIn();
        drawGate(pieces[pacman.getLocation()[0] + 1][pacman.getLocation()[1]]);
        drawLife(pieces,lives);
        drawTimeLabel(this);
        drawScoreLabel(this);
        drawHighScoreLabel(this);
        drawPauseButton(this);
        drawSpeedLabel(this);
        drawTime(0,pieces);
        drawFruitsLabel(this);
    }

    private void swapIn() {
        pieces[pacman.getLocation()[0]][pacman.getLocation()[1]].setWorth(0);
        pieces[pacman.getLocation()[0]][pacman.getLocation()[1]].getImage().getGraphics().drawImage(pacman.getImage(),0,0,null);//setImage(pacman.getImage());
    }



    //---------------------First Draw Methods END-----------------------//
    //--------------------- re-draw Methods-----------------------//

    //--------------------- re-draw Methods END-----------------------//
    //------------------------Level Initiation--------------------------------//



    //-----------------------Level Initiation END---------------------------//
    //------------------------Fruits-----------------------------//

    private void insertFruits(){
        Piece piece;
        Fruit fruit;
        int x,y;
        int index;
        int num=1;
        while (num!=0){
            x = (int)(Math.random() * 19 + 2);
            y = (int)(Math.random() * 22 + 6);
            piece = pieces[x][y];
            if (!piece.isWall()&&!(x==pacman.getLocation()[0]&&y==pacman.getLocation()[1])&&checkCell(x,y)){
                index = (int)(Math.random()*fruits.length);
                if (fruits[index].isOut()){
                    index = 0;
                    while (index<fruits.length&&fruits[index].isOut()){
                        index++;
                    }
                }
                if (index==fruits.length)
                    return;

                fruit = fruits[index];
                piece.addFruit(fruit);
                piece.setEaten(false);
                fruit.setOut(true);
                piece.repaint();
                num--;
            }
        }


    }

    private void prepareFruits(){
        switch (level){
            case 1:
                fruits = new Fruit[4];
                fruits[0] = new Fruit(0);
                fruits[1] = new Fruit(1);
                fruits[2] = new Fruit(0);
                fruits[3] = new Fruit(1);
                break;
            case 2:
                fruits = new Fruit[9];
                fruits[0] = new Fruit(0);
                fruits[1] = new Fruit(1);
                fruits[2] = new Fruit(0);
                fruits[3] = new Fruit(1);
                fruits[4] = new Fruit(0);
                fruits[5] = new Fruit(1);
                fruits[6] = new Fruit(0);
                fruits[7] = new Fruit(1);
                fruits[8] = new Fruit(2);
                break;
            case 3:
                fruits = new Fruit[12];
                fruits[0] = new Fruit(0);
                fruits[1] = new Fruit(1);
                fruits[2] = new Fruit(0);
                fruits[3] = new Fruit(1);
                fruits[4] = new Fruit(0);
                fruits[5] = new Fruit(1);
                fruits[6] = new Fruit(0);
                fruits[7] = new Fruit(1);
                fruits[8] = new Fruit(0);
                fruits[9] = new Fruit(1);
                fruits[10] = new Fruit(2);
                fruits[11] = new Fruit(2);
                break;
        }
    }

    //-------------------------Ghosts-----------------------------//

    private void prepareGhosts(){
        ghosts = new Ghost[5];
        ghosts[0] = new Ginky();
        ghosts[1] = new Inky();
        ghosts[2] = new Blinky();
        ghosts[0].setLocation(12,16);
        ghosts[1].setLocation(12,17);
        ghosts[2].setLocation(12,15);

        ghosts[0].insert(pieces[12][16]);
        ghosts[1].insert(pieces[12][17]);
        ghosts[2].insert(pieces[12][15]);
    }

    //-----------------------------Movement----------------------//



    private boolean checkCell(int x,int y){
        if (pieces[x][y].getFruit()!=null)
            return false;
        for (int i=0;i<3;i++){
            if (ghosts[i].getLocation()[0]==x&&ghosts[i].getLocation()[1]==y)
                return false;
        }
        return true;
    }

    private void fire(Ghost ghost){
        ghosts[ghost.getId()+2] = ghost.getWeapon();
        ghost.fire(pieces);
    }

    private void checkKill(){
        int myX,myY;
        for (int i=0;i<5;i++){
            if (ghosts[i]!=null){
                myX = ghosts[i].getLocation()[0];
                myY = ghosts[i].getLocation()[1];
                if (pacman.getLocation()[0] == myX && pacman.getLocation()[1] == myY){
                    lives--;
                    if (lives==0){
                        gameFrame.endGame();
                    } else cleanBoard();
                }
            }
        }
    }

    private void cleanBoard(){

        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                if (!pieces[i][j].isWall())
                    if (pieces[i][j].getFruit()!=null)
                        pieces[i][j].killFruit();
            }
        }

        for (int i=0;i<5;i++){
            if (ghosts[i]!=null){
                ghosts[i].getTimer().stop();
                pieces[ghosts[i].getLocation()[0]][ghosts[i].getLocation()[1]].setImage(ghosts[i].getCoveredImage());

            }
        }
        ghosts=null;

        drawBlack(pieces[pacman.getLocation()[0]][pacman.getLocation()[1]]);
        pieces[gate[0]][gate[1]].setWall(false);
        int[] reset = {10,16};
        pacman.setLocation(reset);
        swapIn();
        drawLife(pieces,lives);
        repaint();
        prepareGhosts();
        timerRepeats = 0;
    }

    public void start(){
        for (int i=0;i<3;i++){
            ghosts[i].getTimer().start();
        }
        timer.stop();
    }

    public void stop(){

        for (int i=0;i<3;i++){
            ghosts[i].getTimer().stop();
        }
        timer.stop();
    }
    public void speedUp(){
        for (int i=0;i<3;i++){
            ghosts[i].getTimer().setDelay(ghosts[i].getTimer().getDelay()/2);
            ghosts[i].setRepeats(ghosts[i].getRepeats()*2);
        }
        timer.setDelay(timer.getDelay()/2);
        timerRepeats = timerRepeats*2;
        speedActivated=true;
    }


    public void speedDown(){
        for (int i=0;i<3;i++){
            ghosts[i].getTimer().setDelay(ghosts[i].getTimer().getDelay()*2);
            ghosts[i].setRepeats(ghosts[i].getRepeats()/2);
        }
        timer.setDelay(timer.getDelay()*2);
        timerRepeats = timerRepeats/2;
        speedActivated=false;
    }

    public void eatenFruit(Fruit fruit){
        eatenFruits.push(fruit);
        reDrawFruitsLabel(this,eatenFruits.size(), fruit);
    }

    public void checkCompletion(){
        if (pills==0){
            gameFrame.finishBoard();
        }
    }

}
