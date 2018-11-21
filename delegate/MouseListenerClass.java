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
        if(Border.enableZoom || Border.enablePan){
            MbP.draw = true;
        }


        System.out.println("Mouse pressed " + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e){
        MbP.x = Math.min((int)clickX, e.getPoint().x);
        MbP.y = Math.min((int)clickY, e.getPoint().y);
        MbP.width = Math.abs((int)clickX - e.getPoint().x);
        MbP.height = Math.abs((int)clickY - e.getPoint().y);

        if(MbP.draw){
            MbP.repaint();
        }

    }

    @Override
    public void mouseReleased (MouseEvent e) {
        System.out.println("Mouse released " + e.getX() + " " + e.getY());
        releasedX = (double)e.getPoint().x;
        releasedY = (double)e.getPoint().y;

        if(Border.enableZoom){
            MbP.draw = false;
            Border.enableZoom = false;
            zoomMandelbrot();
        }  else if(Border.enablePan) {
            MbP.draw = false;
            Border.enableZoom = false;
            //call method to pan
        }
    }



    //MOVE TO MODEL
    public void zoomMandelbrot() {

        //calculate all this in the model

        double upperX = Math.max(clickX, releasedX);
        double lowerX = Math.min(clickX, releasedX);
        double upperY = Math.max(clickY, releasedY);
        double lowerY = Math.min(clickY, releasedY);

        model.zoom(upperX, lowerX, upperY, lowerY);

        /*
        double new_MIN_REAL = ((lowerX * ((Model.MAX_REAL - Model.MIN_REAL))) / 750) + Model.MIN_REAL;
        double new_MAX_REAL = ((upperX * ((Model.MAX_REAL - Model.MIN_REAL))) / 750) + Model.MIN_REAL;
        double new_MIN_IMAGINARY = ((lowerY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY))) / 750) + Model.MIN_IMAGINARY;
        double new_MAX_IMAGINARY = ((upperY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY))) / 750) + Model.MIN_IMAGINARY;

        Model.MIN_REAL = new_MIN_REAL;
        Model.MAX_REAL = new_MAX_REAL;
        Model.MIN_IMAGINARY = new_MIN_IMAGINARY;
        Model.MAX_IMAGINARY = new_MAX_IMAGINARY;
        */

        MbP.repaint();

        /* double new_MIN_REAL = ((clickX * ((Model.MAX_REAL - Model.MIN_REAL))) / 750) + Model.MIN_REAL;
        double new_MAX_REAL = ((releasedX * ((Model.MAX_REAL - Model.MIN_REAL))) / 750) + Model.MIN_REAL;
        double new_MIN_IMAGINARY = ((releasedY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY))) / 750) + Model.MIN_IMAGINARY;
        double new_MAX_IMAGINARY = ((clickY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY))) / 750) + Model.MIN_IMAGINARY; */

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
