package delegate;

import model.Model;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseListenerClass implements MouseListener {
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
    public void mouseClicked (MouseEvent e) {
        System.out.println("Mouse clicked " + e.getX() + " " + e.getY());
        clickX = (double)e.getX();
        clickY = (double)e.getY();
    }

    @Override
    public void mouseReleased (MouseEvent e) {
        System.out.println("Mouse released " + e.getX() + " " + e.getY());
        releasedX = (double)e.getX();
        releasedY = (double)e.getY();
        zoomMandelbrot();
    }

    public void zoomMandelbrot() {

        Model.MIN_REAL = clickX / 750 * Model.MIN_REAL;
        Model.MAX_REAL = releasedX / 750 * Model.MAX_REAL;
        Model.MIN_IMAGINARY = releasedY / 750 * Model.MIN_IMAGINARY;
        Model.MAX_IMAGINARY = clickY / 750 * Model.MAX_IMAGINARY;

        //MbP.removeAll();
        //MbP.revalidate();
        MbP.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed " + e.getX() + " " + e.getY());
        clickX = (double)e.getX();
        clickY = (double)e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
