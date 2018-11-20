import model.MandelbrotCalculator;
import model.Model;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class MandelbrotPanel extends JPanel {

    @Override
    public void paint (Graphics g) {
        renderMandelbrot(g);

    }

	public void renderMandelbrot(Graphics g){
        System.out.println("rendering mandelbrot");

        int x = Model.defaultX;
        int y = Model.defaultY;

        int[][] MC_data = Model.createMB();
        for(int i=0; i<y; i++){
            for(int j=0; j<x; j++){
                if(MC_data[i][j] >= Model.MAX_ITERATIONS) {
                    g.drawLine(j, i, j, i);
                    //System.out.println("creating MB with max IT of: " + Model.MAX_ITERATIONS); --> this outputted the correct values but it still didnt re-render
                }
            }
        }
	}
}
