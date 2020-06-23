package GamePackage.GameObjects;

import utilities.Vector2D;

import static GamePackage.Constants.HALF_WIDTH;
import static GamePackage.Constants.QUARTER_HEIGHT;

public class BossObject extends CharacterObject {


    public BossObject() {
        super(new Vector2D(HALF_WIDTH, QUARTER_HEIGHT), new Vector2D());
    }

    @Override
    void individualCharacterUpdate() {

    }
}
