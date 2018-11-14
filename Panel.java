import java.awt.Graphics;
import javax.swing.JPanel;

public class Panel extends JPanel {

    @Override
    public void paint (Graphics g) {
        g.drawLine (0, 0, 75, 75);

        int x = 20;
        int y= 25;

        MandelbrotCalculator MC = new MandelbrotCalculator();
        int[][] MC_data = MC.calcMandelbrotSet(
                x,
                y,
                MandelbrotCalculator.INITIAL_MIN_REAL,
                MandelbrotCalculator.INITIAL_MAX_REAL,
                MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_ITERATIONS,
                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED
        );

        for(int i=0; i<y; i++){
            String row = "";
            for(int j=0; j<x; j++){
                row = row + MC_data[i][j] + " ";
            }
            System.out.println(row);
        }

        //g.drawOval (10, 10, 20, 20);
    }

	/* public void getMandelbrot(){


	} */
}
