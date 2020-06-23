package GamePackage.GameObjects;

import utilities.Vector2D;

import java.awt.*;

public class BackgroundRippleObject extends GameObject {

    int timeToLive;

    int rValue;
    int gValue;
    int bValue;

    public BackgroundRippleObject() {
        super(new Vector2D(), new Vector2D());
        this.alive = false;
    }

    public BackgroundRippleObject revive(){
        return this.revive(
                randomPosInObjBounds(),
                makeRandomColor()
        );
    }

    public BackgroundRippleObject revive(ButtonObject b){
        return this.revive(b.getPos(), b.getObjectColour());
    }

    public BackgroundRippleObject revive(Vector2D p, Color c){
        super.revive(p, new Vector2D());

        timeToLive = 255;
        radius = OBJ_RADIUS;
        rValue = c.getRed();
        gValue = c.getGreen();
        bValue = c.getBlue();
        setColor();
        return this;
    }

    void amIAlive(){
        alive = (timeToLive > 0);
    }

    @Override
    void individualUpdate() {
        timeToLive--;
        radius++;
        setColor();
    }

    @Override
    void keepInBounds() { }

    @Override
    void renderObject(Graphics2D g) {
        g.setColor(objectColour);
        int d = radius*2;
        g.fillOval(-radius,-radius,d,d);
    }

    void setColor(){
        this.objectColour = new Color(rValue,gValue,bValue,timeToLive);
    }

    static Color makeRandomColor(){
        return Color.getHSBColor(
                (float)Math.random(),
                0.9f,
                1.0f
        );
    }
}
