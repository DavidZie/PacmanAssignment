package Logic;

import Frames.MainFrame;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Startup {

    private static final String songPath = System.getProperty("user.dir");

    public static void main(String[] args) {

        MainFrame.getInstance().setVisible(true);//Show The Main Frame.
        music();

    }

    private static void music() {
        File clap = new File(songPath + "\\PacManOriginal.wav");
        playSound(clap);
    }

    private static void playSound(File sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
