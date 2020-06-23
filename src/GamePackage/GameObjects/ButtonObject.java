package GamePackage.GameObjects;

import GamePackage.Constants;
import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

public class ButtonObject extends GameObject{


    static final int MIN_MAXPRESSES = 5;
    static final int RANGE_MAXPRESSES = 5;





    int maxPresses;
    int pressesToLive;

    int decayTime;
    int decayRate;

    static final int STANDARD_DECAY = 50;

    private final AttributeStringObject<Integer> buttonLabel;

    public ButtonObject() {
        super(new Vector2D(), new Vector2D());
        radius = OBJ_RADIUS;
        alive = false;
        setupMaxPresses();
        buttonLabel = new AttributeStringObject<>(
                new Vector2D(),
                new Vector2D(),
                "",
                pressesToLive,
                StringObject.MIDDLE_ALIGN
        );
    }

    public ButtonObject revive(Vector2D p, Vector2D v){
        super.revive(p,v);
        setupMaxPresses();
        buttonLabel.revive();
        pressesToLiveChanged();
        return this;
    }


    public ButtonObject revive(Vector2D p){
        return(this.revive(p,new Vector2D()));
    }

    public ButtonObject revive(boolean moving){
        Vector2D pos = randomPosInObjBounds();
        if (moving){
            return(
                this.revive(
                    pos,
                    Vector2D.polar(
                        Math.random()*2*Math.PI,
                        Math.random() * BUTTON_MAX_SPEED
                    )
                )
            );
        } else{
            return (this.revive(pos));
        }
    }

    private void setupMaxPresses(){
        maxPresses = (int)(MIN_MAXPRESSES + (Math.random() * RANGE_MAXPRESSES));
        pressesToLive = maxPresses;
        decayRate = STANDARD_DECAY;
    }

    public void pressed(){
        if (pressesToLive < maxPresses){
            pressesToLive++;
            decayTime = decayRate;
            pressesToLiveChanged();
            SoundManager.playClap();
        }
    }

    private void decay(){
        if (decayTime == 0){
            if (decayRate > 1){
                decayRate--;
            }
            pressesToLive--;
            decayTime = decayRate;
            pressesToLiveChanged();
            SoundManager.playPlac();
        } else{
            decayTime--;
        }
    }

    @Override
    void amIAlive(){
        if (pressesToLive == 0){
            alive = false;
        }
    }

    @Override
    void individualUpdate() {
        decay();
    }

    @Override
    void keepInBounds() {
        //if it's out of bounds on the X axis, keep it within bounds
        if (position.x < OBJ_RADIUS || position.x > OBJ_X_BOUNDS) {
            velocity.invertX(); //inverts X of velocity
            if (position.x < OBJ_RADIUS) {
                position.x = OBJ_RADIUS;
            } else {
                position.x = OBJ_X_BOUNDS;
            }
        }

        //if it's out of bounds on the Y axis, keep it within bounds
        if (position.y < OBJ_RADIUS || position.y > OBJ_Y_BOUNDS){
            velocity.invertY();
            if (position.y < OBJ_RADIUS){
                position.y = OBJ_RADIUS;
            } else{
                position.y = OBJ_Y_BOUNDS;
            }
        }
    }

    private void pressesToLiveChanged(){
        int redScale = (int)((pressesToLive/maxPresses) * 255);
        this.objectColour = new Color(redScale, 255-redScale, 0);
        //green at max presses, fades to red as it gets closer to 0

        buttonLabel.showValue(pressesToLive);
        //buttonLabel shows updated pressesToLive value
    }

    @Override
    void renderObject(Graphics2D g) {
        //draws the button circle
        g.setColor(objectColour);
        int d = radius*2;
        g.fillOval(-radius,-radius,d,d);

        //draws the button label over the button
        buttonLabel.renderObject(g);
    }

    public boolean collideWithPlayer(PlayerObject p){
        if (this.position.dist(p.getPos()) < (this.radius + p.getRadius())){
            this.pressed();
            return true;
        } else{
            return false;
        }
    }
}
