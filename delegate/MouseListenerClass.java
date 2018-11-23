package delegate;

import model.Model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * The MouseListener class is responsible for registering and managing all mouse-related activities.
 * It is passed a model object and a MandelbrotPanel object which it interacts.
 * The class has attributes to track the (X, Y) coordinates of when the mouse is clicked and released.
 */
public class MouseListenerClass implements MouseListener, MouseMotionListener {
    private Model model;
    private MandelbrotPanel MbP;
    private double clickX;
    private double clickY;
    private double releasedX;
    private double releasedY;

    public MouseListenerClass(Model model, MandelbrotPanel MbP){
        this.model = model;
        this.MbP = MbP;
    }

    /**
     * The mousePressed method sets the class' clickX and clickY variables when the user presses the mouse.
     * If zoom or pan is enabled, the Mandelbrot's draw variable is set to true
     * @param e event that occurs when the user clicks the Mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        clickX = (double)e.getPoint().x;
        clickY = (double)e.getPoint().y;
        if(Border.enableZoom || Border.enablePan){
            MbP.draw = true;
        }
    }

    /**
     * The mouseDragged method changes the values of selected attributes from the MandelbrotPanel object.
     * These attributes are used to draw a rectangle or a line when the user is zooming or panning.
     * If the Mandelbrot's draw Boolean attribute is true, it calls the repaint method.
     * @param e event that occurs when the user drags the Mouse
     */
    @Override
    public void mouseDragged(MouseEvent e){
        //for drawing the rectangle
        MbP.x = Math.min((int)clickX, e.getPoint().x);
        MbP.y = Math.min((int)clickY, e.getPoint().y);
        MbP.width = Math.abs((int)clickX - e.getPoint().x);
        MbP.height = Math.abs((int)clickY - e.getPoint().y);

        //for drawing the line
        MbP.PanX1 = (int)clickX;
        MbP.PanY1 = (int)clickY;
        MbP.PanX2 = e.getPoint().x;
        MbP.PanY2 = e.getPoint().y;

        if(MbP.draw){
            MbP.repaint();
        }
    }

    /**
     * The mouseReleased class grabs the (X, Y) point where the user releases the mouse and sets the corresponding
     * attribute values.
     * If zoom is enabled, it changes the Mandelbrot's draw attribute to false and calls the model's zoom method
     * with the pressed and released (X, Y) coordinates.
     * Else if pan is enabled, it changes the Mandelbrot's draw attribute to false and calls the model's pan method
     * with the pressed and released (X, Y) coordinates.
     * @param e event that occurs when the user releases the Mouse
     */
    @Override
    public void mouseReleased (MouseEvent e) {
        releasedX = (double)e.getPoint().x;
        releasedY = (double)e.getPoint().y;

        if(Border.enableZoom){
            MbP.draw = false;
            model.zoom(clickX, releasedX, clickY, releasedY);
        }  else if(Border.enablePan) {
            MbP.draw = false;
            model.pan(clickX, releasedX, clickY, releasedY);
        }
    }


    @Override
    public void mouseClicked (MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {}
}
