package GamePackage;

import utilities.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene) (enhanced a bit by me)

public class View extends JComponent {

    private Model model;

    private static Image bg;
    static {
        try {
            bg = ImageManager.loadImage("loading");
        } catch (IOException e) { e.printStackTrace(); }
    }


    private boolean displayingModel;


    View(){

        displayingModel = false;
        this.setPreferredSize(Constants.DEFAULT_DIMENSION);

    }

    void showModel(Model m){
        this.model = m;
        displayingModel = true;
    }



    @Override
    public void paintComponent(Graphics g0) {


        Graphics2D g = (Graphics2D) g0;
        AffineTransform initialTransform = g.getTransform();

        int width = getWidth();
        int height = getHeight();
        //g.setColor(Color.CYAN);

        //g.fillRect(0,0, width ,height);

        //Scaling the view so it's scaled according to the preferredSize of it
        g.scale(width / getPreferredSize().getWidth(), height /getPreferredSize().getHeight());


        if (displayingModel) {
            model.draw(g);
        } else{
            AffineTransform backup = g.getTransform();
            g.drawImage(bg,backup,null);
        }
        g.setTransform(initialTransform);
        revalidate();
    }

    //@Override
    //public Dimension getPreferredSize() { return Constants.DEFAULT_DIMENSION; }

    @Override
    public void setSize(Dimension d){
        super.setSize(d);
        //this.setPreferredSize(d);
        /*
        Constants.GAME_HEIGHT = this.getHeight();
        Constants.GAME_WIDTH = this.getWidth();
        if (displayingModel) {
            model.updateBackgroundRectangle();
        }
        */
        System.out.println(this.getSize().toString());
    }

}