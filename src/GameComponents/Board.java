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
    private Piece[][] pieces;//Board Piece.
    private Pacman pacman;//Pacman.
    private Timer timer;//Timer for executing actions.
    private Stack<Fruit> eatenFruits;//Stack of eaten fruits. Used to draw Eaten fruits on board.
    private int timerRepeats;//Timer ticker.
    private boolean pauseStatus;//Is game Paused?
    private boolean speedActivated;//is Speed Activated?
    private int speedDivisor;//Used to make time calculations when delay time changes (speed up).
    private int currentScore[];//Save Current Score and info about eaten pills/Fruits.
    private int currentHighScore;//Current Highest scoring user.
    private Fruit[] fruits;//All the Fruits avalable on Current Board.
    private int level;//Current level.
    private Ghost[] ghosts;//All the Game Ghosts.
    private int lives;//Lives left.
    private int id;//Board id.

    public Board(int id, int level, int currentHighScore, int lives, int[] currentScore) {
        super(new GridBagLayout());
        this.id=id;
        this.level=level;
        if (level!=1) {
            ghosts=null;
        }//If level isn't 1. make sure Ghosts were nullified as testing proved there might be a problem with it.
        this.currentHighScore=currentHighScore;
        this.currentScore = currentScore;
        this.lives = lives;
        pauseStatus=true;
        eatenFruits=new Stack<>();
        createBoard((String[][]) gameBoards[id]);//Create Game Board According to board id.
        drawInfo();//Draw Labels.
        prepareFruits();//Prepare Fruits for this level.
        prepareGhosts();//Prepare Ghosts for this level.
        timerSetup();//Setup Board Timer.
        stop();//Make sure to Stop All running Timers to be safe.


    }//Constructor
    //----------------Board Initiation----------------------//
    //Very important therefore appearing before getters and setters.
    private void createBoard(String[][] board) {
        pacman = new Pacman(level);
        setBorder(new LineBorder(Color.GREEN));
        pieces = new Piece[boardSize][boardSize];
        GridBagConstraints constraints = new GridBagConstraints();
        for (int i = 0; i < boardSize; i++) {
            constraints.gridy = i;
            for (int j = 0; j < boardSize; j++) {
                constraints.gridx = j;
                if (board[i][j].equals("8")) {
                    int[] playerLocation = {i,j};
                    pacman.setLocation(playerLocation);

                }
                pieces[i][j] = new Piece(board[i][j]);
                add(pieces[i][j], constraints);
            }
        }
    }//Create Level Pieces and Assign each Piece it's role in the game according to the CSV file.
    //------------------------Board Initiation END--------------------------------------//

    //-----------------------Getters and Setters----------------//


    public int getLevel() {
        return level;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isPauseStatus() {
        return pauseStatus;
    }

    public Ghost[] getGhosts() {
        return ghosts;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setCurrentScore(int[] currentScore) {
        this.currentScore = currentScore;
    }

    public void setPauseStatus(boolean pauseStatus) {
        this.pauseStatus = pauseStatus;
    }

    public boolean isSpeedActivated() {
        return speedActivated;
    }

    public void setSpeedActivated(boolean speedActivated) {
        this.speedActivated = speedActivated;
    }


    public int[] getCurrentScore() {
        return currentScore;
    }

    public int getCurrentHighScore() {
        return currentHighScore;
    }

    //--------------------------Timer--------------------------//

    private void timerSetup() {
        timerRepeats=0;
        timer = new Timer(250, e -> {
            if (!speedActivated)//Speed Activated Changes Divisor value as it affects the game clock.
                speedDivisor = 4;
            else speedDivisor = 8;
            if (timerRepeats % speedDivisor == 0) {
                Drawings.drawTime(timerRepeats / speedDivisor,pieces);
                if ((timerRepeats / speedDivisor)%10==0) {
                    insertFruits();//Try to Insert Fruit every 10 seconds.
                }
            }

            if (level>1&&ghosts[1].isLoaded())//Inky Can Fire only on levels 2 or 3.
                fire(ghosts[1]);

            if (level>2&&ghosts[2].isLoaded())//Blinky can fire only on level 3.
                fire(ghosts[2]);
            timerRepeats++;//Ticks.
            checkCompletion();//Check if Board is Completed.
            for (int j=6;j<27;j++){
                cleanGarbage(j,2+timerRepeats%21);
            }//Every Tick Redraw row of cells (Excluding Cells with Fruits, Ghosts or pacman) to avoid overwriting existing pills.
        });
    }
//-----------------------First Draw Methods------------------//

    private void drawInfo() {
        swapIn();//Swap the pacman into the Board.
        drawGate(pieces[pacman.getLocation()[0] + 1][pacman.getLocation()[1]]);//Draw Ghost cage Gate.
        drawLife(pieces,lives);//Draw number of lives left.
        drawTimeLabel(this);//Draw Time Label.
        drawScoreLabel(this);//Draw Score Label.
        drawHighScoreLabel(this);//Draw High Score Label.
        drawPauseButton(this);//Draw Pause Label.
        drawSpeedLabel(this);//Draw Speed Label.
        drawTime(0,pieces);//Update Time Label with current Time.
        drawFruitsLabel(this);//Draw Eaten Fruits Label.
        drawGhostsAddLabel(this);//Draw Ghosts add label (BONUS).
        mainMenuLabel(this);//Draw Return to Main Menu Label.
        fruitCounterPiece(this);
        drawLevelPiece(this);
    }//Draw Info Surrounding the board.

    private void swapIn() {
        pieces[pacman.getLocation()[0]][pacman.getLocation()[1]].setWorth(0);
        pieces[pacman.getLocation()[0]][pacman.getLocation()[1]].getImage().getGraphics().drawImage(pacman.getImage(),0,0,null);//setImage(pacman.getImage());
    }//Swap Pacman into location as recognized on CSV by board Construction.

    //---------------------First Draw Methods END-----------------------//
    public Piece replaceLabels(int x, int y, int width, int height) {
        Piece newPiece = new Piece(null);
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
    }//Replace labels starting at given x,y and right and down with one label. used for adding game info around the board easily.



    public void drawBlack(Piece piece) {
        BufferedImage blackImage = new BufferedImage(piece.getWidth(), piece.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = blackImage.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, piece.getWidth(), piece.getHeight());
        piece.setImage(blackImage);
    }//Take a piece and draw it black.

    public void updateScore(Piece piece){
        if (piece.isEaten()){
            currentScore[0]+=piece.getWorth();
            switch (piece.getWorth()){
                case 10:
                    currentScore[1]++;
                    break;
                case 50:
                    currentScore[2]++;
                    break;
                case 100:
                    currentScore[3]++;
                    break;
                case 110:
                    currentScore[3]++;
                    currentScore[1]++;
                    break;
                case 200:
                    currentScore[4]++;
                    break;
                case 210:
                    currentScore[4]++;
                    currentScore[1]++;
                    break;
                case 300:
                    currentScore[5]++;
                    break;
                case 310:
                    currentScore[5]++;
                    currentScore[1]++;
                    break;
            }
            piece.setEaten(true);
            Drawings.reDrawScoreLabel(pieces[22][7],currentScore[0],currentHighScore,pieces);
        }
    }//Update Score if a piece is eaten according to what was eaten.
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
            if (!piece.isWall()&&!piece.isGhostHouse()&&!(x==pacman.getLocation()[0]&&y==pacman.getLocation()[1])&&checkCell(x,y)&&piece.getWorth()!=50){
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
                fruit.setX(x);
                fruit.setY(y);
                piece.setEaten(false);
                fruit.setOut(true);
                piece.repaint();
                num--;
            }
        }//Randomly insert Fruit on Board only if there's no wall on piece, not inside Ghosts cage, not on occupied Piece (Will Cause Images Errors).

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
    }//Initiate the needed fruit for the level.

    //-------------------------Ghosts-----------------------------//

    private void prepareGhosts(){
        ghosts = new Ghost[7];
        ghosts[0] = new Ginky();
        ghosts[1] = new Inky(level);
        ghosts[2] = new Blinky(level);
        ghosts[0].setLocation(12,16);
        ghosts[1].setLocation(12,17);
        ghosts[2].setLocation(12,15);

        ghosts[0].insert(pieces[12][16]);
        ghosts[1].insert(pieces[12][17]);
        ghosts[2].insert(pieces[12][15]);
    }//Initiate the Basic Ghosts and insert them into Ghost Cage.

    private boolean checkCell(int x,int y){
        if (pieces[x][y].getFruit()!=null)
            return false;
        for (int i=0;i<7;i++) {
            if (ghosts[i] != null) {
                if (ghosts[i].getLocation()[0] == x && ghosts[i].getLocation()[1] == y)
                    return false;
            }
        }
        return true;
    }//Check if a cell at x,y is NOT occupied.

    private void fire(Ghost ghost){
        ghosts[ghost.getId()+2] = ghost.getWeapon();
        ghost.fire(pieces);
    }//Fire ghost's Weapon.

    public void checkImpact(){
        int myX,myY;
        for (int i=0;i<7;i++){
            if (ghosts[i]!=null){
                myX = ghosts[i].getLocation()[0];
                myY = ghosts[i].getLocation()[1];
                if (pacman.getLocation()[0] == myX && pacman.getLocation()[1] == myY){
                    pacman.attack(ghosts[i]);
                    return;
                }
            }
        }
    }//Check if 2 players are on the same tile and start Visitor Pattern if necessary.

    public void cleanBoard(){

        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                if (!pieces[i][j].isWall())
                    if (pieces[i][j].getFruit()!=null)
                        pieces[i][j].killFruit();
            }
        }

        for (int i=0;i<7;i++){
            if (ghosts[i]!=null){
                ghosts[i].getTimer().stop();
                pieces[ghosts[i].getLocation()[0]][ghosts[i].getLocation()[1]].setImage(ghosts[i].getCoveredImage());

            }
        }
        ghosts=null;

        drawBlack(pieces[pacman.getLocation()[0]][pacman.getLocation()[1]]);
        speedActivated=false;
        drawGhostsAddLabel(this);
        drawSpeedLabel(this);
        int[] reset = {10,16};
        pacman=new Pacman(level);
        pacman.setLocation(reset);
        swapIn();
        drawLife(pieces,lives);
        prepareGhosts();
        Drawings.drawPauseButton(this);
        gameFrame.getGlass().setVisible(true);
        stop();
        timerRepeats = 0;
        drawTime(0,pieces);
        pauseStatus=true;
    }//After a round is over Stop Timers and Clean the Board for next use.

    public void start(){
        for (int i=0;i<7;i++){
            try{ghosts[i].getTimer().start();}
            catch (NullPointerException ignored){}
        }
        timer.start();
        for (Fruit fruit : fruits) {
            try {
                if (fruit.isOut()) {
                    pieces[fruit.getX()][fruit.getY()].getFruitTimer().start();
                }
            } catch (NullPointerException ignored) {
            }
        }
    }//Start all Timers (Except Kill/Death Timers).

    public void stop(){

        for (int i=0;i<7;i++){
            try{ghosts[i].getTimer().stop();}
            catch (NullPointerException ignored){}
        }
        timer.stop();
        for (Fruit fruit : fruits) {
            try {
                pieces[fruit.getX()][fruit.getY()].getFruitTimer().stop();
            } catch (NullPointerException ignored) {
            }
        }
    }//Stop all Timers (Except Kill/Death Timers).

    public void speedUp(){
        for (int i=0;i<7;i++){
            try{ghosts[i].getTimer().setDelay(ghosts[i].getTimer().getDelay()/2);
            ghosts[i].setRepeats(ghosts[i].getRepeats()*2);}
            catch (NullPointerException ignored){}

        }
        timer.setDelay(timer.getDelay()/2);
        timerRepeats = timerRepeats*2;
        speedActivated=true;
    }//Speed up all Timers (Except Kill/Death Timers).


    public void speedDown(){
        for (int i=0;i<7;i++){
            try{ghosts[i].getTimer().setDelay(ghosts[i].getTimer().getDelay()*2);
            ghosts[i].setRepeats(ghosts[i].getRepeats()/2);
            switch (i){
                case 0:
                    if (ghosts[i].getRepeats()*2>22&&ghosts[i].getRepeats()<22)
                        ghosts[i].setRepeats(22);
                    break;
                case 1:
                    if (ghosts[i].getRepeats()*2>16&&ghosts[i].getRepeats()<16)
                        ghosts[i].setRepeats(16);
                    break;
                case 2:
                    if (ghosts[i].getRepeats()*2>14&&ghosts[i].getRepeats()<14)
                        ghosts[i].setRepeats(14);
                    break;
                }//Avoid Freezing Ghosts if Speeding down caused timer to get below Cage Exit Time.
            }
            catch (NullPointerException ignored){}
        }
        timer.setDelay(timer.getDelay()*2);
        timerRepeats = timerRepeats/2;
        speedActivated=false;
    }//Slow down all Timers (Except Kill/Death Timers).

    public void eatenFruit(Fruit fruit){
        eatenFruits.push(fruit);
        reDrawFruitsLabel(this,eatenFruits.size(), fruit);
    }//Update Board when a fruit has been eaten.

    private void checkCompletion(){
        for (int i=0;i<32;i++){
            for (int j=0;j<32; j++){
                if (pieces[i][j].isEaten() &&pieces[i][j].getFruit()==null) {
                    if (checkCell(i,j))
                        pieces[i][j].reDrawMe();
                    return;
                }
            }
        }
        stop();
        getGraphics().dispose();
        gameFrame.finishBoard(id, lives, level, currentScore);
    }//Check if All pills were Eaten.

    public void cleanGarbage(int rowIndex, int colIndex){
        if (!pieces[colIndex][rowIndex].isWall()&&!pieces[colIndex][rowIndex].isGhostHouse()&&checkCell(colIndex,rowIndex)&&pacman.getLocation()[0]!=colIndex&&pacman.getLocation()[1]!=rowIndex){
            pieces[colIndex][rowIndex].reDrawMe();
        }
    }//Redraw row  cell (Unless Cell has a Fruit, ghost or pacman on it) to avoid overwriting existing pills.


    public void addExtraGhost(int id){
        ghosts[id] = new ExtraGhost(id,pieces[12][16].getImage());
        ghosts[id].setLocation(12,16);
        ghosts[id].insert(pieces[12][16]);
        Graphics g =pieces[12][16].getImage().getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, getHeight() - 1, getWidth(), 2);
        pieces[12][16].repaint();
    }//Add Extra Ghost (BONUS).



}
