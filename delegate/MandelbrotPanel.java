package delegate;

import model.Model;

import java.awt.*;
import javax.swing.*;

/**
 * The MandelbrotPanel class displays the Mandelbrot. It stores the coorindates of the mouse, along with a Boolean
 * controlling whether drawing is enable, as attributes.
 * It contains methods for displaying the Mandelbrot itself as well as the boxes and line shown when zooming or panning.
 */
public class MandelbrotPanel extends JPanel {

    public Boolean draw = false;
    public int x;
    public int y;
    public int width;
    public int height;

    public int PanX1;
    public int PanY1;
    public int PanX2;
    public int PanY2;

    private Model model;

    public MandelbrotPanel(Model model) {
        this.model = model;
    }


    /**
     * This method first calls the parent's paintComponent method, then it calls the renderMandelbrot. If zooming is
     * enable, it will draw a green rectangle. If panning is enable, it will draw a red line. If zoom display is enabled
     * it will display the zoom percentage.
     * @param g Graphics object
     */
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        renderMandelbrot(g);
        if(Border.displayZoom){
            String zoom = model.calculateZoom();
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawString(zoom, 20, 40);
        }

        if(draw && Border.enableZoom){
            g.setColor(Color.GREEN);
            g.drawRect(x, y, width, height);
        } else if (draw && Border.enablePan){
            g.setColor(Color.RED);
            g.drawLine(PanX1, PanY1, PanX2, PanY2);
        }
    }

    /**
     * This method is responsible for printing the Mandelbrot to the screen. It first generates the data used to print
     * the Mandelbrot with the createMB method. It then uses nested for-loops to cycle through the 2D array containing
     * the data and uses the drawLine method to print the points onto themselves, outputting a pixel to the screen.
     * When coloring is enabling, it breaks the data points into tranches based on percentages of the MAX_ITERATION
     * value.
     * @param g Graphics object
     */
	public void renderMandelbrot(Graphics g){
        int x = Model.defaultX;
        int y = Model.defaultY;

        int[][] MandelbrotData = Model.createMB();
        for(int i=0; i<y; i++){
            for(int j=0; j<x; j++){
                if(MandelbrotData[i][j] >= Model.MAX_ITERATIONS) {
                    g.setColor(Color.BLACK);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS && MandelbrotData[i][j] >= Model.MAX_ITERATIONS*.8 && Border.color) {
                    g.setColor(Color.WHITE);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS*.8 && MandelbrotData[i][j] >= Model.MAX_ITERATIONS*.6 && Border.color) {
                    Color darkestBlue = new Color(0, 0, 70);
                    g.setColor(darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS*.6 && MandelbrotData[i][j] >= Model.MAX_ITERATIONS*.4 && Border.color) {
                    Color Second_darkestBlue = new Color(0, 0, 160);
                    g.setColor(Second_darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS*.4 && MandelbrotData[i][j] >= Model.MAX_ITERATIONS*.3 && Border.color) {
                    Color Third_darkestBlue = new Color(0, 0, 255);
                    g.setColor(Third_darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS*.3 && MandelbrotData[i][j] >= Model.MAX_ITERATIONS*.2 && Border.color) {
                    Color Fourth_darkestBlue = new Color(0, 75, 255);
                    g.setColor(Fourth_darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS*.2 && MandelbrotData[i][j] >= Model.MAX_ITERATIONS*.1 && Border.color) {
                    Color Fifth_darkestBlue = new Color(0, 150, 255);
                    g.setColor(Fifth_darkestBlue);
                    g.drawLine(j, i, j, i);
                }
                else if (MandelbrotData[i][j] < Model.MAX_ITERATIONS*.1 && Border.color) {
                    Color lightestBlue = new Color(0, 225, 255);
                    g.setColor(lightestBlue);
                    g.drawLine(j, i, j, i);
                }
            }
        }
	}
}
