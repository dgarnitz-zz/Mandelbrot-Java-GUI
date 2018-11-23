package model;

import delegate.Border;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Stack;

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

    public void addObserver(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

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

        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println(c.getMessage());
        }

        notifier.firePropertyChange("load", "old", "new");
    }

    public void addColor() {
        Boolean old = Border.color;
        Border.color = !old;
        notifier.firePropertyChange("Color", old, Border.color);
    }

    public void zoomWithMouse() {
        Boolean old = false;
        Border.enableZoom = true;
        notifier.firePropertyChange("Mouse Zoom", old, Border.enableZoom);
    }

    public void panWithMouse(){
        Boolean old = false;
        Border.enablePan = true;
        notifier.firePropertyChange("Mouse Pan", old, Border.enablePan);
    }

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

    public void pan(double upperX, double lowerX, double upperY, double lowerY) {
        Configurations oldConfig = new Configurations(MAX_ITERATIONS, MIN_REAL, MAX_REAL, MIN_IMAGINARY, MAX_IMAGINARY, Border.color);
        Undo.push(oldConfig);

        Model.MIN_REAL = ((upperX - lowerX)/1000 * (Model.MAX_REAL - Model.MIN_REAL)) + Model.MIN_REAL;
        Model.MAX_REAL = ((upperX - lowerX)/1000 * (Model.MAX_REAL - Model.MIN_REAL)) + Model.MAX_REAL;
        Model.MIN_IMAGINARY = ((upperY - lowerY)/950 * (Model.MAX_IMAGINARY - Model.MIN_IMAGINARY)) + Model.MIN_IMAGINARY;
        Model.MAX_IMAGINARY = ((upperY - lowerY)/950 * (Model.MAX_IMAGINARY - Model.MIN_IMAGINARY)) + Model.MAX_IMAGINARY;

        notifier.firePropertyChange("Panning", "old", "new");
    }

    public String calculateZoom(){
        double zoomPercentage = (MandelbrotCalculator.INITIAL_MAX_REAL - MandelbrotCalculator.INITIAL_MIN_REAL) / (MAX_REAL - MIN_REAL);
        DecimalFormat df = new DecimalFormat("#.##");
        String zoom = df.format(zoomPercentage) + "x Zoom";
        return zoom;
    }

    public void displayZoom(){
        Boolean old = Border.displayZoom;
        Border.displayZoom = !old;
        notifier.firePropertyChange("Display Zoom", old, Border.displayZoom);
    }

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
