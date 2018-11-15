import java.awt.Graphics;
import javax.swing.JPanel;

public class Panel extends JPanel {

    @Override
    public void paint (Graphics g) {
        //g.drawLine (0, 0, 75, 75);
        getMandelbrot(g, 550, 550);

    }

	public void getMandelbrot(Graphics g, int A, int B){
        int x = A;
        int y = B;

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
                if(MC_data[i][j] >= MandelbrotCalculator.INITIAL_MAX_ITERATIONS) {
                    g.drawLine(j, i, j, i);
                }
            }
        }

	}
}
