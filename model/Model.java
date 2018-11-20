package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {

    public static int defaultX = 750;
    public static int defaultY = 750;
    public static int MAX_ITERATIONS = 50;
    public static double MIN_REAL = -2.0;
    public static double MAX_REAL = 0.75;
    public static double MIN_IMAGINARY = -1.25;
    public static double MAX_IMAGINARY = 1.25;
    private PropertyChangeSupport notifier;

    public Model() {
        notifier = new PropertyChangeSupport(this);
    }

    public void addObserver(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    public void updateMaxIteration(String str) {
        try {
            int updatedMaxIterations = Integer.parseInt(str);
            if(updatedMaxIterations > 0 && updatedMaxIterations < 50000) {
                int old = MAX_ITERATIONS;
                MAX_ITERATIONS = updatedMaxIterations;
                System.out.println("Successfully updated max iterations to: " + MAX_ITERATIONS);
                notifier.firePropertyChange("max iterations", old, MAX_ITERATIONS);


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static int[][] createMB(){

        MandelbrotCalculator MC = new MandelbrotCalculator();
        int[][] MC_data = MC.calcMandelbrotSet(
                defaultX,
                defaultY,
                MIN_REAL,
                MAX_REAL,
                MIN_IMAGINARY,
                MAX_IMAGINARY,
                MAX_ITERATIONS,
                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED
        );
        return MC_data;
    }

}
