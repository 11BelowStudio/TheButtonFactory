package GamePackage.GameObjects;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

public class ButtonObject extends GameObject{


    private static final int MIN_MAXPRESSES = 10;
    private static final int RANGE_MAXPRESSES = 20;


    private static final double MIN_DIST_FROM_OTHER = 100;
    private static final double RANGE_DIST_FROM_OTHER = 300;

    private static final double MOVEMENT_CHANCE = 0.6;



    int maxPresses;
    int pressesToLive;

    int decayTime;
    int decayRate;

    double buttonValue;
    private static final double DEFAULT_BUTTON_VALUE = 1;
    private static final double MIN_BUTTON_VALUE = 0.1;
    private static final double BUTTON_DEVALUATION_RATE = 0.9;

    private static final int STANDARD_DECAY = 50;

    private final AttributeStringObject<Integer> buttonLabel;

    public ButtonObject() {
        super(new Vector2D(), new Vector2D());
        radius = OBJ_RADIUS;
        alive = false;
        setupMaxPresses(getRandomMaxPresses());
        buttonLabel = new AttributeStringObject<>(
                new Vector2D(),
                new Vector2D(),
                "",
                pressesToLive,
                StringObject.MIDDLE_ALIGN
        );
    }

    public ButtonObject revive(Vector2D p, Vector2D v){
        return this.revive(p,v, getRandomMaxPresses());

    }

    public ButtonObject revive(Vector2D p, Vector2D v, int presses){
        super.revive(p,v);
        buttonValue = DEFAULT_BUTTON_VALUE;
        setupMaxPresses(presses);
        buttonLabel.revive();
        pressesToLiveChanged();
        return this;
    }

    public ButtonObject revive(){
        return this.revive(randomPosInObjBounds(),willThisMove());
    }

    public ButtonObject reviveNoMovement(){
        return this.revive(randomPosInObjBounds());
    }

    public ButtonObject revive(Vector2D p){ return(this.revive(p,new Vector2D())); }

    public ButtonObject reviveNoMovement(ButtonObject other){
        this.revive(getPosFromOtherButtonObject(other));
        keepInBounds();
        return this;
    }

    public ButtonObject revive(ButtonObject other){
        this.revive(getPosFromOtherButtonObject(other), willThisMove());
        keepInBounds();
        return this;
    }

    private static Vector2D getPosFromOtherButtonObject(ButtonObject other){
        boolean foundValidVector;
        Vector2D attemptVector;
        do {
            attemptVector = Vector2D.randomVectorFromOrigin(other.getPos(), MIN_DIST_FROM_OTHER, RANGE_DIST_FROM_OTHER);
            foundValidVector = attemptVector.isInBounds(OBJ_RADIUS,OBJ_X_BOUNDS,OBJ_RADIUS,OBJ_Y_BOUNDS);
        } while (!foundValidVector);
        return attemptVector;
    }

    private boolean willThisMove(){
        return (Math.random() < MOVEMENT_CHANCE);
    }

    public ButtonObject revive(Vector2D p, boolean moving){
        if (moving){
            return(
                this.revive(
                    p,
                    Vector2D.polar(
                        Math.random()*2*Math.PI,
                        Math.random() * BUTTON_MAX_SPEED
                    )
                )
            );
        } else{
            return (this.revive(p));
        }
    }

    private void setupMaxPresses(int maxPressValue){
        pressesToLive = maxPresses = maxPressValue;
        decayRate = STANDARD_DECAY;
    }

    public void pressed(){
        if (pressesToLive < maxPresses){
            pressesToLive++;
            if (decayRate > 5){
                decayRate--;
            }
            decayTime = decayRate;
            pressesToLiveChanged();
            SoundManager.playButtonPress();
        }
    }

    private void decay(){
        if (decayTime == 0){
            pressesToLive--;
            decayTime = decayRate;
            pressesToLiveChanged();
            SoundManager.playButtonDecay();
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
        float hue = (float) ((double)pressesToLive/(double)maxPresses)/3;
        this.objectColour = Color.getHSBColor(
                hue,
                0.9f,
                1.0f
        );
        //int redScale = ((pressesToLive/maxPresses) * 255);
        //this.objectColour = new Color(redScale, 255-redScale, 0);
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
        g.setColor(Color.black);
        g.drawOval(-radius,-radius,d,d);
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

    private static int getRandomMaxPresses(){
        return (int)(MIN_MAXPRESSES + (Math.random() * RANGE_MAXPRESSES));
    }

    public double getPoints(){
        double returnThisValue = buttonValue;
        if (buttonValue > MIN_BUTTON_VALUE){
            buttonValue *= BUTTON_DEVALUATION_RATE;
        }
        return returnThisValue;
    }
}
