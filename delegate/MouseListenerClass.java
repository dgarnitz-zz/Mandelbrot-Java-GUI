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

        //I think this might be incorrect
        MbP.PanX = Math.max((int)clickX, e.getPoint().x);
        MbP.PanY = Math.max((int)clickY, e.getPoint().y);


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
            System.out.println("initiating Pan");
            panMandelbrot();
        }
    }

    public void zoomMandelbrot() {

        //calculate all this in the model

        double upperX = Math.max(clickX, releasedX);
        double lowerX = Math.min(clickX, releasedX);
        double upperY = Math.max(clickY, releasedY);
        double lowerY = Math.min(clickY, releasedY);

        model.zoom(upperX, lowerX, upperY, lowerY);
        MbP.repaint();
    }

    public void panMandelbrot() {
        model.pan(clickX, releasedX, clickY, releasedY);
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
