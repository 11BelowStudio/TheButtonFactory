package GamePackage.GameObjects;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class CharacterObject extends GameObject {

    final StringObject speechString;

    boolean wordsVisible;

    static final int SPEECH_TIME = 50;
    int timeUntilWordsDisappear;

    Vector2D direction;

    double rotationAngle;

    static final double HALF_PI = Math.PI/2;

    public CharacterObject(Vector2D p, Vector2D v) {
        super(p, v);
        direction = Vector2D.polar(UP_RADIANS,1);
        setAngle();
        radius = OBJ_RADIUS;
        speechString = new StringObject(new Vector2D(), new Vector2D());
    }

    @Override
    public CharacterObject revive(Vector2D p, Vector2D v){
        super.revive(p,v);
        direction = Vector2D.polar(UP_RADIANS,1);
        return this;
    }

    @Override
    void individualUpdate() {
        wordCountdown();
        individualCharacterUpdate();
    }

    void wordCountdown(){
        if (wordsVisible){
            if (timeUntilWordsDisappear > 0){
                timeUntilWordsDisappear--;
            } else{
                speechString.setText("");
                wordsVisible = false;
            }
        }
    }

    abstract void individualCharacterUpdate();

    @Override
    void keepInBounds() {
        //if it's out of bounds on the X axis, keep it within bounds
        if (position.x < OBJ_RADIUS || position.x > OBJ_X_BOUNDS) {
            velocity.x = 0; //cancels out X velocity
            if (position.x < OBJ_RADIUS) {
                position.x = OBJ_RADIUS;
            } else {
                position.x = OBJ_X_BOUNDS;
            }
        }

        //if it's out of bounds on the Y axis, keep it within bounds
        if (position.y < OBJ_RADIUS || position.y > OBJ_Y_BOUNDS){
            velocity.y = 0; //cancels out Y velocity
            if (position.y < OBJ_RADIUS){
                position.y = OBJ_RADIUS;
            } else{
                position.y = OBJ_Y_BOUNDS;
            }
        }
    }

    @Override
    void renderObject(Graphics2D g) {

        //backup of unrotated transform
        AffineTransform notRotated = g.getTransform();
        g.rotate(rotationAngle);

        //drawing the circle
        g.setColor(objectColour);
        int d = radius*2;
        g.fillOval(-radius,-radius,d,d);

        g.drawImage(img,-radius, -radius,null);

        g.setTransform(notRotated);
        //drawing the speech string
        if (wordsVisible){
            speechString.renderObject(g);
        }
    }

    public void speak(String s){
        speechString.setText(s);
        wordsVisible = true;
        timeUntilWordsDisappear = SPEECH_TIME;
    }

    void setAngle(){
        rotationAngle = direction.angle() + HALF_PI;
    }
}
