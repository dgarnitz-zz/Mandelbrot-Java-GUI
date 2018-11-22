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
         g.setColor(Color.BLACK);
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
                    g.setColor(Color.BLACK);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS && MC_data[i][j] >= Model.MAX_ITERATIONS*.8 && Border.color) {
                    g.setColor(Color.WHITE);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS*.8 && MC_data[i][j] >= Model.MAX_ITERATIONS*.6 && Border.color) {
                    Color darkestBlue = new Color(0, 0, 70);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS*.6 && MC_data[i][j] >= Model.MAX_ITERATIONS*.4 && Border.color) {
                    Color darkestBlue = new Color(0, 0, 160);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS*.4 && MC_data[i][j] >= Model.MAX_ITERATIONS*.3 && Border.color) {
                    Color darkestBlue = new Color(0, 0, 255);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS*.3 && MC_data[i][j] >= Model.MAX_ITERATIONS*.2 && Border.color) {
                    Color darkestBlue = new Color(0, 75, 255);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS*.2 && MC_data[i][j] >= Model.MAX_ITERATIONS*.1 && Border.color) {
                    Color darkestBlue = new Color(0, 150, 255);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MC_data[i][j] < Model.MAX_ITERATIONS*.1 && Border.color) {
                    Color darkestBlue = new Color(0, 225, 255);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
            }
        }
	}
}
