package utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
//import java.io.File;


// SoundManager class

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene)

//edited slightly by me

public class SoundManager {


    private static boolean playingMenu = false;
    private static boolean playingGameTheme = false;


    // this may need modifying
    private final static String path = "sounds/";

    private final static Clip gameTheme = getClip("SkyJumpGameMusic");
    private final static Clip menuTheme = getClip("SkyJumpIntro");
    private final static Clip clap = getClip("clap");
    private final static Clip plac = getClip("plac");
    private final static Clip eatingNoise = getClip("eatingNoise");
    private final static Clip crunch = getClip("crunch");
    private final static Clip nice = getClip("nice");



    // methods which do not modify any fields

    private static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }


    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path + filename + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }


    public static void startMenu(){
        if (!playingMenu){
            menuTheme.loop(-1);
            playingMenu = true;
        }
    }

    public static void stopMenu(){
        menuTheme.loop(0);
        menuTheme.stop();
        playingMenu = false;
    }

    public static void startGame(){
        if (!playingGameTheme){
            gameTheme.loop(-1);
            playingGameTheme = true;
        }
    }

    public static void stopGame(){
        gameTheme.loop(0);
        gameTheme.stop();
        playingGameTheme = false;
    }

    //playing a particular sound
    public static void playClap(){ play(clap); }
    public static void playPlac() { play(plac);}
    public static void playEat(){ play(eatingNoise); }

    public static void playCrunch(){ play(crunch); }

    public static void playNice(){ play(nice); }






}