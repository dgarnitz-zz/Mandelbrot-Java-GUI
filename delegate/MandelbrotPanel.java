package delegate;

import model.Model;

import java.awt.*;
import javax.swing.*;

public class MandelbrotPanel extends JPanel {

    public Boolean draw = false;
    public int x;
    public int y;
    public int PanX;
    public int PanY;
    public int width;
    public int height;
    private Model model;

    public MandelbrotPanel(Model model) {
        this.model = model;
    }


    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        renderMandelbrot(g);
         String zoom = model.calculateZoom();
         g.setFont(new Font("TimesRoman", Font.BOLD, 20));
         g.drawString(zoom, 20, 40);

        if(draw && Border.enableZoom){       //can draw line or box depending on pan or zoom
            g.setColor(Color.GREEN);
            g.drawRect(x, y, width, height);
        } else if (draw && Border.enablePan){       //can draw line or box depending on pan or zoom
            g.setColor(Color.RED);
            g.drawLine(x, y, PanX, PanY);
        }

    }

	public void renderMandelbrot(Graphics g){
        //Can clean this up so that variables are not static

        int x = Model.defaultX;
        int y = Model.defaultY;

        int[][] MC_data = Model.createMB();
        for(int i=0; i<y; i++){
            for(int j=0; j<x; j++){
                if(MC_data[i][j] >= Model.MAX_ITERATIONS) {
                    g.drawLine(j, i, j, i);
                    //System.out.println("creating MB with max IT of: " + Model.MAX_ITERATIONS); --> this outputted the correct values but it still didnt re-render

                    //NEED TO FIX rendering error that occurs when you lower max iterations then try to go back up
                }
            }
        }
	}
}
