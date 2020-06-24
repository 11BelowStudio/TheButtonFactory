package GamePackage.GameObjects;

import utilities.Vector2D;

import static GamePackage.Constants.HALF_WIDTH;
import static GamePackage.Constants.QUARTER_HEIGHT;

public class BossObject extends CharacterObject {

    //WHAT THIS GUY DOES

    //Appears, tells you to press another button (via inherited 'speak()' method), disappears when done talking
    //Does not interact with any game objects.
    //literally fucking evil


    public BossObject() {
        super(new Vector2D(HALF_WIDTH, QUARTER_HEIGHT), new Vector2D());
        objectColour = SAFETY_PURPLE;
        img = BOSS;
        alive = false;
    }

    @Override
    public BossObject revive(){
        super.revive(new Vector2D(HALF_WIDTH, QUARTER_HEIGHT), new Vector2D());
        return this;
    }

    public void begone(){
        alive = false;
    }

    @Override
    void individualCharacterUpdate() {

    }
}
