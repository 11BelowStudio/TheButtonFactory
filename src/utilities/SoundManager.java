package utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.util.Arrays;
//import java.io.File;


// SoundManager class

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene)

//edited slightly by me

public class SoundManager {


    private static boolean playingMenu = false;
    private static boolean playingGameTheme = false;
    private static boolean playingGameOverlay = false;


    // this may need modifying
    private final static String path = "resourcesPlsNoDelet/sounds/";


    //arrays for clips that may be played multiple times at once
    private final static Clip[] BUTTON_PRESS_ARRAY = new Clip[3];
    private final static Clip[] BUTTON_DECAY_ARRAY = new Clip[6];
    //cursor values for these arrays
    private static int pressCursor = 0;
    private static int decayCursor = 0;

    //actually obtaining the clips
    private final static Clip buttonPressNoise = getClip("clap");
    private final static Clip buttonDecayNoise = getClip("plac");

    private final static Clip gameBackingTrack = getClip("TheMusicWhatPlaysWhenYouAreDoingWell");
    private final static Clip gameOverlayTrack = getClip("placeholderOverlayNoises");

    static{
        Arrays.fill(BUTTON_PRESS_ARRAY,buttonPressNoise);
        Arrays.fill(BUTTON_DECAY_ARRAY,buttonDecayNoise);
    }



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
        /*
        if (!playingMenu){
            menuTheme.loop(-1);
            playingMenu = true;
        }
        */
    }

    public static void stopMenu(){
        /*
        menuTheme.loop(0);
        menuTheme.stop();
        playingMenu = false;
        */
    }

    public static void startGameBacking(){

        if (!playingGameTheme){
            gameBackingTrack.loop(-1);
            playingGameTheme = true;
        }

    }

    //TODO: menu theme
    //TODO: game theme for less than 3 buttons
    //TODO: play backing instead of default game theme if 3 buttons are active
    //TODO: play overlay on top of backing if 4+ buttons are active

    public static void startGameOverlay(){
        if (!playingGameOverlay){
            //gameOverlayTrack.setFramePosition(gameBackingTrack.getFramePosition());
            //gameOverlayTrack.loop(-1);
            //playingGameOverlay = true;
        }

    }

    public static void stopGameBacking(){
        stopGameOverlay();
        gameBackingTrack.loop(0);
        gameBackingTrack.stop();
        playingGameTheme = false;

    }

    public static void stopGameOverlay(){

        gameOverlayTrack.loop(0);
        gameOverlayTrack.stop();
        playingGameOverlay = false;

    }



    //playing a particular sound
    //public static void playButtonPress(){ play(buttonPressNoise); }
    //public static void playButtonDecay() { play(buttonDecayNoise);}


    //playing the clips that are held in an array of Clips
    private static int playClipHeldInArray(Clip[] clipArray, int arrayCursor){
        //play the clip at the position the cursor points to, increment the cursor value, and return it.
        Clip clip = clipArray[arrayCursor];
        clip.setFramePosition(0);
        clip.start();
        return ((arrayCursor + 1) % clipArray.length);
    }

    public static void playButtonPress() {
        pressCursor = playClipHeldInArray(BUTTON_PRESS_ARRAY, pressCursor);
    }

    public static void playButtonDecay(){
        decayCursor = playClipHeldInArray(BUTTON_DECAY_ARRAY, decayCursor);
    }






}