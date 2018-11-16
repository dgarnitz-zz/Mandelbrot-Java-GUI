import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {

    public static int defaultX = 750;
    public static int defaultY = 750;
    public static int MAX_ITERATIONS = 50;
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
            if(updatedMaxIterations > 50 && updatedMaxIterations < 50000) {
                System.out.println("Successfully updated max iterations to: " + updatedMaxIterations);
                MAX_ITERATIONS = updatedMaxIterations;
                defaultMB(MAX_ITERATIONS);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static int[][] defaultMB(int maximumIterations){

        MandelbrotCalculator MC = new MandelbrotCalculator();
        int[][] MC_data = MC.calcMandelbrotSet(
                defaultX,
                defaultY,
                MandelbrotCalculator.INITIAL_MIN_REAL,
                MandelbrotCalculator.INITIAL_MAX_REAL,
                MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_IMAGINARY,
                maximumIterations,
                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED
        );
        return MC_data;
    }
}
