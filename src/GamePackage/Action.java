package GamePackage;

import java.awt.*;

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene) (enhanced by me)

public class Action {


    public int yInput; // -1: up, 0: no movement, 1: down
    public int xInput; // -1 = left, 0 = no movement, 1 = right
    boolean space; //whether or not the spacebar has been pressed


    boolean clicked; //whether or not the mouse is clicked

    Point clickLocation; //where the mouse was last clicked



    void noAction(){
        yInput = 0;
        xInput = 0;
        space = false;
        clicked = false;
    }

    public boolean checkForSpacePress(){
        if (space){
            space = false;
            //need to press the space bar again for the next space press to count
            return true;
        } else{
            return false;
        }
    }

    Point getClickLocation(){
        if (clicked){
            clicked = false;
            //need to click again for the next click to count
            return clickLocation;
        } else{
            return new Point(0,0);
        }
    }

    Action(){}

}