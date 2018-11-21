package delegate;

import model.Model;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseListenerClass implements MouseListener, MouseMotionListener {
    private Model model;
    private MandelbrotPanel MbP;
    private double clickX;
    private double clickY;
    private double releasedX;
    private double releasedY;

    //It may not need this model or this MandelbrotPanel
    public MouseListenerClass(Model model, MandelbrotPanel MbP){
        this.model = model;
        this.MbP = MbP;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickX = (double)e.getPoint().x;
        clickY = (double)e.getPoint().y;
        System.out.println("Mouse pressed " + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e1){
        MbP.draw = true;
        MbP.x = Math.min((int)clickX, e1.getPoint().x);
        MbP.y = Math.min((int)clickY, e1.getPoint().y);
        MbP.width = Math.abs((int)clickX - e1.getPoint().x);
        MbP.height = Math.abs((int)clickY - e1.getPoint().y);

        MbP.repaint();

    }

    @Override
    public void mouseReleased (MouseEvent e) {
        System.out.println("Mouse released " + e.getX() + " " + e.getY());
        releasedX = (double)e.getPoint().x;
        releasedY = (double)e.getPoint().y;

        MbP.draw = false;

        //logic if statement for zoom or pan

        MbP.repaint();
        zoomMandelbrot();
    }



    //MOVE TO MODEL
    public void zoomMandelbrot() {

        double new_MIN_REAL = ((clickX * ((Model.MAX_REAL - Model.MIN_REAL)))/750) + Model.MIN_REAL;
        double new_MAX_REAL = ((releasedX * ((Model.MAX_REAL - Model.MIN_REAL)))/750) + Model.MIN_REAL;
        double new_MIN_IMAGINARY = ((releasedY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY)))/750) + Model.MIN_IMAGINARY;
        double new_MAX_IMAGINARY = ((clickY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY)))/750) + Model.MIN_IMAGINARY;

        Model.MIN_REAL = new_MIN_REAL;
        Model.MAX_REAL = new_MAX_REAL;
        Model.MIN_IMAGINARY = new_MIN_IMAGINARY;
        Model.MAX_IMAGINARY = new_MAX_IMAGINARY;

        MbP.repaint();
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
