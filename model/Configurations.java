package model;

public class Configurations { //extend Serialize then create methods to read and write to files
    public int MAX_ITERATIONS;
    public double MIN_REAL;
    public double MAX_REAL;
    public double MIN_IMAGINARY;
    public double MAX_IMAGINARY;

    public Configurations(int MAX_ITERATIONS, double MIN_REAL, double MAX_REAL, double MIN_IMAGINARY, double MAX_IMAGINARY) {
        this.MAX_ITERATIONS = MAX_ITERATIONS;
        this.MIN_REAL = MIN_REAL;
        this.MAX_REAL = MAX_REAL;
        this.MIN_IMAGINARY = MIN_IMAGINARY;
        this.MAX_IMAGINARY = MAX_IMAGINARY;
    }
}
