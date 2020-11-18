package GamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameFrame extends JFrame {

    //yeah, it's pretty much the JEasyFrame thing but the view is added via a method
    //which was intended for some sort of optional fullscreen support
    //but this idea was not implemented in time.

    private Component comp;

    public GameFrame() {
        this.setTitle("The Button Factory");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addView(View v){
        comp = v;
        getContentPane().add(BorderLayout.CENTER, comp);
        this.setVisible(true);

        pack();

        this.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        Dimension newDimension = GameFrame.this.getContentPane().getSize();
                        comp.setSize(newDimension);
                    }
                }
        );
    }


    //public Dimension getDimensions(){ return comp.getPreferredSize(); }


}