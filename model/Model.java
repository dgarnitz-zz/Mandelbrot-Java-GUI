package model;

import delegate.Border;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * The Model class is principal class of the model package. It contains all the methods used perform the calculations
 * and actions underlying the GUI's behavior. It has attributes that contain the current values of the Mandelbrot's
 * parameters. It also has two stacks as attributes, both of which hold Configurations objects used for the undo and
 * redo functionality.
 * It also contains a PropertyChangeSupport object called "notifier" that it uses to fire events to the Border class
 * in the delegate package.
 */
public class Model {

    public static int defaultX = 1000;
    public static int defaultY = 950;
    public static int MAX_ITERATIONS = 50;
    public static double MIN_REAL = -2.0;
    public static double MAX_REAL = 0.7;
    public static double MIN_IMAGINARY = -1.25;
    public static double MAX_IMAGINARY = 1.25;
    public static Stack<Configurations> Undo;
    public static Stack<Configurations> Redo;

    private PropertyChangeSupport notifier;

    public Model() {
        notifier = new PropertyChangeSupport(this);
        Undo = new Stack<>();
        Redo = new Stack<>();
    }

    /**
     * This method takes a PropertyChangeListener and adds it to the notifier PropertyChangeSupport object as
     * one of its listeners.
     * @param listener
     */
    public void addObserver(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * This method changes the MAX_ITERATION value to that entered by the user, if its valid, then fires an event
     * to the Border class in the delegate to make it render.
     * @param str sting containing the new MAX_ITERATION value
     */
    public void updateMaxIteration(String str) {
        try {
            int updatedMaxIterations = Integer.parseInt(str);
            if(updatedMaxIterations > 0 && updatedMaxIterations < 3000) {
                Configurations current = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
                Undo.push(current);
                int old = MAX_ITERATIONS;
                MAX_ITERATIONS = updatedMaxIterations;
                notifier.firePropertyChange("max iterations", old, MAX_ITERATIONS);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Uses try-with-resources to create an ObjectOutputStream and save a Configurations object with the current
     * model's attributes in a given file.
     * @param save File object representing the file that the saved object will be written to
     */
    public void save(File save) {
        try(FileOutputStream fileToSave = new FileOutputStream(save);
            ObjectOutputStream out = new ObjectOutputStream(fileToSave)) {

            Configurations saved = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
            out.writeObject(saved);
        } catch (IOException e){
            e.printStackTrace();
        }

        notifier.firePropertyChange("save", "old", "new");
    }

    /**
     * Uses try-with-resources to create an ObjectInputStream and load a Configurations object by setting the model's
     * attributes containing the Mandelbrot parameters equal to those attributes in the Configurations object containing
     * saved parameter values stored in a given file.
     * @param load File object representing the file that the saved object will be read from
     */
    public void load(File load) {
        try (FileInputStream file = new FileInputStream(load);
            ObjectInputStream loadConfigurations = new ObjectInputStream(file)) {

            Configurations loaded = (Configurations)loadConfigurations.readObject();
            Model.MIN_REAL = loaded.MIN_REAL;
            Model.MAX_REAL = loaded.MAX_REAL;
            Model.MIN_IMAGINARY = loaded.MIN_IMAGINARY;
            Model.MAX_IMAGINARY = loaded.MAX_IMAGINARY;
            Model.MAX_ITERATIONS = loaded.MAX_ITERATIONS;
            Border.color = loaded.color;

            Undo = new Stack<>();
            Redo = new Stack<>();

        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println(c.getMessage());
        }

        notifier.firePropertyChange("load", "old", "new");
    }

    /**
     * This method toggles the color, either adding it or removing it by changing the value of the Border's color
     * attribute, then fires an event causing the delegate to render with the new values
     */
    public void addColor() {
        Boolean old = Border.color;
        Border.color = !old;
        notifier.firePropertyChange("Color", old, Border.color);
    }

    /**
     * This method toggles the zoom, either enabling or disabling it by changing the value of the Border's enableZoom
     * attribute, then fires an event causing the delegate to render with the new values
     */
    public void zoomWithMouse() {
        Boolean old = Border.enableZoom;
        Border.enableZoom = !old;
        Border.enablePan = false;
        notifier.firePropertyChange("Mouse Zoom", old, Border.enableZoom);
    }

    /**
     * This method toggles the pan, either enabling it or disabling it by changing the value of the Border's enablePan
     * attribute, then fires an event causing the delegate to render with the new values
     */
    public void panWithMouse(){
        Boolean old = Border.enablePan;
        Border.enablePan = !old;
        Border.enableZoom = false;
        notifier.firePropertyChange("Mouse Pan", old, Border.enablePan);
    }

    /**
     * This method recalculates the parameters of the Mandelbrot object by taking the two (X, Y) coordinates of
     * where the mouse was clicked and released, and determining the minimum and maximum X & Y, then running them
     * through a recalculation formula. Lastly, it fires an event causing the delegate to render with the new values
     * @param clickX the X coordinate of where the mouse was clicked
     * @param releasedX the Y coordinate of where the mouse was click
     * @param clickY the X coordinate of where the mouse was released
     * @param releasedY the Y coordinate of where the mouse was released
     */
    public void zoom(double clickX, double releasedX, double clickY, double releasedY) {
        Configurations oldConfig = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
        Undo.push(oldConfig);

        double upperX = Math.max(clickX, releasedX);
        double lowerX = Math.min(clickX, releasedX);
        double upperY = Math.max(clickY, releasedY);
        double lowerY = Math.min(clickY, releasedY);

        Model.MIN_REAL = ((lowerX * ((Model.MAX_REAL - Model.MIN_REAL))) / 1000) + Model.MIN_REAL;;
        Model.MAX_REAL = ((upperX * ((Model.MAX_REAL - Model.MIN_REAL))) / 1000) + Model.MIN_REAL;
        Model.MIN_IMAGINARY = ((lowerY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY))) / 950) + Model.MIN_IMAGINARY;
        Model.MAX_IMAGINARY = ((upperY * ((Model.MAX_IMAGINARY - Model.MIN_IMAGINARY))) / 950) + Model.MIN_IMAGINARY;

        notifier.firePropertyChange("Zooming", "old", "new");
    }

    /**
     * This method recalculates the parameters of the Mandelbrot object by taking the two (X, Y) coordinates of
     * where the mouse was clicked and released, then running them through a recalculation formula.
     * Lastly, it fires an event causing the delegate to render with the new values
     * @param upperX the X coordinate of where the mouse was clicked
     * @param lowerX the Y coordinate of where the mouse was click
     * @param upperY the X coordinate of where the mouse was released
     * @param lowerY the Y coordinate of where the mouse was released
     */
    public void pan(double upperX, double lowerX, double upperY, double lowerY) {
        Configurations oldConfig = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
        Undo.push(oldConfig);

        Model.MIN_REAL = ((upperX - lowerX)/1000 * (Model.MAX_REAL - Model.MIN_REAL)) + Model.MIN_REAL;
        Model.MAX_REAL = ((upperX - lowerX)/1000 * (Model.MAX_REAL - Model.MIN_REAL)) + Model.MAX_REAL;
        Model.MIN_IMAGINARY = ((upperY - lowerY)/950 * (Model.MAX_IMAGINARY - Model.MIN_IMAGINARY)) + Model.MIN_IMAGINARY;
        Model.MAX_IMAGINARY = ((upperY - lowerY)/950 * (Model.MAX_IMAGINARY - Model.MIN_IMAGINARY)) + Model.MAX_IMAGINARY;

        notifier.firePropertyChange("Panning", "old", "new");
    }

    /**
     * A method to calculate the zoom of the mandelbrot
     * @return a string with the zoom
     */
    public String calculateZoom(){
        double zoomPercentage = (MandelbrotCalculator.INITIAL_MAX_REAL - MandelbrotCalculator.INITIAL_MIN_REAL) / (MAX_REAL - MIN_REAL);
        DecimalFormat df = new DecimalFormat("#.##");
        String zoom = df.format(zoomPercentage) + "x Zoom";
        return zoom;
    }

    /**
     * This method toggles the zoom display, either enabling or disabling it by changing the value of the Border's
     * displayZoom attribute, then fires an event causing the delegate to render with the new values
     */
    public void displayZoom(){
        Boolean old = Border.displayZoom;
        Border.displayZoom = !old;
        notifier.firePropertyChange("Display Zoom", old, Border.displayZoom);
    }

    /**
     * Method to undo user actions. It pops a Configurations object off the Undo stack. Then, before undo-ing the last
     * action, it saves the model's attributes in a new Configurations object and pushes it to the Redo stack, in
     * case the user decides they want to redo the last action. Then it takes the object from the Undo stack, sets
     * the model's attribute values equal to those of the object's attribute value, and fires an event that causes
     * the GUI to render with the updated values.
     */
    public void undo(){
        if(Undo.empty()){
            return;
        }

        Configurations undo = Undo.pop();
        Configurations current = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
        Redo.push(current);

        MIN_REAL = undo.MIN_REAL;
        MAX_REAL = undo.MAX_REAL;
        MIN_IMAGINARY = undo.MIN_IMAGINARY;
        MAX_IMAGINARY = undo.MAX_IMAGINARY;
        MAX_ITERATIONS = undo.MAX_ITERATIONS;
        Border.color = undo.color;

        notifier.firePropertyChange("Undo", "Create Old Settings Object", "Create New Settings Object");
    }

    /**
     * Works the same as the undo() method but with the Redo stack.
     */
    public void redo() {
        if(Redo.empty()){
            return;
        }

        Configurations redo = Redo.pop();
        Configurations current = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
        Undo.push(current);

        MIN_REAL = redo.MIN_REAL;
        MAX_REAL = redo.MAX_REAL;
        MIN_IMAGINARY = redo.MIN_IMAGINARY;
        MAX_IMAGINARY = redo.MAX_IMAGINARY;
        MAX_ITERATIONS = redo.MAX_ITERATIONS;
        Border.color = redo.color;

        notifier.firePropertyChange("Redo", "Create Old Settings Object", "Create New Settings Object");
    }

    /**
     * This method resets the Mandelbrot GUI by changing the Mandelbrot parameters stored as model attributes back to their
     * initial values.
     * It then resets the Undo and Redo stacks.
     * Lastly, it fires an event to force the re-rendering.
     */
    public void reset(){
        MIN_REAL = MandelbrotCalculator.INITIAL_MIN_REAL;
        MAX_REAL = MandelbrotCalculator.INITIAL_MAX_REAL;
        MIN_IMAGINARY = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        MAX_IMAGINARY = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        MAX_ITERATIONS = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        Border.color = false;

        Undo = new Stack<>();
        Redo = new Stack<>();

        notifier.firePropertyChange("Reset", "Create Old Settings Object", "Create New Settings Object");
    }

    /**
     * This method calls the MandelbrotCalculator's calcMandelbrotSet using the model's attributes as parameters. This
     * generates the data that will be printed to create the Mandelbrot image.
     * @return a 2D array containing the Mandelbrot data
     */
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
