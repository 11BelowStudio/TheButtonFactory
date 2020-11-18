package GamePackage;

import GamePackage.GameObjects.*;
import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static GamePackage.Constants.GAME_HEIGHT;
import static GamePackage.Constants.GAME_WIDTH;

public abstract class Model {

    final List<StringObject> hudObjects;
    final List<CharacterObject> characterObjects;
    final List<ButtonObject> buttonObjects;
    final List<BackgroundRippleObject> backgroundObjects;


    final List<StringObject> aliveHUD;
    final List<CharacterObject> aliveCharacters;
    final List<ButtonObject> aliveButtonObjects;
    final List<BackgroundRippleObject> aliveBackground;


    final Stack<BackgroundRippleObject> ripples;


    Color backgroundColor;
    Rectangle backgroundRect;

    boolean gameOver;

    boolean stopThat;

    Controller ctrl;

    HighScoreHandler hs;



    //Sky blue: 94bfac
    static Color SKYBLUE =new Color(48, 191, 172);
    //night: 282b2f
    static Color NIGHT = new Color(40, 43, 47);
    //sunrise: cfb48a
    static Color SUNRISE = new Color(207, 180, 138);
    //sunset pink: e3bbbd
    static Color SUNSET = new Color(227, 187, 189);

    //w3schools camo grey: 9495a5
    static Color W3_CAMO_GREY = new Color(148, 149, 165);
    //w3schools BS 381 (381 642) night: #282b2f
    static Color W3_NIGHT = new Color(40, 43, 47);
    //w3schools BS 4800 (20-C-40) midnight: #29374b
    static Color W3_MIDNIGHT = new Color(41, 55, 75);


    Vector2D topLeftCorner;


    public Model(Controller ctrl, HighScoreHandler hs){

        topLeftCorner = new Vector2D(0,0);

        hudObjects = new ArrayList<>();
        characterObjects = new ArrayList<>();
        buttonObjects = new ArrayList<>();
        backgroundObjects = new ArrayList<>();

        aliveHUD = new ArrayList<>();
        aliveCharacters = new ArrayList<>();
        aliveButtonObjects = new ArrayList<>();
        aliveBackground = new ArrayList<>();

        ripples = new Stack<>();

        backgroundRect = new Rectangle(0,0,GAME_WIDTH, GAME_HEIGHT);

        gameOver = false;
        stopThat = false;
        backgroundColor = W3_NIGHT;
        this.hs = hs;
        this.ctrl = ctrl;
    }

    public void draw(Graphics2D g){
        backgroundRect = new Rectangle(0,0,GAME_WIDTH, GAME_HEIGHT);
        GameObject.UPDATE_X_Y_BOUNDS();

        g.setColor(backgroundColor);
        g.fill(backgroundRect);

        AffineTransform backup = g.getTransform();


        synchronized (Model.class) {

            g.translate(topLeftCorner.x,topLeftCorner.y);

            for (GameObject o: backgroundObjects){
                o.draw(g);
                //draws background objects
            }
            for (GameObject o : buttonObjects) {
                o.draw(g);
                //draws buttons
            }
            for (GameObject o : characterObjects){
                o.draw(g);
                //draws each character
            }
            for (GameObject o : hudObjects) {
                o.draw(g);
                //and then the HUD (so its displayed above the game objects)
            }
        }

        g.setTransform(backup);
    }

    void refreshLists(){
        synchronized (Model.class) {
            topLeftCorner.add(ctrl.action.xScroll,ctrl.action.yScroll);

            backgroundObjects.clear();
            backgroundObjects.addAll(aliveBackground);


            buttonObjects.clear();
            buttonObjects.addAll(aliveButtonObjects);

            characterObjects.clear();
            characterObjects.addAll(aliveCharacters);

            hudObjects.clear();
            hudObjects.addAll(aliveHUD);
        }
        aliveBackground.clear();
        aliveButtonObjects.clear();
        aliveCharacters.clear();
        aliveHUD.clear();
    }

    void endThis(){
        stopThat = true;
        stopModelMusic();
        clearCollections();
    }

    abstract void startModelMusic();

    abstract void stopModelMusic();

    public Model revive(){
        topLeftCorner = new Vector2D(0,0);
        this.gameOver = false;
        this.stopThat = false;
        setupModel();
        startModelMusic();
        return this;
    }

    public void update(){
        updateLoop();
        refreshLists();
    }

    abstract void updateLoop();

    public boolean keepGoing(){
        return !stopThat;
    }

    abstract void setupModel();

    void clearCollections(){
        refreshLists();

        backgroundObjects.clear();
        buttonObjects.clear();
        characterObjects.clear();
        hudObjects.clear();

        ripples.clear();
    }

    boolean canWeSpawnARipple(){
        return (!ripples.isEmpty());
    }

    public void updateBackgroundRectangle(){

        backgroundRect = new Rectangle(0,0,GAME_WIDTH, GAME_HEIGHT);
        GameObject.UPDATE_X_Y_BOUNDS();
    }

}
