package GamePackage.GameObjects;

import GamePackage.Action;
import GamePackage.Controller;
import utilities.Vector2D;

import static GamePackage.Constants.*;

public class PlayerObject extends CharacterObject {

    Controller ctrl;

    boolean pressingButton;

    private static final double ACCELERATION = 5;
    private static final double DECELERATION = 0.985;



    public PlayerObject(Controller c) {
        super(
                new Vector2D(HALF_WIDTH, HALF_HEIGHT),
                new Vector2D()
        );

        this.ctrl = c;

        img = JOE;
        this.objectColour = SAFETY_ORANGE;
    }

    public PlayerObject revive(){
        super.revive(new Vector2D(HALF_WIDTH, HALF_HEIGHT),
                new Vector2D());
        ctrl.noAction();
        return this;
    }

    @Override
    void individualCharacterUpdate() {
        Action currentAction = ctrl.getAction();

        direction = new Vector2D(currentAction.xInput,currentAction.yInput).normalise();

        if (!direction.isNull()) {
            velocity.addScaled(direction, (ACCELERATION / DT));
            setAngle();
        }

        velocity.mult(DECELERATION);

        velocity.capMag(PLAYER_MAX_SPEED);

        pressingButton = currentAction.checkForSpacePress();

    }

    public boolean isTryingToPressAButton(){
        return pressingButton;
    }
}
