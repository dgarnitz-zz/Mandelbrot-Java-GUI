package delegate;

import model.Model;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.SwingUtilities;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

public class Border extends JFrame implements PropertyChangeListener {
    private Model model;
    public MandelbrotPanel MbP = new MandelbrotPanel();
    private Container cp;
    public MouseListenerClass MLC;

    public Border(Model model) {
        this.model = model;
        model.addObserver(this);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new Toolbar(model), BorderLayout.NORTH);
        cp.add(MbP, BorderLayout.CENTER);
        //MLC = new MouseListenerClass(this.model, MbP);


        setSize(1000,1000);
        setVisible (true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("The propertyChange function has been called");
        if(evt.getSource() == model && evt.getPropertyName().equals("Mouse Zoom")){
            MLC = new MouseListenerClass(this.model, MbP);
            addMouseListener(MLC);

        } else {
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    MbP.repaint();
                    System.out.println("The repaint function has been called");
                }
            });
        }
    }

}

/*
For the zoom, it scales linearly
So you have a range in the complex plan, initially its -2 to 0.7 for real and -1.25 to 1.25 for complex, and the that scales linearly into the pixels on the screen
which right now is 700x700.
When you zoom in, you need to figure out what the new range on screen is - so if you are grabbing a pixels 200 through 500
You need to take that range and figure out what the new range should be for the complex plain
then you have to alter the INITIAL_MIN_REAL, INITIAL_MAX_REAL, INITIAL_MIN_IMAGINARY and INITIAL_MAX_IMAGINARY inside the Mandelbrot calculator
When you zoom, go for a square since it will prevent the mandelbrot set from being distorted.
As you zoom in, you have to increase the iteration count to maintain definition.

After each zoom, the range for real and complex changes, so you need to update the
INITIAL_MIN_REAL, INITIAL_MAX_REAL, INITIAL_MIN_IMAGINARY and INITIAL_MAX_IMAGINARY inside the Mandelbrot calculator

The reset just goes back to specified parameters

For the coloring, you don't just change the color after the threshold, you want to pick a color and scale the value relative to that of the
INITIAL_MAX_ITERATIONS parameter. In his example he started with green then had it approach white right before the threshold, then at the
threshold it switches to black

For the pan, you have to recalculate the values in the same way as for the zoom.
The INITIAL_MIN_REAL, INITIAL_MAX_REAL, INITIAL_MIN_IMAGINARY and INITIAL_MAX_IMAGINARY inside the Mandelbrot calculator
will all need to be altered. For this one, you figure out how much the pan is based on the pixels, then determines what
that translates to in the complex/real range, adjust the range and recalculate.

For the magnification, you just compare what you current real/complex ranges are relative to the default. So if you start at (-2.0, 0.75) and end
up at (-.002, 0.00075) you have a zoom in of 1000

Super imposing -

For basic saving and loading, all you are doing is saving the PARAMETER settings using object serialization. A slightly more complicated
extension would be to actually save the JPEG.

 */