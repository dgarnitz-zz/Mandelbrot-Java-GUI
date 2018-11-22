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

        if(draw && Border.enableZoom){
            g.setColor(Color.GREEN);
            g.drawRect(x, y, width, height);
        } else if (draw && Border.enablePan){
            g.setColor(Color.RED);
            g.drawLine(x, y, PanX, PanY);
        }
    }

	public void renderMandelbrot(Graphics g){
        int x = Model.defaultX;
        int y = Model.defaultY;

        int[][] MC_data = Model.createMB();
        for(int i=0; i<y; i++){
            for(int j=0; j<x; j++){
                if(MC_data[i][j] >= Model.MAX_ITERATIONS) {
                    g.drawLine(j, i, j, i);
                }
                //CREATE COLOR CONFIGURATIONS HERE

                //CREATE COLOR CONFIGURATIONS HERE
            }
        }
	}
}
