package model;

import java.io.Serializable;

/**
 * Serializable class designed to store the values of the model's attributes, along with that of the Border's
 * color attribute. This object is used for the undo, redo, save, and load functionality of the program.
 * It is part of the model package, as its only responsibility is storing data.
 */
public class Configurations implements Serializable {
    public int MAX_ITERATIONS;
    public double MIN_REAL;
    public double MAX_REAL;
    public double MIN_IMAGINARY;
    public double MAX_IMAGINARY;
    public Boolean color;

    public Configurations(int MAX_ITERATIONS, double MIN_REAL, double MAX_REAL, double MIN_IMAGINARY, double MAX_IMAGINARY, Boolean color) {
        this.MAX_ITERATIONS = MAX_ITERATIONS;
        this.MIN_REAL = MIN_REAL;
        this.MAX_REAL = MAX_REAL;
        this.MIN_IMAGINARY = MIN_IMAGINARY;
        this.MAX_IMAGINARY = MAX_IMAGINARY;
        this.color = color;
    }
}
