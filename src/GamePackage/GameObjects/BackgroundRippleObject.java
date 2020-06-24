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

    //use this for title screen background animations
    public BackgroundRippleObject revive(){
        return this.revive(
                randomPosInObjBounds(),
                makeRandomColor()
        );
    }

    //use this to summon a background ripple after a ButtonObject has been pressed (pass the pressed button as argument)
    public BackgroundRippleObject revive(ButtonObject b){
        return this.revive(new Vector2D(b.getPos()), b.getObjectColour());
    }

    //only public just in case I need to put a specific background ripple object with a specific colour in a specific place
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
        //gets bigger+fades out with each update
            // (may implement a timer so it'll take multiple updates to change radius/transparency if this is too quick)
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

    //fades out over time
    void setColor(){
        this.objectColour = new Color(rValue,gValue,bValue,timeToLive).brighter();
    }

    static Color makeRandomColor(){
        return Color.getHSBColor(
                (float)Math.random(),
                0.9f,
                1.0f
        );
    }
}
