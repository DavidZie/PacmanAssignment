package Frames;

import GameComponents.JPanelWithBackground;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static Logic.Globals.imagesPath;

public class AboutFrame {

    JFrame about;
    JPanel containerPanel;

    public AboutFrame() {

        createFrame();

        JLabel label=new JLabel("PacMan Game");
        label.setFont(new Font("David",Font.BOLD,65));
        containerPanel.add(label);

        JTextArea textArea=new JTextArea(35,30);
        JScrollPane scrollPane=new JScrollPane(textArea);
        String content="Pac-Man , stylized as PAC-MAN, is an arcade game developed by Namco and first released in Japan as Puck Man in May 1980. It was created by Japanese video game designer Toru Iwatani. It was licensed for distribution in the United States by Midway Games and released in October 1980. Immensely popular from its original release to the present day, Pac-Man is considered one of the classics of the medium, and an icon of 1980s popular culture. Upon its release, the game—and, subsequently, Pac-Man derivatives—became a social phenomenon that yielded high sales of merchandise and inspired a legacy in other media, such as the Pac-Man animated television series and the top-ten Buckner and Garcia hit single \"Pac-Man Fever\". Pac-Man was popular in the 1980s and 1990s and is still played in the 2010s.\n" +
                "When Pac-Man was released, the most popular arcade video games were space shooters—in particular, Space Invaders and Asteroids. The most visible minority were sports games that were mostly derivatives of Pong. Pac-Man succeeded by creating a new genre. \n" +
                "Pac-Man is often credited with being a landmark in video game history and is among the most famous arcade games of all time. It is also one of the highest-grossing video games of all time, having generated more than $2.5 billion in quarters by the 1990s.\n" +
                "The character has appeared in more than 30 officially licensed game spin-offs, as well as in numerous unauthorized clones and bootlegs. \n" +
                "According to the Davie-Brown Index, Pac-Man has the highest brand awareness of any video game character among American consumers, recognized by 94 percent of them. \n" +
                "Pac-Man is one of the longest running video game franchises from the golden age of video arcade games. It is part of the collection of the Smithsonian Institution in Washington, D.C. and New York's Museum of Modern Art.\n" +
                "GamePlay:\n" +
                "The player navigates Pac-Man through a maze containing various dots, known as Pac-Dots, and four multi-colored ghosts: Blinky, Pinky, Inky, and Clyde. The goal of the game is to accumulate points by eating all the Pac-Dots in the maze, completing that 'stage' of the game and starting the next stage and maze of Pac-dots. Between some stages, one of three intermission animations plays. The four ghosts roam the maze, trying to kill Pac-Man. If any of the ghosts hit Pac-Man, he loses a life; when all lives have been lost, the game is over. Pac-Man is awarded a single bonus life at 10,000 points by default—DIP switches inside the machine can change the required points to 15,000 or 20,000, or disable the bonus life altogether. \n" +
                "The number of lives can be set to 1 life only or up to five lives maximum. High score cannot exceed 999,990 points; players may exceed that score, but the game keeps the last 6 digits. There are 256 levels in total, however, when the game was made, the memory ran out at 256, so it is only half loaded while the other half is a jumble of letters and digits.\n" +
                "Near the corners of the maze are four larger, flashing dots known as Power Pellets that provide Pac-Man with the temporary ability to eat the ghosts and earn bonus points. The enemies turn deep blue, reverse direction and usually move more slowly. When an enemy is eaten, its eyes remain and return to the center box where the ghost is regenerated in its normal color. Blue enemies flash white to signal that they are about to become dangerous again and the length of time for which the enemies remain vulnerable varies from one stage to the next, generally becoming shorter as the game progresses. In later stages, the enemies go straight to flashing, bypassing blue, which means that they can only be eaten for a short amount of time, although they still reverse direction when a Power Pellet is eaten; starting at stage 19, the ghosts do not become edible (i.e., they do not change color and still make Pac-Man lose a life on contact), but they still reverse direction. There are also fruits, located directly below the center box, that appear twice per level; eating one of them results in bonus points (100-5,000).\n" +
                "Enemies:\n" +
                "The enemies in Pac-Man are known variously as \"monsters\" or \"ghosts\". Despite the seemingly random nature of the enemies, their movements are strictly deterministic, which players have used to their advantage. In an interview, creator Toru Iwatani stated that he had designed each enemy with its own distinct personality in order to keep the game from becoming impossibly difficult or boring to play. More recently, Iwatani described the enemy behaviors in more detail at the 2011 Game Developers Conference. He stated that the red enemy chases Pac-Man, and the pink enemy aims for a position in front of Pac-Man's mouth. The blue enemy is \"fickle\" and sometimes heads towards Pac-Man, and other times away. Although he claimed that the orange enemy's behavior is random, in actuality it alternates from behaving like the red enemy when at some distance from Pac-Man and aiming towards the lower-left corner of the maze whenever it gets too close to him.\n" +
                "Although Midway's 1980 flyer for Pac-Man used both the terms \"monsters\" and \"ghost monsters\", the term \"ghosts\" started to become more popular after technical limitations in the Atari 2600 version caused the antagonists to flicker and seem ghostlike, leading them to be referred to in the manual as \"ghosts\", and they have most frequently been referred to as ghosts in English ever since.\n" +
                "(from wikipedia).";

        textArea.setText(content);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        containerPanel.add(scrollPane);
    }

    private void createFrame() {
        about=new JFrame("About");
        about.setSize(509,735);//Set Frame dimensions.
        about.setResizable(false);//Lock Frame Size.
        about.setVisible(true);//Make Frame Visible.
        about.setLocationRelativeTo(null);//Center the frame on the screen.

        try {
            containerPanel=new JPanelWithBackground(imagesPath+"\\about.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        about.add(containerPanel, BorderLayout.CENTER);//Add Container Panel to Frame.
    }
}
