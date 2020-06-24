package GamePackage;

import GamePackage.GameObjects.BackgroundRippleObject;
import GamePackage.GameObjects.StringObject;
import utilities.HighScoreHandler;
import utilities.Vector2D;

import static GamePackage.Constants.HALF_HEIGHT;
import static GamePackage.Constants.HALF_WIDTH;

public class TitleScreen extends Model {


    static final int RIPPLE_CHANCES = 1024;
    int rippleTimer;

    private final StringObject titleText;
    private final StringObject subtitleText;

    private final StringObject play;

    private final StringObject showScores;

    public TitleScreen(Controller ctrl, HighScoreHandler hs) {
        super(ctrl, hs);

        titleText = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT/2),new Vector2D(),"Button Factory",StringObject.MIDDLE_ALIGN,StringObject.BIG_SANS);
        //titleText.kill();

        subtitleText = new StringObject(new Vector2D(HALF_WIDTH,5*(HALF_HEIGHT/8)), new Vector2D(),"The Game",StringObject.MIDDLE_ALIGN,StringObject.MEDIUM_SANS);
        //subtitleText.kill();

        play = new StringObject(new Vector2D(HALF_WIDTH,HALF_HEIGHT),new Vector2D(),"*Play*",StringObject.MIDDLE_ALIGN,StringObject.MEDIUM_SANS);
        //play.kill();

        showScores = new StringObject(new Vector2D(HALF_WIDTH,3*(HALF_HEIGHT/2)),new Vector2D(),"*Show Scores*",StringObject.MIDDLE_ALIGN,StringObject.MEDIUM_SANS);
        //showScores.kill();
    }

    @Override
    public TitleScreen revive() {
        setupModel();
        return this;
    }

    @Override
    void updateLoop() {
        for (BackgroundRippleObject o: backgroundObjects) {
            o.update();
            if (o.stillAlive()){
                aliveBackground.add(o);
            } else{
                ripples.push(o);
            }
        }

        for (StringObject o: hudObjects) {
            o.update();
            if (o.stillAlive()){
                aliveHUD.add(o);
            } else{
                //ripples.push(o);
            }
        }

        if (Math.random()*RIPPLE_CHANCES < rippleTimer && canWeSpawnARipple()){
            aliveBackground.add(ripples.pop().revive());
            rippleTimer = 0;
        } else{
            rippleTimer++;
        }

        if (ctrl.getTheAnyButton()){
            endThis();
        }
    }

    @Override
    void setupModel() {
        rippleTimer = 0;
        for (int i = 0; i < 50; i++) {
            ripples.push(new BackgroundRippleObject());
        }

        aliveHUD.add(titleText);
        aliveHUD.add(subtitleText);
        aliveHUD.add(play);
        aliveHUD.add(showScores);

    }

    /*
    @Override
    void clearCollections(){
        super.clearCollections();

    }*/
}
